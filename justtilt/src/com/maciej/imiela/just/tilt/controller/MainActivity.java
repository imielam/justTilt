package com.maciej.imiela.just.tilt.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.maciej.imiela.just.tilt.logic.JTEngine;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
