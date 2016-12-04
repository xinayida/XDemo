package com.example;

import android.app.Application;
import android.view.WindowManager;

public class DemoApp extends Application {
	private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getWindowParams() {
		return windowParams;
	}
}
