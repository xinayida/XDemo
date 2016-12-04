package com.example.rotatelayer;

import com.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class RotateLayerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RotateLayer mRotateLayer = new RotateLayer(this,null);
		ImageView view = new ImageView(this);
		view.setBackgroundResource(R.drawable.background_1);
		mRotateLayer.addView(view);
		
		view = new ImageView(this);
		view.setBackgroundResource(R.drawable.background_2);
		mRotateLayer.addView(view);
		
		view = new ImageView(this);
		view.setBackgroundResource(R.drawable.background_3);
		mRotateLayer.addView(view);
		
		view = new ImageView(this);
		view.setBackgroundResource(R.drawable.background_4);
		mRotateLayer.addView(view);
		
		setContentView(mRotateLayer);
	}
}
