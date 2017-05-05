/*
 * 
 * 
 * 电子科大
 * 大学生信息安全竞赛
 * 编者：户宇宙
 * 2014年5月31日
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
	// 字频存储数据结构
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
	private String coun = "0";// 统计count
	private static final String MY_ACTION = "com.myapp.action.MY_ACTION";// 发送count统计"
	private static final String SERVICE = "com.myapp.service";// 服务监听器，监听器的时候实现
	private static final String serving = "com.myapp.serving";// 统计扫描次数
	private int k = 0;// 用于计算扫描次数的全局变量

	/********************************************************************************************************************************  */
	GsmCellLocation gsmCellLocation;
	GsmCellLocation locationuse;
	Intent intentalert, intentnormal;
	/********************************************************************************************************************************  */
	long time = 0,timenormal = 0;
	List<NeighboringCellInfo> infos;
	/********************************************************************************************************************************  */
	private static final String SMS = "android.provider.Telephony.SMS_RECEIVED";// 短信广播
	protected int NETWORK_TYPE_GPRS = 1;// 网络信号
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };// 读取电话号码
	private static final int PHONES_NUMBER_INDEX = 1;// 电话参数
	private StringBuffer sb = new StringBuffer();// 全局变量用于存储电话号码
	private String sb1 = "";// 用于存储短信内容
	boolean test = false;// 用于短信到来与否的全局变量
	private String junk = "一居二居三居四居保险股票商场购房热线咨询开盘发票免费恭候光临热销现房促销直销中奖办理抽奖中奖热线优惠酬宾火爆惊爆开业活动特价抢购促销";// 垃圾关键字
	SmsBroadCastReceiver smsBroadCastReceiver = new SmsBroadCastReceiver();

	/********************************************************************************************************************************  */
	// service功能实现//监听器实现
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
		

		// 注册获取手机信号量
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
							Toast.LENGTH_SHORT);// 测试用于显示

					toast.show();
				} else {
					Toast toast1 = Toast.makeText(getApplicationContext(),
							"该手机不是GSM", Toast.LENGTH_SHORT);
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
		// 注册获取手机短信
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
	// 检测机制即主方法
	public void go() {

		// sent();
		sentserving();

		// LAC = locationuse.getLac();// 测试用于显示
		// CID = locationuse.getCid();// 测试用于显示
		// 方法一
		if (cid == 10 || lac == 65535 || lac == 0 || lac == -1 || cid == -1) {
			
               //test5 = 50;
			if (windowsignal) {
				
					sent();			
					windowsignal = false;
			}
			titlebar_show_station();
			// sent();
			
			// startActivity(intentalert);// 弹出对话框
			

			// }
			/*
			 * else { try { Thread.sleep(20000); windowsignal = true; Intent
			 * intent2 = new Intent(service.this, normalstation.class);
			 * intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 * startActivity(intent2);// 弹出对话框 } catch (InterruptedException e)
			 * { // TODO // Auto-generated // catch block e.printStackTrace(); }
			 * }
			 */

		} else {
			// 方法二与方法三
			if (lacsignal) {
				
				getcellstation_test1();
				
				if ((test1 + test2 + test3 + test4 + test5) >= 80) {
					if (windowsignal) {
						titlebar_show_station();
						sent();
						windowsignal = false;
					}
					//test5 = 0;//清零
				}else {
					//getcellstation_test1();
					if(test){
						
					       if ((test1 + test2 + test3 + test4 + test5) >= 80) {
						   
						         if (windowsignal) {
						        	 
							             titlebar_show_sms();
							              sent();// 回送统计量

							// startActivity(intentalert);// 弹出对话框
							windowsignal = false;

						} else {
							try {
								Thread.sleep(20000);
								windowsignal = true;
								Intent intent2 = new Intent(service.this,
										normalstation.class);
								intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent2);// 弹出对话框
							} catch (InterruptedException e) { // TODO
																// Auto-generated
																// catch
																// block
								e.printStackTrace();
							}
						}

					}
					test = false;// 清零
					//test1 = 0;// 清零
					test2 = 0;// 清零
					test3 = 0;// 清零
					//test4 = 0;//清零
				}
			  } 
				test5 = 0;//清零
				test1 = 0;//清零
				test4 = 0;//清零
				lacsignal = false;//清零
			}
			
		}
		
		if (istimeoknormal(120000)) {
            if ( !windowsignal) {
            	
            	windowsignal = true;
            	titlebar_show_normal();
			}
			
		}

	}

	// 伪基站检测原理方案

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

		
		notification.tickerText = "正常提醒：";

		notification.defaults = Notification.DEFAULT_SOUND;

		notification.setLatestEventInfo(service.this, "伪基站防护","劫持通知：您的手机现在已恢复正常！", pendingIntent);

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

		notification.tickerText = "可疑提醒：";

		notification.defaults = Notification.DEFAULT_SOUND;

		
		notification.setLatestEventInfo(service.this, "伪基站防护","劫持通知：您收到的短信可能是伪基站发出的，请注意！", pendingIntent);

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

		notification.setLatestEventInfo(service.this, "伪基站防护","劫持通知：您的手机现在被劫持", pendingIntent);

		notificationManager.notify(0, notification);

	}
	
	/********************************************************************************************************************************  */
	// 手机状态匹配值test1
	private void getcellstation_test1() {

		
		if (lac >= 1000 && lac <= 9999) {
			test4 = 70;
		}

		if (!isconnectnet() || getLocalIpAddress()) {
			test1 = test1 + 25;
		}

	}

	/********************************************************************************************************************************  */
	// 获取劫持前的手机基站包括当前基站信息
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
	// 获取手机与SIM卡通讯号码
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
	// 获取手机与SIM卡通讯号码
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
	// 用于比较是否存在该号码
	private boolean serch(String string) {
		if (sb.indexOf(string) == -1) {
			return true;

		} else {
			return false;
		}
	}

	/********************************************************************************************************************************  */
	// 短信广播
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
	// 回送serving次数
	private void sentserving() {

		Intent intent = new Intent();
		intent.setAction(serving);
		k++;
		intent.putExtra("serving", String.valueOf(k));
		sendBroadcast(intent);

	}

	/********************************************************************************************************************************  */
	// 回送count用于显示手机被劫持的次数
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
	// 弹出警告窗口用于提醒
	private void showAlertDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
		builder.setTitle("WARNING");
		builder.setMessage("您的手机已被伪基站劫持！");
		builder.setPositiveButton("确定", null);
		builder.setNegativeButton("举报", null);
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
	// 网络连接与否
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
	// 获取手机IP地址
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
	// 文件写入
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

	// 文件读出
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
	 * startActivity(intent1);// 弹出对话框 } else { try { Thread.sleep(20000);
	 * windowsignal = true; Intent intent2 = new Intent(service.this,
	 * normalstation.class); intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 * startActivity(intent2);// 弹出对话框 } catch (InterruptedException e) { //
	 * TODO Auto-generated // catch block e.printStackTrace(); } }
	 * 
	 * } } }
	 */

}
