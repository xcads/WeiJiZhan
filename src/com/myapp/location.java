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
				textView.setText("正在重新定位......");
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
		mLocationClient.stop();// 停止定位
	}

	public void setlocationoption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setProdName("Location1");
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("当前时间 : ");
			sb.append(location.getTime());
			sb.append("\n错误次数 : ");
			sb.append(location.getLocType());
			sb.append("\n经度 : ");
			sb.append(location.getLatitude());
			sb.append("\n纬度 : ");
			sb.append(location.getLongitude());
			sb.append("\n半径 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n当前地址 : ");
				sb.append(location.getAddrStr());
			}
			textView.setText(sb.toString());
			// logMsg(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("当前时间 : ");
			sb.append(poiLocation.getTime());
			sb.append("\n错误次数 : ");
			sb.append(poiLocation.getLocType());
			sb.append("\n经度 : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\n纬度 : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\n半径 : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n当前地址 : ");
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