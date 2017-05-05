package com.myapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.http.util.EncodingUtils;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;

public class alert extends Activity {
	int count1[] = { 1, 2, 3, 4, 3, 9, 4, 7, 1, 3 };

	private Button button1, button2, button3, button4, button5;
	private TextView textView, textView1,textView2;
	private static final String MY_ACTION = "com.myapp.action.MY_ACTION";
	private static final String serving = "com.myapp.serving";
	private Long count;
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };// ��ȡ�绰����
	private static final int PHONES_NUMBER_INDEX = 1;// �绰����
	private StringBuffer sb = new StringBuffer();// ȫ�ֱ������ڴ洢�绰����

	private class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String count = intent.getStringExtra("count");

			textView1.setText("��ʷͳ�ƴ�����" + count);
			try {
				writeFile("android", count);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class broadcastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
		    String serving = intent.getStringExtra("serving");
		    textView2.setText("ɨ�������"+ serving);
		}
	}

	MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
	broadcastReceiver bReceiver =  new broadcastReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);
		button1 = (Button) this.findViewById(R.id.button1);
		button1.getBackground().setAlpha(60);
		button2 = (Button) this.findViewById(R.id.button2);
		button2.getBackground().setAlpha(60);
		button3 = (Button) this.findViewById(R.id.button3);
		button3.getBackground().setAlpha(60);
		button4 = (Button) this.findViewById(R.id.button4);
		button4.getBackground().setAlpha(60);
		button5 = (Button) this.findViewById(R.id.button5);
		button5.getBackground().setAlpha(60);

		textView = (TextView) this.findViewById(R.id.textView3);
		textView1 = (TextView) this.findViewById(R.id.textView4);
		textView2 = (TextView)this.findViewById(R.id.textView5);

		try {
			textView1.setText("��ʷͳ�ƽٳִ�����" + (readFile("android")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IntentFilter intentFilter = new IntentFilter(serving);
		registerReceiver(bReceiver, intentFilter);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (getphonetype()) {
					textView.setText("���������ã�");
					new AlertDialog.Builder(alert.this).setTitle("��ʾ��")
							.setMessage("������������").setPositiveButton("ȷ��", null)
							.show();

					IntentFilter intentFilter = new IntentFilter(MY_ACTION);
					registerReceiver(myBroadcastReceiver, intentFilter);
					Intent intent = new Intent(alert.this, service.class);
					// textView.setText("������������");
					startService(intent);

				} else {
					new AlertDialog.Builder(alert.this).setTitle("��Ǹ")
							.setMessage("�����ֻ�����GSM���͡�����")
							.setPositiveButton("ȷ��", null).show();
				}

			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(alert.this, service.class);
				textView.setText("������ֹͣ��");
				stopService(intent);
				new AlertDialog.Builder(alert.this).setTitle("��ʾ��")
						.setMessage("������ֹͣ��").setPositiveButton("ȷ��", null)
						.show();
			}
		});
		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
				renderer.setChartTitle("�б�ͳ��/ͳ�Ƶ��Ľٳִ���");// ��ӱ���
				renderer.setApplyBackgroundColor(true);
				renderer.setBackgroundColor(Color.GREEN);
				renderer.setAxesColor(Color.RED);// �߿���ɫ
				// renderer.setBackgroundColor(Color.BLUE);
				renderer.setXTitle("ʱ��");

				renderer.setLegendTextSize(20);
				renderer.setLabelsTextSize(25);
				renderer.setYTitle("����");
				renderer.setChartTitleTextSize(25);
				// 2,������ʾ
				XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

				XYSeries series = new XYSeries("������Ϣ/��λ/��");

				for (int k = 0; k < 10; k++) {
					// ��x,yֵ
					series.add(k, count1[k]);

				}
				// ��Ҫ���Ƶĵ�Ž�dataset��
				dataset.addSeries(series);

				// 3, �Ե�Ļ��ƽ�������
				XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
				// 3.1������ɫ
				xyRenderer.setColor(Color.RED);
				// 3.2���õ����ʽ
				xyRenderer.setPointStyle(PointStyle.SQUARE);
				// 3.3, ��Ҫ���Ƶĵ���ӵ����������
				renderer.addSeriesRenderer(xyRenderer);

				// Intent intent = new LinChart().execute(this);
				Intent intent = ChartFactory.getLineChartIntent(alert.this,
						dataset, renderer);
				startActivity(intent);

			}
		});
		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String junk = "һ�Ӷ��������ľӱ��չ�Ʊ�̳�����������ѯ���̷�Ʊ��ѹ�����������ַ�����ֱ���н�����齱�н������Żݳ���𱬾�����ҵ��ؼ���������";
				new AlertDialog.Builder(alert.this).setMessage(junk)
						.setPositiveButton("ȷ��", null)
						.setPositiveButton("���", null).show();

			}
		});
		button5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSIMContacts();
				getPhoneContacts();
				new AlertDialog.Builder(alert.this).setMessage(sb.toString())
						.setPositiveButton("ȷ��", null).show();
			}
		});

	}

	/********************************************************************************************************************************  */
	public boolean getphonetype() {

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);
		if (tm.getPhoneType() == 1) {
			return true;
		} else {
			return false;
		}

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

	/****************************************************************************************************************** ***************** */

	// activity����
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// unregisterReceiver(myBroadcastReceiver);
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

	/**************************************************************************************************************************************/
}
