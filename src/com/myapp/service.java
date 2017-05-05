/*
 * 
 * 
 * ���ӿƴ�
 * ��ѧ����Ϣ��ȫ����
 * ���ߣ�������
 * 2014��5��31��
 * 
 * 
 * */

package com.myapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class service extends Service {
	/********************************************************************************************************************************  */
	// ��Ƶ�洢���ݽṹ
	public class junkword {
		private String wordString;
		private float wordfrequency;

		junkword() {
			// TODO Auto-generated constructor stub
			wordString = null;
			wordfrequency = 0;
		}

		public String getwordString() {
			return wordString;
		}

		public float getwordfrequency() {
			return wordfrequency;
		}
	}

	/********************************************************************************************************************************  */
	// junkword[] aa;
	/********************************************************************************************************************************  */
	/********************************************************************************************************************************  */
	private int test1 = 0, test2 = 0, test3 = 0,test4 = 0,test5 = 0;
	private boolean windowsignal = true;
	/********************************************************************************************************************************  */
	private TelephonyManager tm;
	private int signal = 0, lac = 0, cid = 0, LAC = 0, CID = 0;
	boolean lacsignal= false;
	private int signal1 = 0;
	private String coun = "0";// ͳ��count
	private static final String MY_ACTION = "com.myapp.action.MY_ACTION";// ����countͳ��"
	private static final String SERVICE = "com.myapp.service";// �������������������ʱ��ʵ��
	private static final String serving = "com.myapp.serving";// ͳ��ɨ�����
	private int k = 0;// ���ڼ���ɨ�������ȫ�ֱ���

	/********************************************************************************************************************************  */
	GsmCellLocation gsmCellLocation;
	GsmCellLocation locationuse;
	Intent intentalert, intentnormal;
	/********************************************************************************************************************************  */
	long time = 0,timenormal = 0;
	List<NeighboringCellInfo> infos;
	/********************************************************************************************************************************  */
	private static final String SMS = "android.provider.Telephony.SMS_RECEIVED";// ���Ź㲥
	protected int NETWORK_TYPE_GPRS = 1;// �����ź�
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };// ��ȡ�绰����
	private static final int PHONES_NUMBER_INDEX = 1;// �绰����
	private StringBuffer sb = new StringBuffer();// ȫ�ֱ������ڴ洢�绰����
	private String sb1 = "";// ���ڴ洢��������
	boolean test = false;// ���ڶ��ŵ�������ȫ�ֱ���
	private String junk = "һ�Ӷ��������ľӱ��չ�Ʊ�̳�����������ѯ���̷�Ʊ��ѹ�����������ַ�����ֱ���н�����齱�н������Żݳ���𱬾�����ҵ��ؼ���������";// �����ؼ���
	SmsBroadCastReceiver smsBroadCastReceiver = new SmsBroadCastReceiver();

	/********************************************************************************************************************************  */
	// service����ʵ��//������ʵ��
	public class mybroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// go();

		}

	}

	mybroadcastReceiver mReceiver = new mybroadcastReceiver();

	/********************************************************************************************************************************  */
	/********************************************************************************************************************************  */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		getPhoneContacts();
		getSIMContacts();
		time = System.currentTimeMillis();
		timenormal = System.currentTimeMillis();
		/********************************************************************************************************************************  */
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		locationuse = (GsmCellLocation)tm.getCellLocation();
		lac = locationuse.getLac();
		LAC = lac;
		

		// ע���ȡ�ֻ��ź���
		final TelephonyManager telmanager = (TelephonyManager) service.this
				.getSystemService(Context.TELEPHONY_SERVICE);

		PhoneStateListener listen = new PhoneStateListener() {
			public void onSignalStrengthsChanged(SignalStrength signalStrength) {

				if (signalStrength.isGsm()) {
					signal = -113 + 2 * signalStrength.getGsmSignalStrength();
					if (istimeok(20000)) {
						signal1 = signal;
					}
					if (signal > signal1) {
						if ((signal - signal1) >= 15) {
							test1 = 60;
						}
					} else {
						if ((signal1 - signal) >= 15) {
							test1 = 60;
						}
					}

					Toast toast = Toast.makeText(service.this,
							String.valueOf(signal) + " " + String.valueOf(lac)
									+ " " + String.valueOf(cid),
							Toast.LENGTH_SHORT);// ����������ʾ

					toast.show();
				} else {
					Toast toast1 = Toast.makeText(getApplicationContext(),
							"���ֻ�����GSM", Toast.LENGTH_SHORT);
					toast1.show();
				}

			}

			@Override
			public void onCellLocationChanged(CellLocation location) {
				// TODO Auto-generated method stub
				// super.onCellLocationChanged(location);
				// go();
				gsmCellLocation = (GsmCellLocation) location;
				lac = gsmCellLocation.getLac();
				if (LAC != lac) {
					LAC = lac;
					lacsignal = true;
				}
				cid = gsmCellLocation.getCid();

			}

			@Override
			public void onDataActivity(int direction) {
				// TODO Auto-generated method stub
				// super.onDataActivity(direction);
			}

			@Override
			public void onDataConnectionStateChanged(int state) {
				// TODO Auto-generated method stub
				// super.onDataConnectionStateChanged(state);
			}

		};
		telmanager.listen(listen, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
				| PhoneStateListener.LISTEN_CELL_LOCATION
				| PhoneStateListener.LISTEN_DATA_ACTIVITY
				| PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);

		/********************************************************************************************************************************  */
		// ע���ȡ�ֻ�����
		IntentFilter intentFilter = new IntentFilter(SMS);
		registerReceiver(smsBroadCastReceiver, intentFilter);
		// getPhoneContacts();
		// getSIMContacts();
		/********************************************************************************************************************************  */

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		super.onStartCommand(intent, flags, startId);

		// titlebar_show();
		intentalert = new Intent(service.this, alertdialog.class);
		intentnormal = new Intent(service.this, normalstation.class);

		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				go();
				handler.postDelayed(this, 5000);

			}
		};
		handler.postDelayed(runnable, 5000);

		return startId;
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		// unregisterReceiver(smsBroadCastReceiver);
	}

	/********************************************************************************************************************************  */
	// �����Ƽ�������
	public void go() {

		// sent();
		sentserving();

		// LAC = locationuse.getLac();// ����������ʾ
		// CID = locationuse.getCid();// ����������ʾ
		// ����һ
		if (cid == 10 || lac == 65535 || lac == 0 || lac == -1 || cid == -1) {
			
               //test5 = 50;
			if (windowsignal) {
				
					sent();			
					windowsignal = false;
			}
			titlebar_show_station();
			// sent();
			
			// startActivity(intentalert);// �����Ի���
			

			// }
			/*
			 * else { try { Thread.sleep(20000); windowsignal = true; Intent
			 * intent2 = new Intent(service.this, normalstation.class);
			 * intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 * startActivity(intent2);// �����Ի��� } catch (InterruptedException e)
			 * { // TODO // Auto-generated // catch block e.printStackTrace(); }
			 * }
			 */

		} else {
			// �������뷽����
			if (lacsignal) {
				
				getcellstation_test1();
				
				if ((test1 + test2 + test3 + test4 + test5) >= 80) {
					if (windowsignal) {
						titlebar_show_station();
						sent();
						windowsignal = false;
					}
					//test5 = 0;//����
				}else {
					//getcellstation_test1();
					if(test){
						
					       if ((test1 + test2 + test3 + test4 + test5) >= 80) {
						   
						         if (windowsignal) {
						        	 
							             titlebar_show_sms();
							              sent();// ����ͳ����

							// startActivity(intentalert);// �����Ի���
							windowsignal = false;

						} else {
							try {
								Thread.sleep(20000);
								windowsignal = true;
								Intent intent2 = new Intent(service.this,
										normalstation.class);
								intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent2);// �����Ի���
							} catch (InterruptedException e) { // TODO
																// Auto-generated
																// catch
																// block
								e.printStackTrace();
							}
						}

					}
					test = false;// ����
					//test1 = 0;// ����
					test2 = 0;// ����
					test3 = 0;// ����
					//test4 = 0;//����
				}
			  } 
				test5 = 0;//����
				test1 = 0;//����
				test4 = 0;//����
				lacsignal = false;//����
			}
			
		}
		
		if (istimeoknormal(120000)) {
            if ( !windowsignal) {
            	
            	windowsignal = true;
            	titlebar_show_normal();
			}
			
		}

	}

	// α��վ���ԭ����

	// }

	/********************************************************************************************************************************  */

	
	/********************************************************************************************************************************  */
	
	public void get_SMS_word_test2(String string) {

		for (int j = 0; j <= string.length() - 2; j = j + 2) {

			if (junk.contains(string.subSequence(j, j + 2))) {

				test2 = test2 + 10;

			}
		}
	}

	/********************************************************************************************************************************  */
	public void titlebar_show_normal() {
		
		NotificationManager notificationManager;
		Intent intent;

		PendingIntent pendingIntent;
		Notification notification;

		notificationManager = (NotificationManager) service.this.getSystemService(NOTIFICATION_SERVICE);

		intent = new Intent(service.this, normalstation.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		pendingIntent = PendingIntent.getActivity(service.this, 0, intent, 0);

		notification = new Notification();
		// TODO Auto-generated method stub
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.drawable.myapp;

		
		notification.tickerText = "�������ѣ�";

		notification.defaults = Notification.DEFAULT_SOUND;

		notification.setLatestEventInfo(service.this, "α��վ����","�ٳ�֪ͨ�������ֻ������ѻָ�������", pendingIntent);

		notificationManager.notify(0, notification);
		
	}
	/********************************************************************************************************************************  */
	public void titlebar_show_sms() {
		
		NotificationManager notificationManager;
		Intent intent;

		PendingIntent pendingIntent;
		Notification notification;

		notificationManager = (NotificationManager) service.this.getSystemService(NOTIFICATION_SERVICE);

		intent = new Intent(service.this, SMS_alert.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		pendingIntent = PendingIntent.getActivity(service.this, 0, intent, 0);

		notification = new Notification();
		// TODO Auto-generated method stub
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.drawable.myapp;

		notification.tickerText = "�������ѣ�";

		notification.defaults = Notification.DEFAULT_SOUND;

		
		notification.setLatestEventInfo(service.this, "α��վ����","�ٳ�֪ͨ�����յ��Ķ��ſ�����α��վ�����ģ���ע�⣡", pendingIntent);

		notificationManager.notify(0, notification);
	}
	/********************************************************************************************************************************  */

	public void titlebar_show_station() {

		NotificationManager notificationManager;
		Intent intent;

		PendingIntent pendingIntent;
		Notification notification;

		notificationManager = (NotificationManager) service.this.getSystemService(NOTIFICATION_SERVICE);

		intent = new Intent(service.this, alertdialog.class);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		pendingIntent = PendingIntent.getActivity(service.this, 0, intent, 0);

		notification = new Notification();
		// TODO Auto-generated method stub
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.drawable.myapp;

		notification.tickerText = "WARNING...........";

		notification.defaults = Notification.DEFAULT_SOUND;

		notification.setLatestEventInfo(service.this, "α��վ����","�ٳ�֪ͨ�������ֻ����ڱ��ٳ�", pendingIntent);

		notificationManager.notify(0, notification);

	}
	
	/********************************************************************************************************************************  */
	// �ֻ�״̬ƥ��ֵtest1
	private void getcellstation_test1() {

		
		if (lac >= 1000 && lac <= 9999) {
			test4 = 70;
		}

		if (!isconnectnet() || getLocalIpAddress()) {
			test1 = test1 + 25;
		}

	}

	/********************************************************************************************************************************  */
	// ��ȡ�ٳ�ǰ���ֻ���վ������ǰ��վ��Ϣ
	private boolean istimeok(long ctime) {

		if (System.currentTimeMillis() - time >= ctime) {

			time = System.currentTimeMillis();

			return true;
		} else {

			return false;
		}

	}
	private boolean istimeoknormal (long ctime) {
		if (System.currentTimeMillis() - timenormal >= ctime ) {
			
			timenormal = System.currentTimeMillis();
			return true;
		}else {
			return false;
		}
	}

	/********************************************************************************************************************************  */
	// ��ȡ�ֻ���SIM��ͨѶ����
	private void getSIMContacts() {
		ContentResolver resolver = this.getContentResolver();

		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);

				if (TextUtils.isEmpty(phoneNumber))
					continue;

				sb.append(phoneNumber);
			}
			phoneCursor.close();
		}
		// textView.setText(sb.toString());
	}

	/********************************************************************************************************************************  */
	// ��ȡ�ֻ���SIM��ͨѶ����
	private void getPhoneContacts() {
		ContentResolver resolver = this.getContentResolver();

		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);

				if (TextUtils.isEmpty(phoneNumber))
					continue;

				sb.append(phoneNumber);
			}
			phoneCursor.close();
		}
		// textView.setText(sb.toString());
	}

	/********************************************************************************************************************************  */
	// ���ڱȽ��Ƿ���ڸú���
	private boolean serch(String string) {
		if (sb.indexOf(string) == -1) {
			return true;

		} else {
			return false;
		}
	}

	/********************************************************************************************************************************  */
	// ���Ź㲥
	public class SmsBroadCastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			StringBuffer sBuffer = new StringBuffer();
			StringBuffer sBuffer2 = new StringBuffer();
			// textView.setText("hahahahahahahaha");
			if (intent.getAction().equals(
					"android.provider.Telephony.SMS_RECEIVED")) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					Object[] myOBJpdus = (Object[]) bundle.get("pdus");
					// int a = myoObjects.length;
					// textView.setText(String.valueOf(a));
					SmsMessage[] messages = new SmsMessage[myOBJpdus.length];

					for (int i = 0; i < myOBJpdus.length; i++) {

						messages[i] = SmsMessage
								.createFromPdu((byte[]) myOBJpdus[i]);
					}
					for (SmsMessage currentMessage : messages) {
						sBuffer.append(currentMessage
								.getDisplayOriginatingAddress());
						sBuffer2.append(currentMessage.getMessageBody());

						if (serch(sBuffer.toString())) {
							// textView.setText(sBuffer.toString());
							test = true;
							test3 = 30;
						}
					}
					get_SMS_word_test2(sBuffer2.toString());
				}
			}
		}
	}

	/********************************************************************************************************************************  */
	// ����serving����
	private void sentserving() {

		Intent intent = new Intent();
		intent.setAction(serving);
		k++;
		intent.putExtra("serving", String.valueOf(k));
		sendBroadcast(intent);

	}

	/********************************************************************************************************************************  */
	// ����count������ʾ�ֻ����ٳֵĴ���
	private void sent() {
		Intent intent = new Intent();
		intent.setAction(MY_ACTION);
		int i = Integer.parseInt(coun);
		i++;
		coun = String.valueOf(i);
		intent.putExtra("count", coun);
		sendBroadcast(intent);
		// windowsignal = false;
	}

	/********************************************************************************************************************************  */
	// @SuppressWarnings("unused")
	// �������洰����������
	private void showAlertDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
		builder.setTitle("WARNING");
		builder.setMessage("�����ֻ��ѱ�α��վ�ٳ֣�");
		builder.setPositiveButton("ȷ��", null);
		builder.setNegativeButton("�ٱ�", null);
		// builder.setView(v);//use custom view
		AlertDialog dialog = builder.create();// need a <span
												// style="font-family: 'Microsoft YaHei';">AlertDialog</span>
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);// use
																		// alert.
		// dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
		dialog.show();
	}

	/********************************************************************************************************************************  */
	// �����������
	private boolean isconnectnet() {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	/********************************************************************************************************************************  */
	// ��ȡ�ֻ�IP��ַ
	public boolean getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						// inetAddress.getHostAddress().toString();
						return true;
					}
				}
			}
		} catch (SocketException ex) {
			// Log.e("WifiPreference IpAddress", ex.toString());
		}
		return false;
	}

	/********************************************************************************************************************************  */
	// �ļ�д��
	public void writeFile(String fileName, String writestr) throws IOException {
		try {

			FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);

			byte[] bytes = writestr.getBytes();

			fout.write(bytes);

			fout.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ļ�����
	public String readFile(String fileName) throws IOException {
		String res = "";
		try {
			FileInputStream fin = openFileInput(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	/********************************************************************************************************************************  */


	/********************************************************************************************************************************  */
	/*
	 * public class TelLocationListener extends PhoneStateListener{
	 * 
	 * public void onCellLocationChanged(CellLocation location){
	 * super.onCellLocationChanged(location); TelephonyManager tel =
	 * (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	 * GsmCellLocation location4 = (GsmCellLocation) tel .getCellLocation(); if
	 * (location4.getCid() == 10 || location4.getLac() == 65535 ||
	 * location4.getLac() == 0) { if (windowsignal) { test1 = test1 + 20;
	 * sent(); Intent intent1 = new Intent(service.this, alertdialog.class);
	 * intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 * startActivity(intent1);// �����Ի��� } else { try { Thread.sleep(20000);
	 * windowsignal = true; Intent intent2 = new Intent(service.this,
	 * normalstation.class); intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 * startActivity(intent2);// �����Ի��� } catch (InterruptedException e) { //
	 * TODO Auto-generated // catch block e.printStackTrace(); } }
	 * 
	 * } } }
	 */

}
