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
				show.append("\n" + "      ��û�ӭʹ�ñ������������Ĺ����ǰ�����������ֻ��Ƿ�α��վ�ٳ֡�");
				show.append("�������ǵĳ��ΰ汾�ڽ�����ǻ��᲻�ϵظĽ����Ĺ��ܡ�");
				show.append("�����ǳ����������л��кܶ�BUGҲϣ�����������ȱ����������ǹ�ͬ���ơ�");
				show.append("\n" + " �������ǲμ���Ϣ��ȫ��������Ʒ��");
				show.append("\n" + "   ������Ĺ���ģ�飺");
				show.append("\n" + "һ ��˵������----");
				show.append("\n" + "��  : ��λ����----");
				show.append("\n" + "��  : ��ǰ�ֻ�״̬----");
				show.append("\n" + "�� ��������----");
				show.append("\n" + "ѧУ��");
				show.append("������������");
				show.append("\n" + "����");
				show.append("��������������������������");
				new AlertDialog.Builder(MainUI.this).setTitle("˵����")
						.setMessage(show.toString())
						.setPositiveButton("ȷ��", null).show();

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