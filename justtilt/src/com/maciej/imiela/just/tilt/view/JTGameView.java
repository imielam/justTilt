package com.maciej.imiela.just.tilt.view;

import com.maciej.imiela.just.tilt.controller.JTGameRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
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
public class JTGameView extends GLSurfaceView{
	private JTGameRenderer renderer;

	public JTGameView(Context context) {
		super(context);
		renderer = new JTGameRenderer();
		setRenderer(renderer);
		// TODO Auto-generated constructor stub
	}

	public JTGameRenderer getRenderer() {
		return renderer;
	}

}
