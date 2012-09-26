package com.maciej.imiela.just.tilt.logic;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.maciej.imiela.just.tilt.controller.R;

public class JTEngine {
	/* Constants that will be used in the game */
	public static final int GAME_THREAD_DELAY = 4000;
	public static final int MENU_BUTTON_ALPHA = 0;
	public static final boolean HEPTIC_BUTTON_FEEDBACK = true;
	/* Constants that will be essential to use music */
	public static final int SPLAH_SCREEN_MUSIC = R.raw.background_music_by_setuniman;
	public static final int R_VOLUME = 100;
	public static final int L_VOLUME = 100;
	public static final boolean LOOP_BACKGRAND_MUSIC = true;

	public static Context context; // hold the thread that is playing the music
	// public static boolean isPlayingMusic = false;
	public static Thread musicThread;

	/**
	 * Method that is called when the Exit button is pressed. Perform any
	 * housekeeping that is needed in the game before it can exit cleanly.
	 * 
	 * @param v
	 * @return true - when everything went smooth; false - if anything out of
	 *         ordinary happened
	 */
	public boolean onExit(View v) {
		try {
			Intent bgMusic = new Intent(context, JTMusic.class);
			context.stopService(bgMusic);
			// musicThread.stop(); //stop shouldn't be used, think of something
			// else.
			musicThread.interrupt(); // I think it works better, but I should
										// still try to find a better way to
										// implement this.

			return true;
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			return false;
		}
	}
}
