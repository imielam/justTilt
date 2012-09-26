package com.maciej.imiela.just.tilt.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.maciej.imiela.just.tilt.logic.JTEngine;
import com.maciej.imiela.just.tilt.logic.JTMusic;

public class JTMainMenu extends Activity {

	/**
	 * called when the activity is first created.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /**Fire up background music*/
        
        JTEngine.musicThread = new Thread(){
        	
        	private final int DELAY = 10000;
//        	private volatile boolean stop = false;
//        	
//        	public void requestStop() {
//        		stop = true;
//        	}
        	
        	public void run() {
        		Intent bgmusic = new Intent(getApplicationContext(), JTMusic.class);
        		startService(bgmusic);
        		JTEngine.context = getApplicationContext();
        		
        		
        		try {
        			Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return;
				}
        	}
        };
        JTEngine.musicThread.start();
        
        final JTEngine engine = new JTEngine();
        
        /**
         * Set menu button options
         */
        ImageButton start = (ImageButton)findViewById(R.id.btnStart);
        ImageButton exit = (ImageButton)findViewById(R.id.btnExit);
        
        start.getBackground().setAlpha(JTEngine.MENU_BUTTON_ALPHA);
        start.setHapticFeedbackEnabled(JTEngine.HEPTIC_BUTTON_FEEDBACK);
        
        exit.getBackground().setAlpha(JTEngine.MENU_BUTTON_ALPHA);
        exit.setHapticFeedbackEnabled(JTEngine.HEPTIC_BUTTON_FEEDBACK);
        
        start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/**
				 * Start Game!!
				 */
				
			}
		});
        exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean clean = false;
				clean = engine.onExit(v);
				if(clean) {
					int pid = android.os.Process.myPid();
					android.os.Process.killProcess(pid);
				}
				
			}
		});
    }


}
