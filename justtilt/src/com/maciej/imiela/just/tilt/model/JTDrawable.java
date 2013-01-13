package com.maciej.imiela.just.tilt.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;


public abstract class JTDrawable {
	public PointF currentPosition;
	
	protected FloatBuffer vertexBuffer;
	protected FloatBuffer textureBuffer;
	protected ByteBuffer indexBuffer;
	/**
	 * Default vertixes definig position
	 */
	protected float vertices[] = { //
			-0.5f, -0.5f, 0.0f, //
			0.5f, -0.5f, 0.0f, //
			0.5f, 0.5f, 0.0f, //
			-0.5f, 0.5f, 0.0f, };
	/**
	 * Defult texture window
	 */
	protected float texture[];
	protected byte indices[] = { 0, 1, 2, 0, 2, 3, };
	public float alfa = 0f; //tg alfa of the line which enemy is moving
	protected int numberSpriteShite;
	
	
	public void draw(GL10 gl, int[] spriteSheet){
		
			gl.glBindTexture(GL10.GL_TEXTURE_2D, spriteSheet[numberSpriteShite]);
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
	
	protected void init() {
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
	
	public void move(GL10 gl, int[] spriteSheets, PointF textureStart){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(.1f, .1f, 0f);
		gl.glTranslatef(this.currentPosition.x, this.currentPosition.y, 0f);
		gl.glRotatef(alfa, 0, 0, 1);
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(textureStart.x, textureStart.y, 0.0f);
		this.draw(gl, spriteSheets);
		gl.glPopMatrix();
		gl.glLoadIdentity();
	}
	
}