package com.maciej.imiela.just.tilt.model;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;

import com.maciej.imiela.just.tilt.controller.R;

public class JTEngine {
	/**Constant to use accelerometer*/
	public static final int ACCELEROMETER_CHANGE_ACCEPTANCE = 1;
	public static final int ACCELEROMETER_CHANGE_BEHAVIOR = 1;
	/** Constants that will be used in the game */
	public static final int GAME_THREAD_DELAY = 4000;
	public static final int MENU_BUTTON_ALPHA = 0;
	public static final boolean HEPTIC_BUTTON_FEEDBACK = true;
	public static final int GAME_THREAD_FPS_SLEEP = (1000 / 60);
	/** Constants that will be essential to use music */
	public static final int SPLAH_SCREEN_MUSIC = R.raw.background_music_by_setuniman;
	public static final int R_VOLUME = 0;
	public static final int L_VOLUME = 0;
	public static final boolean LOOP_BACKGRAND_MUSIC = true;
	/** constants that are essentials to draw background */
	public static final int BACKGROUND_LAYER_ONE = R.drawable.jt_background1;
	public static final float SCROLL_BACKGROUND_1 = 0.002f;
	public static final int BACKGROUND_LAYER_TWO = R.drawable.secendary_background;
	public static float SCROLL_BACKGROUND_2 = 0.007f;

	public static Context context; // hold the thread that is playing the music
	// public static boolean isPlayingMusic = false;
	public static Thread musicThread;
	public static Display display;
	/** move the good character */
	public static int playerFlightAction = 0; // check the stae of the goodGuy
	public static int playerFlightActionY = 0;
	public static final int PLAYER_SHIP = R.drawable.good_guy;
	public static final int PLAYER_BANK_LEFT_1 = 1;
	public static final int PLAYER_RELEASE = 3;
	public static final int PLAYER_BANK_RIGHT_1 = 4;
	public static final int PLAYER_BANK_FORWARD = 1;
	public static final int PLAYER_BANK_BACKWARD = 4;
	public static final float PLAYER_BANK_SPEED = .1f;
	public static float playerBankPosX = 4.5f;
	public static float playerBankPosY = 3f;
	/*
	 * constant that describe how often the sprite is animated (9 animation of
	 * the background for each sprite)
	 */
	public static final int PLAYER_FRAMES_BETWEEN_ANI = 9; 

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
