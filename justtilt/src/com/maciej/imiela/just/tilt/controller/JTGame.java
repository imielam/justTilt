package com.maciej.imiela.just.tilt.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.maciej.imiela.just.tilt.model.JTEngine;
import com.maciej.imiela.just.tilt.view.JTGameView;

public class JTGame extends Activity {
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
//		Rect display = new Rect();
//		JTEngine.display.getRectSize(display);
		
		WindowManager w = getWindowManager();
		Display d = w.getDefaultDisplay();
		
		int height = d.getHeight() / 4;
		int playableArea = d.getHeight() - height;
		if (y > playableArea) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (x < d.getWidth() / 2) {
					JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_LEFT_1;
				} else {
					JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_RIGHT_1;
				}
				break;
			case MotionEvent.ACTION_UP:
				JTEngine.playerFlightAction = JTEngine.PLAYER_RELEASE;
				break;
			}
		}

		return super.onTouchEvent(event);
	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// gameView.onResume();
	// }

	// @Override
	// protected void onStop() {
	// // TODO Auto-generated method stub
	// super.onStop();
	// }
	//
	// @Override
	// protected void onRestart() {
	// // TODO Auto-generated method stub
	// super.onRestart();
	// }

	// @Override
	// protected void onDestroy() {
	// super.onStop();
	// engine.onExit(null);
	// }

}
