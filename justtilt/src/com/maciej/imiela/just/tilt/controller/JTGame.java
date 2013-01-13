package com.maciej.imiela.just.tilt.controller;

import java.util.Observable;
import java.util.Observer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;

import com.maciej.imiela.just.tilt.model.JTEngine;
import com.maciej.imiela.just.tilt.model.JTMusic;
import com.maciej.imiela.just.tilt.view.JTGameView;

@TargetApi(13)
/******************************************************************************* 
 * Filename : MyGraphView
 * 
 * Author : Maciej Imiela <m.imiela@samsung.com>
 * 
 * Date : <12-11-2012>
 * 
 * Description :
 * 
 * Design Document : 
 * 
 * COPYRIGHT NOTICE 
 * ================= 
 * The contents of this file are protected under international copyright 
 * laws and may not be copied.
 *******************************************************************************/
public class JTGame extends Activity implements SensorEventListener, Observer {
	private JTGameView gameView;

	private Sensor accelerometr;
	private SensorManager sm;
	private float prevY, prevX, sensorY, sensorX;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new JTGameView(this);
		setContentView(gameView);
		gameView.getRenderer().addObserver((Observer)this);

		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			accelerometr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sm.registerListener(this, accelerometr,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				JTEngine.fire = true;
		}

		return super.onTouchEvent(event);
	}

	/* (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
	 */
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	/* (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	public void onSensorChanged(SensorEvent event) {
		// TODO think about synchronization and multithreading

		
		boolean changed = false;
		sensorY = event.values[0];
		sensorX = event.values[1];
		
		
		
		if (Math.abs(sensorY - prevY) > JTEngine.ACCELEROMETER_CHANGE_ACCEPTANCE) {
			prevY = sensorY;
			changed = true;
		}
		if (Math.abs(sensorX - prevX) > JTEngine.ACCELEROMETER_CHANGE_ACCEPTANCE) {
			prevX = sensorX;
			changed = true;
		}
		if (changed) {
			gameView.getRenderer().getPlayer1().setWhereToGo(new PointF(sensorX, -sensorY));
			changed = false;
		}

		if (prevY > JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightActionY = JTEngine.PLAYER_BANK_BACKWARD;
		} else if (prevY < -JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightActionY = JTEngine.PLAYER_BANK_FORWARD;
		} else {
			JTEngine.playerFlightActionY = JTEngine.PLAYER_RELEASE;
		}

		
		if (prevX > JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_RIGHT_1;
		} else if (prevX < -JTEngine.ACCELEROMETER_CHANGE_BEHAVIOR) {
			JTEngine.playerFlightAction = JTEngine.PLAYER_BANK_LEFT_1;
		} else {
			JTEngine.playerFlightAction = JTEngine.PLAYER_RELEASE;
		}

	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		Intent intent = new Intent(this, JTEndActivity.class);
		startActivity(intent);
		this.finish();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		gameView.onResume();
		sm.registerListener(this, accelerometr,
				SensorManager.SENSOR_DELAY_NORMAL);
		
		AudioManager am = (AudioManager) this
				.getSystemService(Context.AUDIO_SERVICE);
		if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
			/** Fire up background music */

			JTEngine.musicThread = new Thread() {

				private final int DELAY = 10000;

				// private volatile boolean stop = false;
				//
				// public void requestStop() {
				// stop = true;
				// }

				public void run() {
					Intent bgmusic = new Intent(getApplicationContext(),
							JTMusic.class);
					startService(bgmusic);
					JTEngine.context = getApplicationContext();

					try {
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						return;
					}
				}
			};

			JTEngine.musicThread.start();
		} else {
			JTEngine.context = getApplicationContext();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		gameView.onPause();
		sm.unregisterListener(this);
		JTEngine.onExit();
	}


}
