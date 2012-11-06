package com.maciej.imiela.just.tilt.controller;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

import com.maciej.imiela.just.tilt.model.JTBackground;
import com.maciej.imiela.just.tilt.model.JTEngine;
import com.maciej.imiela.just.tilt.model.JTGoodGuy;

public class JTGameRenderer implements Renderer {

	private JTBackground background = new JTBackground();
	private JTBackground backgroung1 = new JTBackground();
	private JTGoodGuy player1 = new JTGoodGuy();
	private int goodGuyBankFrames = 0;

	private float bgScroll1;
	private float bgScroll2;

	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(JTEngine.GAME_THREAD_FPS_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		scrollBackground1(gl);
		scrollBackground2(gl);
		movePlayer1(gl);
		//
		// gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

	}

	private void movePlayer1(GL10 gl) {
		switch (JTEngine.playerFlightAction) {
		case JTEngine.PLAYER_BANK_LEFT_1:
			break;
		case JTEngine.PLAYER_BANK_RIGHT_1:
			break;
		case JTEngine.PLAYER_RELEASE:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.1f, .1f, 1f);
			gl.glTranslatef(JTEngine.playerBankPosX, 0f, 0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(1 / 3f, 0f, 0.0f);
			player1.draw(gl);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			goodGuyBankFrames += 1;

		default:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.1f, .1f, 1f);
			gl.glTranslatef(JTEngine.playerBankPosX, 4.5f, 0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(1 / 3f, 0f, 0.0f);
			player1.draw(gl);
			gl.glPopMatrix();
			gl.glLoadIdentity();

			break;
		}

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
		if (bgScroll1 == Float.MAX_VALUE)
			bgScroll1 = 0f;
		/** reset the scale and translate of the Model matrix mode */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(1f, 1f, 1f);
		gl.glTranslatef(0f, 0f, 0f);

		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(bgScroll1, 0.0f, 0.0f);

		background.draw(gl);
		gl.glPopMatrix();
		bgScroll1 += JTEngine.SCROLL_BACKGROUND_1;
		gl.glLoadIdentity();
	}

	private void scrollBackground2(GL10 gl) {
		if (bgScroll2 == Float.MAX_VALUE) {
			bgScroll2 = 0f;
		}

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(1f, 0.5f, 1f);
		gl.glTranslatef(0f, -0.5f, 0f);

		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(bgScroll2, 0.0f, 0.0f);
		backgroung1.draw(gl);
		gl.glPopMatrix();
		bgScroll2 += JTEngine.SCROLL_BACKGROUND_2;
		gl.glLoadIdentity();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		/** enabling texture mapping */
		gl.glEnable(GL10.GL_TEXTURE_2D);
		/** inform openGL to test depth of every object onn the surface */
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
//		/** activate blending feature */
		// gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

		background.loadTexture(gl, JTEngine.BACKGROUND_LAYER_ONE,
				JTEngine.context);
		backgroung1.loadTexture(gl, JTEngine.BACKGROUND_LAYER_TWO,
				JTEngine.context);
		player1.loadTexture(gl, JTEngine.PLAYER_SHIP, JTEngine.context);
		// TODO load game textures
	}

}
