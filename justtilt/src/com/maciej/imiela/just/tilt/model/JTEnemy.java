package com.maciej.imiela.just.tilt.model;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;
import android.util.FloatMath;
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
public class JTEnemy extends JTPlayer{
	private static String TAG = JTEnemy.class.getSimpleName();

	// TODO Don't like the public variables, should be private with getter and
	// setter. Remeber to change this.

	public boolean isDestroyed = false; // has this ship been destroyed?+
	private int damage = 0;

	public float lockOnPosX = 0f; // x position of the target
	public float lockOnPosY = 0f; // y position of the target

	private Random randomPos;
	/**
	 * @param r
	 */
	public JTEnemy(Random r, int number) {
		super.numberSpriteShite = number;
		randomPos = r;
		init();
	}
	
	/**
	 * 
	 */
	public JTEnemy(int number) {
		this(new Random(15), number);
	}

	/* (non-Javadoc)
	 * @see com.maciej.imiela.just.tilt.model.JTPlayer#init()
	 */
	@Override
	protected void init() {
		super.vertices = vertices;
		super.texture = texture;
		
		currentPosition = new PointF(randomPos.nextFloat() * 9, randomPos.nextFloat() * 9);
		lockOnPosX = 0;
		lockOnPosY = 0;

		super.init();
	}
	
	/* (non-Javadoc)
	 * @see com.maciej.imiela.just.tilt.model.JTDrawable#move(javax.microedition.khronos.opengles.GL10, int[], android.graphics.PointF)
	 */
	@Override
	public void move(GL10 gl, int[] spriteSheets, PointF textureStart){
		lockOnPosX = JTEngine.playerBankPosX;
		lockOnPosY = JTEngine.playerBankPosY;
		
		//wyznaczenie wzoru prostej po ktorej ma sie poruszac nasz przeciwnik
		float a = (currentPosition.y - lockOnPosY)/(currentPosition.x - lockOnPosX);
		float b = currentPosition.y - a*currentPosition.x;
		
		//wyznaczenie wspó³rzêdnych punktu na prostej w zale¿noœci od szybkoœci prouszania siê
		//wyznaczenie tego korzystaj¹c z wzoru na odleg³oœæ dwóch punktów w przestrzeni
		float c = 1 + a*a;
		float d = 2*a*b - 2*currentPosition.y*a - 2*currentPosition.x;
		float e = currentPosition.x*currentPosition.x + currentPosition.y*currentPosition.y + b*b - 2*currentPosition.y*b - JTEngine.ENEMY_SPEED*JTEngine.ENEMY_SPEED; 
		float x;
		
		float delta = d*d - 4*c*e;
		if (delta < 0){
			x = currentPosition.x;
//			Log.v(TAG, "Delta ujemna");
//			Log.v(TAG, "enemy pos last: x: " + currentPosition.x + " y: " + currentPosition.y);
//			Log.v(TAG, "hero pos last: x: " + lockOnPosX + " y: " + lockOnPosY);
		} else {
			float x1 = (-d + FloatMath.sqrt(delta))/(2*c); 
			float x2 = (-d - FloatMath.sqrt(delta))/(2*c); 
			if (Math.abs(lockOnPosX - x1) < Math.abs(lockOnPosX - x2)){
				x = x1;
			} else {
				x = x2;
			}
		}
		
		float y = a*x + b;
		currentPosition = new PointF(x, y);
		
		alfa = determineRotateAngle(lockOnPosX-currentPosition.x, lockOnPosY-currentPosition.y);
		super.move(gl, spriteSheets, new PointF(0, 0));
	}


	/**
	 * 
	 */
	public void applyDamage() {
		damage++;
		if (damage == JTEngine.ENEMY_SHIELDS) {
			isDestroyed = true;
		}

	}
}
