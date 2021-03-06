package com.maciej.imiela.just.tilt.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.maciej.imiela.just.tilt.model.JTEngine;
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
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spash_screen);

		new Handler().postDelayed(new Thread() {
			public void run() {
				Intent mainMenu = new Intent(MainActivity.this, JTMainMenu.class);
				MainActivity.this.startActivity(mainMenu);
				MainActivity.this.finish();
				
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		}, JTEngine.GAME_THREAD_DELAY);
	}
}
