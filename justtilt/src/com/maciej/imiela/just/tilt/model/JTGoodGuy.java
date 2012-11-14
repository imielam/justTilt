package com.maciej.imiela.just.tilt.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
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

	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer;
//	private int[] textures = new int[1];

	private float vertices[] = { //
	0.0f, 0.0f, 0.0f,//
			1.0f, 0.0f, 0.0f,//
			1.0f, 1.0f, 0.0f,//
			0.0f, 1.0f, 0.0f, };

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

//	public void loadTexture(GL10 gl, int texture, Context context) {
//
//		InputStream imagestream = context.getResources().openRawResource(
//				texture);
//		Bitmap bitmap = null;
//		try {
//			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//            bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
//			bitmap = BitmapFactory.decodeStream(imagestream, null, bitmapOptions);
//		} catch (Exception e) {
//		} finally {
//			try {
//				imagestream.close();
//				imagestream = null;
//			} catch (IOException e) {
//			}
//		}
//		gl.glGenTextures(1, textures, 0);
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
//				GL10.GL_NEAREST);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
//				GL10.GL_LINEAR);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
//				GL10.GL_REPEAT);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
//				GL10.GL_REPEAT);
//		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//		bitmap.recycle();
//
//	}
}
