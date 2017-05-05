package com.myapp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.ListActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class station extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);

		String phonetype, dataactivity, datastate, DeviceSoftwareVersion, deviceid, linenumber, networkoperator, networktype, mcc, mnc, networkopenratorname, SimSerialNumber;
		String ip;

		networkoperator = tm.getNetworkOperator();
		mcc = "MCC:" + networkoperator.substring(0, 3);
		mnc = "MNC:" + networkoperator.substring(3);
		deviceid = "手机IMEI：" + tm.getDeviceId();
		DeviceSoftwareVersion = "设备的软件版本号：" + tm.getDeviceSoftwareVersion();
		linenumber = "手机号：" + tm.getLine1Number();
		networkopenratorname = "服务商名称：" + tm.getSimOperatorName();
		SimSerialNumber = "手机SIM卡号：" + tm.getSimSerialNumber();
		if (tm.getPhoneType() == 1) {
			phonetype = "手机类型：" + "GSM";
		} else if (tm.getPhoneType() == 2) {
			phonetype = "手机类型：" + "CMDA";
		} else {
			phonetype = "手机类型：未知";
		}
		if (tm.getDataState() == 2) {
			datastate = "数据已连接：";

		} else {
			datastate = "数据未连接：";

		}
		ip = "IP地址：" + getLocalIpAddress();

		String[] COUNTRIES = new String[] { mcc, mnc, deviceid,
				DeviceSoftwareVersion, linenumber, networkopenratorname,
				SimSerialNumber, phonetype, datastate, ip };
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, COUNTRIES));

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

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

	}

	public String getLocalIpAddress() {
		try {

			for (Enumeration<NetworkInterface> en = NetworkInterface

			.getNetworkInterfaces(); en.hasMoreElements();) {

				NetworkInterface intf = en.nextElement();

				for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr

				.hasMoreElements();) {

					InetAddress inetAddress = ipAddr.nextElement();
					// ipv4地址
					if (!inetAddress.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(inetAddress
									.getHostAddress())) {

						return inetAddress.getHostAddress();

					}

				}

			}

		} catch (Exception ex) {

		}

		return "";

	}

}