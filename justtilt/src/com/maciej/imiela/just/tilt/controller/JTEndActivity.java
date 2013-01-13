package com.maciej.imiela.just.tilt.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class JTEndActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jtend);
	}
	
	public void backToMainMenu(View v){
		this.finish();
	}


}
