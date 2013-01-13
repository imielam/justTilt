package com.maciej.imiela.just.tilt.model;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;

import com.maciej.imiela.just.tilt.controller.R;
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
public class JTEngine {
	/** Constant to use accelerometer */
	public static final int ACCELEROMETER_CHANGE_ACCEPTANCE = 1;
	public static final int ACCELEROMETER_CHANGE_BEHAVIOR = 1;
	/** Constants that will be used in the game */
	public static final int GAME_THREAD_DELAY = 4000;
	public static final int MENU_BUTTON_ALPHA = 0;
	public static final boolean HEPTIC_BUTTON_FEEDBACK = true;
	public static final int GAME_THREAD_FPS_SLEEP = (1000 / 60);
	/** Constants that will be essential to use music */
	public static final int SPLAH_SCREEN_MUSIC = R.raw.background_music_by_setuniman;
	public static final int R_VOLUME = 100;
	public static final int L_VOLUME = 100;
	public static final boolean LOOP_BACKGRAND_MUSIC = true;
	/** constants that are essentials to draw background */
	public static final int BACKGROUND_LAYER_ONE = R.drawable.fistr_background;
	public static final float SCROLL_BACKGROUND_1 = 0.002f;
	public static final int BACKGROUND_LAYER_TWO = R.drawable.secon_background;
	public static float SCROLL_BACKGROUND_2 = 0.007f;
	public static final float SCREEN_PROPORTION = 9/16f;
	public static float backgroundXPosition = 0.6f*2f/JTEngine.SCREEN_PROPORTION;
	public static float backgroundYPosition = 0.8f;

	public static Context context; // hold the thread that is playing the music
	// public static boolean isPlayingMusic = false;
	public static Thread musicThread = null;
	public static Display display;
	/** move the good character */
	public static int playerFlightAction = 0; // check the stae of the goodGuy
	public static int playerFlightActionY = 0;
	public static int CHARACTER_SHEET = R.drawable.character_sprite;
	public static final int PLAYER_SHIP = R.drawable.good_guy;
	public static final int PLAYER_BANK_LEFT_1 = 1;
	public static final int PLAYER_RELEASE = 3;
	public static final int PLAYER_BANK_RIGHT_1 = 4;
	public static final int PLAYER_BANK_FORWARD = 1;
	public static final int PLAYER_BANK_BACKWARD = 4;
	public static final float PLAYER_BANK_SPEED = .1f;
	public static float playerBankPosX = 4.5f;
	public static float playerBankPosY = 3f;
	/** Enemy AI */
//	public static int TOTAL_ENEMY = 35;
	public static float ENEMY_SPEED = SCROLL_BACKGROUND_1 * 4f;;
//	public static float ENEMY_SPEED = 0.1f;
	/** weapons */
	public static final int WEAPONS_SHEET = R.drawable.weapons_sprite;
	public static final int PLAYER_SHIELDS = 5;
//	public static final int SCOUT_SHIELDS = 3;
	public static final int ENEMY_SHIELDS = 1;
//	public static final int WARSHIP_SHIELDS = 5;
	public static final float PLAYER_BULLET_SPEED = .125f;
	public static boolean fire = false;

	/*
	 * constant that describe how often the sprite is animated (9 animation of
	 * the background for each sprite)
	 */
	public static final int PLAYER_FRAMES_BETWEEN_ANI = 9;

	/**
	 * Method that is called when the Exit button is pressed. Perform any
	 * housekeeping that is needed in the game before it can exit cleanly.
	 * 
	 * @return true - when everything went smooth; false - if anything out of
	 *         ordinary happened
	 */
	public static boolean onExit() {
		if (JTEngine.musicThread == null){
			return true;
		}
		try {
			Intent bgMusic = new Intent(context, JTMusic.class);
			context.stopService(bgMusic);
			// musicThread.stop(); //stop shouldn't be used, think of something
			// else.
			// TODO I think it works better, but I should still try to find a
			// better way to implement this.
			musicThread.interrupt();

			return true;
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			return false;
		}
	}
}
