package com.maciej.imiela.just.tilt.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.maciej.imiela.just.tilt.model.JTEngine;
import com.maciej.imiela.just.tilt.view.JTGameView;

@TargetApi(13)
public class JTGame extends Activity implements SensorEventListener {
	private JTGameView gameView;

	private Sensor accelerometr;
	private SensorManager sm;
	private float prevX, prevY, sensorX, sensorY;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new JTGameView(this);
		setContentView(gameView);

		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			accelerometr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sm.registerListener(this, accelerometr,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		gameView.onPause();
		sm.unregisterListener(this);
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		float x = event.getX();
//		float y = event.getY();
//		Rect display = new Rect();
//		WindowManager w = getWindowManager();
//		Display d = w.getDefaultDisplay();
//		d.getRectSize(display);
//
//		int height = display.height() / 4;
//		int playableArea = display.height() - height;
//		if (y > playableArea) {
//			switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//				if (x < display.width() / 2) {
//					JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_LEFT_1;
//				} else {
//					JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_RIGHT_1;
//				}
//				break;
//			case MotionEvent.ACTION_UP:
//				JTEngine.playerFlightAction = JTEngine.PLAYER_RELEASE;
//				break;
//			}
//		}
//
//		return super.onTouchEvent(event);
//	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		sensorX = event.values[0];
		sensorY = event.values[1];
		if (Math.abs(sensorX - prevX) > JTEngine.ACCELEROMETER_CHANGE_ACCEPTANCE) {
			prevX = sensorX;
		}
		if (Math.abs(sensorY - prevY) > JTEngine.ACCELEROMETER_CHANGE_ACCEPTANCE) {
			prevY = sensorY;
		}

		if (prevX > JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightActionY = JTEngine.PLAYER_BANK_BACKWARD;
		} else if (prevX < -JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightActionY = JTEngine.PLAYER_BANK_FORWARD;
		} else {
			JTEngine.playerFlightActionY = JTEngine.PLAYER_RELEASE;
		}

		
		if (prevY > JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_RIGHT_1;
		} else if (prevY < -JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_LEFT_1;
		} else {
			JTEngine.playerFlightAction = JTEngine.PLAYER_RELEASE;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		gameView.onResume();
		sm.registerListener(this, accelerometr,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

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
