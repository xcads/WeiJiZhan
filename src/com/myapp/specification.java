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
		show.append("\n" + "      ��û�ӭʹ�ñ������������Ĺ����ǰ�����������ֻ��Ƿ�α��վ�ٳ֡�");
		show.append("\n" + "      �������ǵĳ��ΰ汾�ڽ�����ǻ��᲻�ϵظĽ����Ĺ��ܡ�");
		show.append("\n" + "      �����ǳ����������л��кܶ�BUGҲϣ�����������ȱ����������ǹ�ͬ���ơ�");
		show.append("\n" + "      �������ǲμ���Ϣ��ȫ��������Ʒ��");
		show.append("\n" + "\n" + "   ������Ĺ���ģ�飺һ ��˵������----");
		show.append("\n"
				+ "                                         ��  : ��λ����----");
		show.append("\n"
				+ "                                         ��  : ��ǰ�ֻ�״̬----");
		show.append("\n"
				+ "                                         �� ��������----");
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