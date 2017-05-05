package com.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class location extends Activity {
	private TextView textView;
	private Button button;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		textView = (TextView) this.findViewById(R.id.textView31);
		button = (Button) this.findViewById(R.id.button31);
		button.getBackground().setAlpha(80);
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.setAccessKey("Dd9DamOm9xQGKsvI2e5jWx7t");
		setlocationoption();
		mLocationClient.start();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textView.setText("�������¶�λ......");
				if (mLocationClient != null && mLocationClient.isStarted())
					mLocationClient.requestLocation();

			}
		});
	}

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
		mLocationClient.stop();// ֹͣ��λ
	}

	public void setlocationoption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(true);// ��ֹ���û��涨λ
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setProdName("Location1");
		option.setPoiNumber(5); // ��෵��POI����
		option.setPoiDistance(1000); // poi��ѯ����
		option.setPoiExtraInfo(true); // �Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("��ǰʱ�� : ");
			sb.append(location.getTime());
			sb.append("\n������� : ");
			sb.append(location.getLocType());
			sb.append("\n���� : ");
			sb.append(location.getLatitude());
			sb.append("\nγ�� : ");
			sb.append(location.getLongitude());
			sb.append("\n�뾶 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n��ǰ��ַ : ");
				sb.append(location.getAddrStr());
			}
			textView.setText(sb.toString());
			// logMsg(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// �����¸��汾��ȥ��poi����
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("��ǰʱ�� : ");
			sb.append(poiLocation.getTime());
			sb.append("\n������� : ");
			sb.append(poiLocation.getLocType());
			sb.append("\n���� : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nγ�� : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\n�뾶 : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n��ǰ��ַ : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			textView.setText(sb.toString());
			// logMsg(sb.toString());
		}
	}

}