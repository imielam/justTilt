package com.maciej.imiela.just.tilt.view;

import com.maciej.imiela.just.tilt.controller.JTGameRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class JTGameView extends GLSurfaceView{

	public JTGameView(Context context) {
		super(context);
		JTGameRenderer renderer = new JTGameRenderer();
		setRenderer(renderer);
		// TODO Auto-generated constructor stub
	}

}
