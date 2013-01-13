package com.maciej.imiela.just.tilt.model;

import android.graphics.PointF;
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
public class JTWeapon extends JTDrawable{
	public boolean shotFired = false;

	/**
	 * 
	 */
	public JTWeapon(int number) {
		super.numberSpriteShite = number;
		float texture[] = { 0.0f, 0.0f,//
				0.5f, 0.0f,//
				0.5f, 0.5f,//
				0.0f, 0.5f,//
		};
		super.texture = texture;
		super.init();
		
		shotFired = true;
		currentPosition = new PointF(JTEngine.playerBankPosX, JTEngine.playerBankPosY + 0.25f);
	}

}
