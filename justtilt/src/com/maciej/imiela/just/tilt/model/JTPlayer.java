/**
 * 
 */
package com.maciej.imiela.just.tilt.model;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.FloatMath;

/**
 * @author Macimi
 *
 */
public abstract class JTPlayer extends JTDrawable {
		
	@Override
	protected void init() {
		float texture[] = {//
				0.0f, 0.0f,//
				1 / 5f, 0.0f,//
				1 / 5f, 0.5f,//
				0.0f, 0.5f,//
		};
		super.texture = texture;
		super.init();
	}
	
	public float determineRotateAngle(float lockOnPosX, float lockOnPosY) {
		Point u = new Point(0, 1);
		float uLength = 1;
		PointF w = new PointF(lockOnPosX, lockOnPosY);
		float wLength = FloatMath.sqrt(w.x*w.x + w.y*w.y);
		float cos = (u.x*w.x + u.y*w.y)/(uLength*wLength);
		if (w.x > 0){
			return 360f - (float)(Math.acos(cos) * 360 / (2*Math.PI));
		} else {
			return (float) (Math.acos(cos) * 360 / (2*Math.PI));
		}
	}
	
	public float determineRotateAngle(PointF w) {
		Point u = new Point(0, 1);
		float uLength = 1;
		float wLength = FloatMath.sqrt(w.x*w.x + w.y*w.y);
		float cos = (u.x*w.x + u.y*w.y)/(uLength*wLength);
		if (w.x > 0){
			return 360f - (float)(Math.acos(cos) * 360 / (2*Math.PI));
		} else {
			return (float) (Math.acos(cos) * 360 / (2*Math.PI));
		}
	}
	
	@Override
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
