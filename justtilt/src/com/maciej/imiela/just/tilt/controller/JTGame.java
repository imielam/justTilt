package com.maciej.imiela.just.tilt.controller;

import com.maciej.imiela.just.tilt.view.JTGameView;

import android.app.Activity;
import android.os.Bundle;

public class JTGame extends Activity{
	
	private JTGameView gameView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new JTGameView(this);
		setContentView(gameView);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		gameView.onPause();
	}

//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		gameView.onResume();
//	}

//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//	}
//
//	@Override
//	protected void onRestart() {
//		// TODO Auto-generated method stub
//		super.onRestart();
//	}
	
	
}
