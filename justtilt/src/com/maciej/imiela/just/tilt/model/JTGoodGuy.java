package com.maciej.imiela.just.tilt.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Point;
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
public class JTGoodGuy {

	public static volatile float rotationAngle = 0f;
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer;
//	private int[] textures = new int[1];

	private float vertices[] = { -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f,
			0.5f, 0.0f, -0.5f, 0.5f, 0.0f, };
	
//	private float vertices[] = { //
//	0.0f, 0.0f, 0.0f,//
//			1.0f, 0.0f, 0.0f,//
//			1.0f, 1.0f, 0.0f,//
//			0.0f, 1.0f, 0.0f, };

	private float texture[] = {//
	0.0f, 0.0f,//
			1 / 5f, 0.0f,//
			1 / 5f, 0.5f,//
			0.0f, 0.5f,//
	};

	private byte indices[] = { 0, 1, 2, 0, 2, 3, };

	public JTGoodGuy() {
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
	
	public static void determineRotateAngle(float lockOnPosX, float lockOnPosY) {
		Point u = new Point(0, 1);
		float uLength = 1;
		PointF w = new PointF(lockOnPosX, lockOnPosY);
		float wLength = FloatMath.sqrt(w.x*w.x + w.y*w.y);
		float cos = (u.x*w.x + u.y*w.y)/(uLength*wLength);
		if (w.x > 0){
			rotationAngle = 360f - (float)(Math.acos(cos) * 360 / (2*Math.PI));
		} else {
			rotationAngle = (float) (Math.acos(cos) * 360 / (2*Math.PI));
		}
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
