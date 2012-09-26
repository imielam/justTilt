package com.maciej.imiela.just.tilt.logic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class JTMusic extends Service {

	public static boolean isRunning = false;
	MediaPlayer player;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		setMusicOption(this, JTEngine.LOOP_BACKGRAND_MUSIC, JTEngine.R_VOLUME,
				JTEngine.L_VOLUME, JTEngine.SPLAH_SCREEN_MUSIC);
	}

	private void setMusicOption(Context context, boolean isLooped, int rVolume,
			int lVolume, int soundFile) {
		player = MediaPlayer.create(context, soundFile);
		player.setLooping(isLooped);
		player.setVolume(lVolume, rVolume);

	}

	// @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			player.start();
			isRunning = true;
		} catch (Exception e) {
			isRunning = false;
			player.stop();
		}
		return 1;
	}

	// @Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		// super.onStart(intent, startId);
	}

	public void onStop() {
		isRunning = false;
	}

	public IBinder onUnBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onPause() {

	}

	@Override
	public void onDestroy() {
		player.stop();
		player.release();
	}

	@Override
	public void onLowMemory() {
		player.stop();
	}

}
