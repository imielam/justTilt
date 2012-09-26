package com.maciej.imiela.tilt;

import android.view.View;

public class JTEngine {
	/* Constants that will be used in the game */
	public static final int GAME_THREAD_DELAY = 4000;
	public static final int MENU_BUTTON_ALPHA = 0;
	public static final boolean HEPTIC_BUTTON_FEEDBACK = true;

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
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
