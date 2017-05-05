package com.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainUI extends Activity {
	private Button button1, button2, button3, button4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ui);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button1.getBackground().setAlpha(50);
		button2.getBackground().setAlpha(50);
		button3.getBackground().setAlpha(50);
		button4.getBackground().setAlpha(50);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent1 = new Intent(MainUI.this,specification.class);
				// startActivity(intent1);
				StringBuffer show = new StringBuffer();
				show.append("\n" + "      你好欢迎使用本软件，该软件的功能是帮助您来监控手机是否被伪基站劫持。");
				show.append("这是我们的初次版本在今后我们还会不断地改进它的功能。");
				show.append("由于是初版所以其中会有很多BUG也希望您如果发现缺陷请帮助我们共同改善。");
				show.append("\n" + " 这是我们参加信息安全大赛的作品。");
				show.append("\n" + "   本软件的功能模块：");
				show.append("\n" + "一 ：说明介绍----");
				show.append("\n" + "二  : 定位功能----");
				show.append("\n" + "三  : 当前手机状态----");
				show.append("\n" + "四 ：服务监控----");
				show.append("\n" + "学校：");
				show.append("？？？？？？");
				show.append("\n" + "作者");
				show.append("。。。。。。。。。。。。。");
				new AlertDialog.Builder(MainUI.this).setTitle("说明：")
						.setMessage(show.toString())
						.setPositiveButton("确定", null).show();

			}
		});
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(MainUI.this, location.class);
				startActivity(intent2);

			}
		});
		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent3 = new Intent(MainUI.this, station.class);
				startActivity(intent3);

			}
		});
		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent4 = new Intent(MainUI.this, alert.class);
				startActivity(intent4);

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

	}

}