package com.maciej.imiela.just.tilt.model;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;


public class JTBackground  extends JTDrawable{
	public JTBackground(int number, PointF coords) {
		super.currentPosition = coords;
		float texture[] = { 0f, 1f, 0.0f, 0.0f, 1.0f, 0f, 1, 1.0f, };
		super.texture = texture;
		
		float vertices[] = {0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f, 1.0f, 0.0f, };
		super.vertices = vertices;
		
		super.numberSpriteShite = number;
		super.init();
	}
	
	public void move(GL10 gl, int[] spriteSheets, PointF textureStart, PointF scale) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(scale.x, scale.y, 1f);
		gl.glTranslatef(currentPosition.x,
				currentPosition.y, 0f);

		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(textureStart.x, textureStart.y, 0.0f);
		this.draw(gl, spriteSheets);
		gl.glPopMatrix();
		gl.glLoadIdentity();
	}

}
