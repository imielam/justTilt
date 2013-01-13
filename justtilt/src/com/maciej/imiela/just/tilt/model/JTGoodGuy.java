package com.maciej.imiela.just.tilt.model;

import javax.microedition.khronos.opengles.GL10;

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
//TODO Change the class to extend class JTDrawable(create it first)
public class JTGoodGuy extends JTPlayer{
	
	private PointF whereToGo = new PointF(0, 0);

	public synchronized void setWhereToGo(PointF whereToGo) {
		this.whereToGo = whereToGo;
	}
	
	public JTGoodGuy(int number) {
		super.numberSpriteShite = number;
		super.init();
		currentPosition = new PointF(JTEngine.playerBankPosX, JTEngine.playerBankPosY);
	}
	
	@Override
	public void move(GL10 gl, int[] spriteSheets, PointF textureStart){
		switch (JTEngine.playerFlightActionY) {
		case JTEngine.PLAYER_BANK_FORWARD:
			if (JTEngine.playerBankPosY < 9.5f) {
				JTEngine.playerBankPosY += JTEngine.PLAYER_BANK_SPEED;
			}
			break;
		case JTEngine.PLAYER_BANK_BACKWARD:
			if (JTEngine.playerBankPosY > 0.5f) {
				JTEngine.playerBankPosY -= JTEngine.PLAYER_BANK_SPEED;
			}
			break;
		default:
			break;
		}

		switch (JTEngine.playerFlightAction) {
		case JTEngine.PLAYER_BANK_LEFT_1:
			if (JTEngine.playerBankPosX > 0.5f) {
				JTEngine.playerBankPosX -= JTEngine.PLAYER_BANK_SPEED;
			}
			break;
		case JTEngine.PLAYER_BANK_RIGHT_1:
			if (JTEngine.playerBankPosX < 9.5f) {
				JTEngine.playerBankPosX += JTEngine.PLAYER_BANK_SPEED;
			}
			break;
		default:
			break;
		}
		currentPosition = new PointF(JTEngine.playerBankPosX, JTEngine.playerBankPosY);
		alfa = determineRotateAngle(whereToGo);
		if (Float.isNaN(alfa)){
			alfa = 0;
		}
		super.move(gl, spriteSheets, textureStart);

	}

}
