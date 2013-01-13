package com.maciej.imiela.just.tilt.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.maciej.imiela.just.tilt.model.JTEngine;
import com.maciej.imiela.just.tilt.model.JTMusic;
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
public class JTMainMenu extends Activity {

//	private JTEngine engine = new JTEngine();

	/**
	 * called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * Set menu button options
		 */
		ImageButton start = (ImageButton) findViewById(R.id.btnStart);
		ImageButton exit = (ImageButton) findViewById(R.id.btnExit);

		start.getBackground().setAlpha(JTEngine.MENU_BUTTON_ALPHA);
		start.setHapticFeedbackEnabled(JTEngine.HEPTIC_BUTTON_FEEDBACK);

		exit.getBackground().setAlpha(JTEngine.MENU_BUTTON_ALPHA);
		exit.setHapticFeedbackEnabled(JTEngine.HEPTIC_BUTTON_FEEDBACK);

		start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/**
				 * Start Game!!
				 */
				Intent game = new Intent(getApplicationContext(), JTGame.class);
				JTMainMenu.this.startActivity(game);

			}
		});
		exit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				JTMainMenu.this.finish();
			}
		});
	}
}
