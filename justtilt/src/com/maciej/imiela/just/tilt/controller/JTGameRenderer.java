package com.maciej.imiela.just.tilt.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.maciej.imiela.just.tilt.model.JTBackground;
import com.maciej.imiela.just.tilt.model.JTEnemy;
import com.maciej.imiela.just.tilt.model.JTEngine;
import com.maciej.imiela.just.tilt.model.JTGoodGuy;
import com.maciej.imiela.just.tilt.model.JTPlayer;
import com.maciej.imiela.just.tilt.model.JTTextures;
import com.maciej.imiela.just.tilt.model.JTWeapon;
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
public class JTGameRenderer extends Observable implements Renderer {

	private static String TAG = JTGameRenderer.class.getSimpleName();

	private JTBackground background = new JTBackground(2, new PointF(0, 0));
	private JTBackground backgroung1 = new JTBackground(3, new PointF(
			JTEngine.backgroundXPosition, JTEngine.backgroundYPosition));
	private JTGoodGuy player1 = new JTGoodGuy(0);

	private ArrayList<JTEnemy> enemies = new ArrayList<JTEnemy>();
	private JTTextures spriteTextureLoader;
	private int[] spriteSheets = new int[4];
	private List<JTWeapon> playerFire = new LinkedList<JTWeapon>();

	private long loopStart = 0;
	private long loopEnd = 0;
	private long loopRunTime = 0;
	private Random randomPos = new Random(15);

	private int count = 0;

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		loopStart = System.currentTimeMillis();
		try {
			if (loopRunTime < JTEngine.GAME_THREAD_FPS_SLEEP) {
				Thread.sleep(JTEngine.GAME_THREAD_FPS_SLEEP);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count++;
		if (count >= 60) {
			count = 0;
			enemies.add(new JTEnemy(randomPos,1));
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		scrollBackground1(gl);
		scrollBackground2(gl);
		movePlayer1(gl);
		playerWeapons(gl);
		moveEnemy(gl);

		detectCollisions();

		loopEnd = System.currentTimeMillis();
		loopRunTime = ((loopEnd - loopStart));

	}

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);

	}

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		spriteTextureLoader = new JTTextures(gl);
//		backgroundTextureLoader = new JTTextures(gl);
		spriteSheets = spriteTextureLoader.loadTexture(gl, JTEngine.CHARACTER_SHEET,
				JTEngine.context, 1);
		spriteSheets = spriteTextureLoader.loadTexture(gl, JTEngine.WEAPONS_SHEET,
				JTEngine.context, 2);
		spriteSheets = spriteTextureLoader.loadTexture(gl, JTEngine.BACKGROUND_LAYER_ONE,
				JTEngine.context,3);
		spriteSheets = spriteTextureLoader.loadTexture(gl, JTEngine.BACKGROUND_LAYER_TWO,
				JTEngine.context,4);
		/** enabling texture mapping */
		gl.glEnable(GL10.GL_TEXTURE_2D);
		/** inform openGL to test depth of every object onn the surface */
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// /** activate blending feature */
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//
//		background.loadTexture(gl, JTEngine.BACKGROUND_LAYER_ONE,
//				JTEngine.context);
//		backgroung1.loadTexture(gl, JTEngine.BACKGROUND_LAYER_TWO,
//				JTEngine.context);
	}

	public synchronized JTGoodGuy getPlayer1() {
		return player1;
	}
	
	private void detectCollisions() {
		Iterator<JTWeapon> w = playerFire.iterator();
		while (w.hasNext()) {
			JTWeapon fire = w.next();
			Iterator<JTEnemy> e = enemies.iterator();
			while (e.hasNext()) {
				JTEnemy enemy = e.next();
				if (!enemy.isDestroyed) {
					if ((enemy.currentPosition.x - enemy.lockOnPosX < 0.5f && enemy.currentPosition.x
							- enemy.lockOnPosX > -0.5f)
							&& (enemy.currentPosition.y - enemy.lockOnPosY < 0.5f && enemy.currentPosition.y
									- enemy.lockOnPosY > -0.5f)) {
						this.setChanged();
						this.notifyObservers("Colision Detected");
					}
					if ((fire.currentPosition.y >= enemy.currentPosition.y - 1 && fire.currentPosition.y <= enemy.currentPosition.y)
							&& (fire.currentPosition.x <= enemy.currentPosition.x + 1 && fire.currentPosition.x >= enemy.currentPosition.x - 1)) {
						enemy.applyDamage();
						if (enemy.isDestroyed) {
							e.remove();
						}
						w.remove();
						break;
					}
				}
			}
		}
		Iterator<JTEnemy> e = enemies.iterator();
		while (e.hasNext()) {
			JTEnemy enemy = e.next();
			if (!enemy.isDestroyed) {
				if ((enemy.currentPosition.x - enemy.lockOnPosX < 0.5f && enemy.currentPosition.x
						- enemy.lockOnPosX > -0.5f)
						&& (enemy.currentPosition.y - enemy.lockOnPosY < 0.5f && enemy.currentPosition.y
								- enemy.lockOnPosY > -0.5f)) {
					this.setChanged();
					this.notifyObservers("Colision Detected");
				}
			}
		}

	}

	private void playerWeapons(GL10 gl) {
		if (JTEngine.fire) {
			playerFire.add(new JTWeapon(1));
			JTEngine.fire = false;
		}
		Iterator<JTWeapon> itr = playerFire.iterator();
		while (itr.hasNext()) {
			JTWeapon fire = itr.next();
			if (fire.shotFired) {
				if (fire.currentPosition.y > 10.25) {
					itr.remove();
				} else {
					fire.currentPosition.y += JTEngine.PLAYER_BULLET_SPEED;
					fire.move(gl, spriteSheets, new PointF(0, 0));
				}
			}
		}
	}
	private void moveEnemy(GL10 gl) {
		for (JTEnemy enemy : enemies) {
			if (!enemy.isDestroyed) {
				enemy.move(gl, spriteSheets, new PointF(0, 0));
			}
		}
	}

	private void movePlayer1(GL10 gl) {
		player1.move(gl, spriteSheets, new PointF(2 / 5f, 0.5f));
	}

	/**
	 * Method that enable scroll background It reset the model matrix to make
	 * sure it has not been inadvertently moved. It loads the texture matrix and
	 * moves it along the x axis by the value in SCROLL_BACKGROUND_1. It draws
	 * the background and pops the matrix back on the stack.
	 * 
	 * @param gl
	 */
	private void scrollBackground1(GL10 gl) {
		
		background.move(gl, spriteSheets, new PointF(0, 0), new PointF(1, 1));
	}

	private void scrollBackground2(GL10 gl) {
		switch (JTEngine.playerFlightActionY) {
		case JTEngine.PLAYER_BANK_FORWARD:
			if (JTEngine.backgroundYPosition < 1) {
				JTEngine.backgroundYPosition += JTEngine.SCROLL_BACKGROUND_1;
			}
			break;
		case JTEngine.PLAYER_BANK_BACKWARD:
			if (JTEngine.backgroundYPosition > 0) {
				JTEngine.backgroundYPosition -= JTEngine.SCROLL_BACKGROUND_1;
			}
			break;
		default:
			break;
		}

		switch (JTEngine.playerFlightAction) {
		case JTEngine.PLAYER_BANK_LEFT_1:
			if (JTEngine.backgroundXPosition > 0) {
				JTEngine.backgroundXPosition -= JTEngine.SCROLL_BACKGROUND_1;
			}
			break;
		case JTEngine.PLAYER_BANK_RIGHT_1:
			if (JTEngine.backgroundXPosition < (2 / JTEngine.SCREEN_PROPORTION - 1)) {
				JTEngine.backgroundXPosition += JTEngine.SCROLL_BACKGROUND_1;
			}
			break;
		default:
			break;
		}
		backgroung1.currentPosition = new PointF(JTEngine.backgroundXPosition, JTEngine.backgroundYPosition);
		backgroung1.move(gl, spriteSheets, new PointF(0, 0), new PointF(0.5f * JTEngine.SCREEN_PROPORTION, 0.5f));
		
	}

}
