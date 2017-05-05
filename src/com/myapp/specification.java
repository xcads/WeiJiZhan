package com.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class specification extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.specification);
		TextView textView = (TextView) this.findViewById(R.id.show);
		StringBuffer show = new StringBuffer();
		show.append("\n" + "      你好欢迎使用本软件，该软件的功能是帮助您来监控手机是否被伪基站劫持。");
		show.append("\n" + "      这是我们的初次版本在今后我们还会不断地改进它的功能。");
		show.append("\n" + "      由于是初版所以其中会有很多BUG也希望您如果发现缺陷请帮助我们共同改善。");
		show.append("\n" + "      这是我们参加信息安全大赛的作品。");
		show.append("\n" + "\n" + "   本软件的功能模块：一 ：说明介绍----");
		show.append("\n"
				+ "                                         二  : 定位功能----");
		show.append("\n"
				+ "                                         三  : 当前手机状态----");
		show.append("\n"
				+ "                                         四 ：服务监控----");
		textView.setText(show.toString());

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

}