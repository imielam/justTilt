package com.maciej.imiela.just.tilt.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

import com.maciej.imiela.just.tilt.model.JTBackground;
import com.maciej.imiela.just.tilt.model.JTEnemy;
import com.maciej.imiela.just.tilt.model.JTEngine;
import com.maciej.imiela.just.tilt.model.JTGoodGuy;
import com.maciej.imiela.just.tilt.model.JTTextures;
import com.maciej.imiela.just.tilt.model.JTWeapon;

public class JTGameRenderer implements Renderer {


	private JTBackground background = new JTBackground();
	private JTBackground backgroung1 = new JTBackground();
	private JTGoodGuy player1 = new JTGoodGuy();

	private ArrayList<JTEnemy> enemies = new ArrayList<JTEnemy>();
	// private JTEnemy[] enemies = new JTEnemy[JTEngine.TOTAL_INTERCEPTORS
	// + JTEngine.TOTAL_SCOUTS + JTEngine.TOTAL_WARSHIPS - 1];
	private JTTextures textureLoader;
	private int[] spriteSheets = new int[2];
	private List<JTWeapon> playerFire = new LinkedList<JTWeapon>();

	private int goodGuyBankFrames = 0;
	private long loopStart = 0;
	private long loopEnd = 0;
	private long loopRunTime = 0;

//	private float bgScroll1;
//	private float bgScroll2;

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
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		scrollBackground1(gl);
		scrollBackground2(gl);
		movePlayer1(gl);
		moveEnemy(gl);
		
		detectCollisions();

		// All other game drawing will be called here

		loopEnd = System.currentTimeMillis();
		loopRunTime = ((loopEnd - loopStart));

	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		initializeInterceptors();
		// initializePlayerWeapons();
		// initializeScouts();
		// initializeWarships();
		textureLoader = new JTTextures(gl);
		spriteSheets = textureLoader.loadTexture(gl, JTEngine.CHARACTER_SHEET,
				JTEngine.context, 1);
		spriteSheets = textureLoader.loadTexture(gl, JTEngine.WEAPONS_SHEET,
				JTEngine.context, 2);
		/** enabling texture mapping */
		gl.glEnable(GL10.GL_TEXTURE_2D);
		/** inform openGL to test depth of every object onn the surface */
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// /** activate blending feature */
		gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		background.loadTexture(gl, JTEngine.BACKGROUND_LAYER_ONE,
				JTEngine.context);
		backgroung1.loadTexture(gl, JTEngine.BACKGROUND_LAYER_TWO,
				JTEngine.context);
		// player1.loadTexture(gl, JTEngine.PLAYER_SHIP, JTEngine.context);
		// TODO load game textures
	}

	private void initializeInterceptors() {
		for (int x = 0; x < JTEngine.TOTAL_INTERCEPTORS; x++) {
			JTEnemy interceptor = new JTEnemy(JTEngine.TYPE_INTERCEPTOR,
					JTEngine.ATTACK_RANDOM);
			enemies.add(interceptor);

		}
	}

	// private void initializePlayerWeapons() {
	// for (int x = 0; x < 10; x++) {
	// playerFire.add(new JTWeapon());
	// }
	// playerFire.get(0).shotFired = true;
	// playerFire.get(0).posX = JTEngine.playerBankPosX;
	// playerFire.get(0).posY = JTEngine.playerBankPosY + 0.25f;
	// }

	private void detectCollisions() {
		Iterator<JTWeapon> w = playerFire.iterator();
		while (w.hasNext()){
			JTWeapon fire = w.next();
			Iterator<JTEnemy> e = enemies.iterator();
			while (e.hasNext()) {
				JTEnemy enemy = e.next();
				if (!enemy.isDestroyed && enemy.posY < 10.25f) {
					if ((fire.posY >= enemy.posY - 1 && fire.posY <= enemy.posY)
							&& (fire.posX <= enemy.posX + 1 && fire.posX >= enemy.posX - 1)) {
						enemy.applyDamage();
						if (enemy.isDestroyed){
							e.remove();
						}
						w.remove();
						break;
					}
				}
			}
		}
		
		
		
		
//		for (JTWeapon fire : playerFire) {
//			for (JTEnemy enemy : enemies) {
//				if (!enemy.isDestroyed && enemy.posY < 10.25f) {
//					if ((fire.posY >= enemy.posY - 1 && fire.posY <= enemy.posY)
//							&& (fire.posX <= enemy.posX + 1 && fire.posX >= enemy.posX - 1)) {
//
//					}
//				}
//			}
//		}
	}

	private void firePlayerWeapon(GL10 gl) {
		if (JTEngine.fire) {
			playerFire.add(new JTWeapon());
			JTEngine.fire = false;
		}
		Iterator<JTWeapon> itr = playerFire.iterator();
		while (itr.hasNext()) {
			JTWeapon fire = itr.next();
			if (fire.shotFired) {
				if (fire.posY > 10.25) {
					itr.remove();
				} else {
					fire.posY += JTEngine.PLAYER_BULLET_SPEED;
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(.1f, .1f, 0f);
					gl.glTranslatef(fire.posX, fire.posY, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.0f, 0f, 0.0f);
					fire.draw(gl, spriteSheets);
					gl.glPopMatrix();
					gl.glLoadIdentity();
				}
			}
		}
	}

	// private void initializeScouts() {
	// for (int x = 0; x < JTEngine.TOTAL_SCOUTS; x++) {
	// JTEnemy interceptor;
	// if (x >= JTEngine.TOTAL_SCOUTS / 2) {
	// interceptor = new JTEnemy(JTEngine.TYPE_SCOUT,
	// JTEngine.ATTACK_RIGHT);
	// } else {
	// interceptor = new JTEnemy(JTEngine.TYPE_SCOUT,
	// JTEngine.ATTACK_LEFT);
	// }
	// enemies.add(interceptor);
	// }
	// }
	//
	// private void initializeWarships() {
	// for (int x = 0; x < JTEngine.TOTAL_WARSHIPS; x++) {
	// JTEnemy interceptor = new JTEnemy(JTEngine.TOTAL_WARSHIPS,
	// JTEngine.ATTACK_RANDOM);
	// enemies.add(interceptor);
	// }
	// }

	private void moveEnemy(GL10 gl) {
		for (JTEnemy enemy : enemies) {
			if (!enemy.isDestroyed) {
				switch (enemy.enemyType) {
				case JTEngine.TYPE_INTERCEPTOR:
					if (enemy.posY < -0.1) {
						enemy.init();
					}
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					gl.glPushMatrix();
					gl.glScalef(.1f, .1f, 1f);
					if (!enemy.isLockedOn && enemy.posY >= 7) {
						enemy.posY -= JTEngine.INTERCEPTOR_SPEED;
					} else {
						// if (enemy.isLockedOn) {
						enemy.lockOnPosX = JTEngine.playerBankPosX;
						enemy.lockOnPosY = JTEngine.playerBankPosY;
						enemy.isLockedOn = true;
						if (enemy.posY - enemy.lockOnPosY > 0.1f) {
							enemy.posX = enemy.incrementToX();
							enemy.posY -= (JTEngine.INTERCEPTOR_SPEED * 4);
						} else if (enemy.posY - enemy.lockOnPosY < -0.1f) {
							enemy.posX = enemy.decrementToX();
							enemy.posY += (JTEngine.INTERCEPTOR_SPEED * 4);
							// } else {
							// enemy.posY += (JTEngine.INTERCEPTOR_SPEED * 4);
						}
					}
					gl.glTranslatef(enemy.posX, enemy.posY, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0f, 0f, 0.0f);
					enemy.draw(gl, spriteSheets);
					gl.glPopMatrix();
					gl.glLoadIdentity();

					break;
				case JTEngine.TYPE_SCOUT:
					break;
				case JTEngine.TYPE_WARSHIP:
					break;
				}
			}
		}
	}

	private void movePlayer1(GL10 gl) {
		switch (JTEngine.playerFlightActionY) {
		case JTEngine.PLAYER_BANK_FORWARD:
			if (JTEngine.playerBankPosY < 9) {
				JTEngine.playerBankPosY += JTEngine.PLAYER_BANK_SPEED;
			}
			break;
		case JTEngine.PLAYER_BANK_BACKWARD:
			if (JTEngine.playerBankPosY > 0) {
				JTEngine.playerBankPosY -= JTEngine.PLAYER_BANK_SPEED;
			}
			break;
		default:

		}

		switch (JTEngine.playerFlightAction) {
		case JTEngine.PLAYER_BANK_LEFT_1:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.1f, .1f, 1f);

			if (this.goodGuyBankFrames < JTEngine.PLAYER_FRAMES_BETWEEN_ANI
					&& JTEngine.playerBankPosX > 0) {
				JTEngine.playerBankPosX -= JTEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(JTEngine.playerBankPosX,
						JTEngine.playerBankPosY, 0);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(1 / 5f, 0.5f, 0.0f);
				goodGuyBankFrames += 1;
			} else if (this.goodGuyBankFrames >= JTEngine.PLAYER_FRAMES_BETWEEN_ANI
					&& JTEngine.playerBankPosX > 0) {
				JTEngine.playerBankPosX -= JTEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(JTEngine.playerBankPosX,
						JTEngine.playerBankPosY, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0 / 5f, 0.5f, 0);
			} else {
				gl.glTranslatef(JTEngine.playerBankPosX,
						JTEngine.playerBankPosY, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(2 / 5f, 0.5f, 0.0f);
			}
			// player1.draw(gl, spriteSheets);
			// gl.glPopMatrix();
			// gl.glLoadIdentity();
			break;
		case JTEngine.PLAYER_BANK_RIGHT_1:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.1f, .1f, 1f);
			if (goodGuyBankFrames < JTEngine.PLAYER_FRAMES_BETWEEN_ANI
					&& JTEngine.playerBankPosX < 9) {
				JTEngine.playerBankPosX += JTEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(JTEngine.playerBankPosX,
						JTEngine.playerBankPosY, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(3 / 5f, 0.5f, 0.0f);
				goodGuyBankFrames += 1;
			} else if (goodGuyBankFrames >= JTEngine.PLAYER_FRAMES_BETWEEN_ANI
					&& JTEngine.playerBankPosX < 9) {
				JTEngine.playerBankPosX += JTEngine.PLAYER_BANK_SPEED;
				gl.glTranslatef(JTEngine.playerBankPosX,
						JTEngine.playerBankPosY, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(4 / 5f, 0.5f, 0.0f);

			} else {
				gl.glTranslatef(JTEngine.playerBankPosX,
						JTEngine.playerBankPosY, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(2 / 5f, 0.5f, 0.0f);
			}
			// player1.draw(gl);
			// gl.glPopMatrix();
			// gl.glLoadIdentity();
			break;
		case JTEngine.PLAYER_RELEASE:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.1f, .1f, 1f);
			gl.glTranslatef(JTEngine.playerBankPosX, JTEngine.playerBankPosY,
					0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(2 / 5f, 0.5f, 0.0f);
			// player1.draw(gl);
			// gl.glPopMatrix();
			// gl.glLoadIdentity();
			goodGuyBankFrames += 1;

		default:
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.1f, .1f, 1f);
			gl.glTranslatef(JTEngine.playerBankPosX, JTEngine.playerBankPosY,
					0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(2 / 5f, 0.5f, 0.0f);
			// player1.draw(gl);
			// gl.glPopMatrix();
			// gl.glLoadIdentity();

			break;
		}
		player1.draw(gl, spriteSheets);
		gl.glPopMatrix();
		gl.glLoadIdentity();
		firePlayerWeapon(gl);

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
//		if (bgScroll1 == Float.MAX_VALUE)
//			bgScroll1 = 0f;
		/** reset the scale and translate of the Model matrix mode */
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
//		gl.glScalef(1f, 1f, 1f);
		gl.glTranslatef(0f, 0f, 0f);

		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);

		background.draw(gl);
		gl.glPopMatrix();
//		bgScroll1 += JTEngine.SCROLL_BACKGROUND_1;
		gl.glLoadIdentity();
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
			if (JTEngine.backgroundXPosition < (2/JTEngine.SCREEN_PROPORTION - 1)) {
				JTEngine.backgroundXPosition += JTEngine.SCROLL_BACKGROUND_1;
			}
			break;
		case JTEngine.PLAYER_RELEASE:
		default:
			break;
		}
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(0.5f*JTEngine.SCREEN_PROPORTION, 0.5f, 1f);
		gl.glTranslatef(JTEngine.backgroundXPosition, JTEngine.backgroundYPosition, 0f);

		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);
		backgroung1.draw(gl);
		gl.glPopMatrix();
		gl.glLoadIdentity();
	}

}
