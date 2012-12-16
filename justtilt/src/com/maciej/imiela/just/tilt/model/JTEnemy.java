package com.maciej.imiela.just.tilt.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;
import android.util.Log;
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
public class JTEnemy {
	private static String TAG = JTEnemy.class.getSimpleName();

	// TODO Don't like the public variables, should be private with getter and
	// setter. Remeber to change this.
	public float posY = 0f; // the x position of the enemy
	public float posX = 0f; // the y position of the enemy
	// public float posT = 0f; // the t used in calculating a Bezier curve
	public float incrementXToTarget = 0f; // the x increment to reach a
											// potential target
	public float incrementYToTarget = 0f; // the y increment to reach a
											// potential target

	public boolean isDestroyed = false; // has this ship been destroyed?+
	private int damage = 0;

	public boolean isLockedOn = false; // had the enemy locked on to a target?
	public float lockOnPosX = 0f; // x position of the target
	public float lockOnPosY = 0f; // y position of the target
	public float tgalfa = 0f; //tg alfa of the line which enemy is moving

	private Random randomPos = new Random(15);

	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer;
	private float vertices[] = { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f, 0.0f, };
	private float texture[] = { 0.0f, 0.0f, //
			1 / 5f, 0.0f, //
			1 / 5f, 0.5f, //
			0.0f, 0.5f, };
	private byte indices[] = { 0, 1, 2, 0, 2, 3, };

//	public void init() {
//		posY = (randomPos.nextFloat() * 9) + 9;
//		// switch (attackDirection) {
//		// case JTEngine.ATTACK_LEFT:
//		// posX = 0;
//		// break;
//		// case JTEngine.ATTACK_RANDOM:
//		// posX = randomPos.nextFloat() * 9;
//		// break;
//		// case JTEngine.ATTACK_RIGHT:
//		// posX = 9;
//		// break;
//		// }
//		// posT = JTEngine.SCOUT_SPEED;
//		lockOnPosX = 0;
//		lockOnPosY = 0;
//		isLockedOn = false;
//	}

	public JTEnemy() {
		posY = randomPos.nextFloat() * 9;
		posX = randomPos.nextFloat() * 9;
		lockOnPosX = 0;
		lockOnPosY = 0;
		isLockedOn = false;

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	public void move(){
		//wyznaczenie wzoru prostej po ktorej ma sie poruszac nasz przeciwnik
		float a = (posY - lockOnPosY)/(posX - lockOnPosX);
		float b = posY - a*posX;
		tgalfa = a;
		//wyznaczenie wspó³rzêdnych punktu na prostej w zale¿noœci od szybkoœci prouszania siê
		//wyznaczenie tego korzystaj¹c z wzoru na odleg³oœæ dwóch punktów w przestrzeni
		float c = 1 + a*a;
		float d = 2*a*b - 2*posY*a - 2*posX;
		float e = posX*posX + posY*posY + b*b - 2*posY*b - JTEngine.ENEMY_SPEED*JTEngine.ENEMY_SPEED; 
		float x;
		
		float delta = d*d - 4*c*e;
		if (delta < 0){
			x = posX;
			Log.v(TAG, "Delta ujemna");
		} else {
			double x1 = (-d + Math.sqrt(delta))/(2*c); 
			double x2 = (-d - Math.sqrt(delta))/(2*c); 
			if (Math.abs(lockOnPosX - x1) < Math.abs(lockOnPosX - x2)){
				x = (float) x1;
			} else {
				x = (float) x2;
			}
			
		}
		
		float y = a*x + b;
		posX = x;
		posY = y;
		
		
//		if (this.posY - this.lockOnPosY > 1f) {
//			this.posX = this.incrementToX();
//			this.posY -= (JTEngine.ENEMY_SPEED);
//		} else if (this.posY - this.lockOnPosY < -1f) {
//			this.posX = this.decrementToX();
//			this.posY += (JTEngine.ENEMY_SPEED);}
//			// } else {
//			// this.posY += (JTEngine.INTERCEPTOR_SPEED * 4);
	}
	
	
	public float incrementToX() {
		// return ((JTEngine.INTERCEPTOR_SPEED * 4) - posY - lockOnPosY) *
		// (lockOnPosX - posX) / (-lockOnPosY + posY);
		return (posX - lockOnPosX)
				* (posY - JTEngine.ENEMY_SPEED * 4 - lockOnPosY)
				/ (posY - lockOnPosY) + lockOnPosX;
	}

	public float decrementToX() {
		return (lockOnPosX - posX) * (JTEngine.ENEMY_SPEED * 4)
				/ (lockOnPosY - posY) + posX;
	}

	public void draw(GL10 gl, int[] spriteSheet) {

		gl.glBindTexture(GL10.GL_TEXTURE_2D, spriteSheet[0]);
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_BYTE, indexBuffer);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	public void applyDamage() {
		damage++;
		if (damage == JTEngine.ENEMY_SHIELDS) {
			isDestroyed = true;
		}

	}
}
