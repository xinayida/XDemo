package com.example.floatview;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import com.example.DemoApp;
import com.example.R;

public class FloatActivity extends Activity implements OnClickListener {
	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowManagerParams = null;
	private FloatView floatView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȡ��������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��
//		setContentView(R.layout.activity_main);
		createView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onDestroy() {
		super.onDestroy();
		// �ڳ����˳�(Activity���٣�ʱ������������
		windowManager.removeView(floatView);
	}

	private void createView() {
		floatView = new FloatView(getApplicationContext());
		floatView.setOnClickListener(this);
		floatView.setImageResource(R.drawable.ic_launcher); // ����򵥵����Դ���icon������ʾ
		// ��ȡWindowManager
		windowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		// ����LayoutParams(ȫ�ֱ�������ز���
		windowManagerParams = ((DemoApp) getApplication()).getWindowParams();
		windowManagerParams.type = LayoutParams.TYPE_PHONE; // ����window type
		windowManagerParams.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��
		// ����Window flag
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * ע�⣬flag��ֵ����Ϊ�� LayoutParams.FLAG_NOT_TOUCH_MODAL ��Ӱ�������¼�
		 * LayoutParams.FLAG_NOT_FOCUSABLE ���ɾ۽� LayoutParams.FLAG_NOT_TOUCHABLE
		 * ���ɴ���
		 */
		// �����������������Ͻǣ����ڵ�������
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
		windowManagerParams.x = 0;
		windowManagerParams.y = 0;
		// �����������ڳ�������
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
		// ��ʾmyFloatViewͼ��
		windowManager.addView(floatView, windowManagerParams);
	}

	public void onClick(View v) {
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
	}
}
