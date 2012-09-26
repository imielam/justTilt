package com.maciej.imiela.just.tilt.controller;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.maciej.imiela.just.tilt.logic.JTBackground;
import com.maciej.imiela.just.tilt.logic.JTEngine;

import android.opengl.GLSurfaceView.Renderer;

public class JTGameRenderer implements Renderer{

	private JTBackground background = new JTBackground();
	
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		/**enabling texture mapping*/
		gl.glEnable(GL10.GL_TEXTURE_2D);
		/**inform openGL to test depth of every object onn the surface*/
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		/**activate blending feature*/
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		
		background.loadTexture(gl, JTEngine.BACKGROUND_LAYER_ONE, JTEngine.context);
		
		//TODO load game textures
	}

}
