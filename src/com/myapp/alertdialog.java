package com.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class alertdialog extends Activity implements OnClickListener {

	private Button button3, button4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.alert);

		button3 = (Button) alertdialog.this.findViewById(R.id.button3);
		button4 = (Button) alertdialog.this.findViewById(R.id.button4);
		button3.setOnClickListener(alertdialog.this);

	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		alertdialog.this.finish();

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