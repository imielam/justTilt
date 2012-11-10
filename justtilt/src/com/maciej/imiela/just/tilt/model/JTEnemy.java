package com.maciej.imiela.just.tilt.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
//TODO Change the class to extend class JTDrawable(chreate it first)
public class JTEnemy {

	// TODO Don't like the public variables, should be private with getter and
	// setter. Remeber to change this.
	public float posY = 0f; // the x position of the enemy
	public float posX = 0f; // the y position of the enemy
	public float posT = 0f; // the t used in calculating a Bezier curve
	public float incrementXToTarget = 0f; // the x increment to reach a
											// potential target
	public float incrementYToTarget = 0f; // the y increment to reach a
											// potential target

	public int attackDirection = 0; // the attack direction of the ship
	public boolean isDestroyed = false; // has this ship been destroyed?
	public int enemyType = 0; // what type of enemy is this?

	public boolean isLockedOn = false; // had the enemy locked on to a target?
	public float lockOnPosX = 0f; // x position of the target
	public float lockOnPosY = 0f; // y position of the target

	private Random randomPos = new Random();

	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer;
	private float vertices[] = { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 1.0f, 0.0f, };
	private float texture[] = { 0.0f, 0.0f, //
			1/5f, 0.0f, //
			1/5f, 0.5f, //
			0.0f, 0.5f, };
	private byte indices[] = { 0, 1, 2, 0, 2, 3, };

	public void init(){
		posY = (randomPos.nextFloat() * 9) + 9;
		switch (attackDirection) {
		case JTEngine.ATTACK_LEFT:
			posX = 0;
			break;
		case JTEngine.ATTACK_RANDOM:
			posX = randomPos.nextFloat() * 9;
			break;
		case JTEngine.ATTACK_RIGHT:
			posX = 9;
			break;
		}
		posT = JTEngine.SCOUT_SPEED;
		lockOnPosX = 0;
		lockOnPosY = 0;
		isLockedOn = false;
	}
	
	public JTEnemy(int type, int direction) {
		enemyType = type;
		attackDirection = direction;
//		posY = (randomPos.nextFloat() * 9) + 9;
//		switch (attackDirection) {
//		case JTEngine.ATTACK_LEFT:
//			posX = 0;
//			break;
//		case JTEngine.ATTACK_RANDOM:
//			posX = randomPos.nextFloat() * 9;
//			break;
//		case JTEngine.ATTACK_RIGHT:
//			posX = 9;
//			break;
//		}
//		posT = JTEngine.SCOUT_SPEED;
		this.init();

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
	
	public float incrementToX() {
//		return ((JTEngine.INTERCEPTOR_SPEED * 4) - posY - lockOnPosY) *  (lockOnPosX - posX) / (-lockOnPosY + posY);
		return (posX - lockOnPosX)*(posY - JTEngine.INTERCEPTOR_SPEED * 4 - lockOnPosY)/(posY - lockOnPosY) + lockOnPosX;
	}
	
	public float decrementToX() {
		return(lockOnPosX - posX)*(JTEngine.INTERCEPTOR_SPEED * 4) / (lockOnPosY - posY) + posX;
	}

	public float getNextScoutX() {
		if (attackDirection == JTEngine.ATTACK_LEFT) {
			return (float) ((JTEngine.BEZIER_X_4 * (posT * posT * posT))
					+ (JTEngine.BEZIER_X_3 * 3 * (posT * posT) * (1 - posT))
					+ (JTEngine.BEZIER_X_2 * 3 * posT * ((1 - posT) * (1 - posT))) + (JTEngine.BEZIER_X_1 * ((1 - posT)
					* (1 - posT) * (1 - posT))));
		} else {
			return (float) ((JTEngine.BEZIER_X_1 * (posT * posT * posT))
					+ (JTEngine.BEZIER_X_2 * 3 * (posT * posT) * (1 - posT))
					+ (JTEngine.BEZIER_X_3 * 3 * posT * ((1 - posT) * (1 - posT))) + (JTEngine.BEZIER_X_4 * ((1 - posT)
					* (1 - posT) * (1 - posT))));
		}
	}

	public float getNextScoutY() {
		return (float) ((JTEngine.BEZIER_Y_1 * (posT * posT * posT))
				+ (JTEngine.BEZIER_Y_2 * 3 * (posT * posT) * (1 - posT))
				+ (JTEngine.BEZIER_Y_3 * 3 * posT * ((1 - posT) * (1 - posT))) + (JTEngine.BEZIER_Y_4 * ((1 - posT)
				* (1 - posT) * (1 - posT))));
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
}
