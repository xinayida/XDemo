package com.example.silentinstall;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SilentInstallActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		ViewGroup.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		final EditText et = new EditText(this);
		et.setText("/mnt/sdcard/cri_radio.apk");
		final EditText pkget = new EditText(this);
		pkget.setText("com.crionline.radio");

		final TextView tv = new TextView(this);
		tv.setLines(5);

		Button btnInstall = new Button(this);
		btnInstall.setText("Install");
		btnInstall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(install(et.getText().toString()));
			}
		});

		Button btnUninstall = new Button(this);
		btnUninstall.setText("Uninstall");
		btnUninstall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(uninstall(pkget.getText().toString()));
			}
		});

		layout.addView(et, lp);
		layout.addView(btnInstall, lp);
		layout.addView(pkget,lp);
		layout.addView(btnUninstall, lp);
		layout.addView(tv);
		setContentView(layout);
	}

	/**
	 * Install APK
	 * 
	 * @param filepath
	 *            /mnt/sdcard/ApiDemos.apk
	 */
	private String install(String apkAbsolutePath) {

		String[] commands = {
				"LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " +apkAbsolutePath};
		
		return execCommand(commands);
	}

	/**
	 * Uninstall APK
	 * 
	 * @param packageName
	 *            com.example.demo
	 */
	private String uninstall(String packageName) {
		String[] commands = {"LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + packageName};
		return execCommand(commands);
	}
	
	/**
	 * 静默安装
	 * @param file
	 * @return
	 */
	public String execCommand(String[] commands) {
		Process process = null;
		OutputStream out = null;
		InputStream in = null;
		String result = "";
		try {
			process = Runtime.getRuntime().exec("sh");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			for(String command:commands){
				dataOutputStream.writeBytes(command);
			}
			// 提交命令
			dataOutputStream.flush();
			// 关闭流操作
			dataOutputStream.close();
			out.close();
			
//			int value = process.waitFor();
//			
//			// 代表成功
//			if (value == 0) {
//				result = true;
//			} else if (value == 1) { // 失败
//				result = false;
//			} else { // 未知情况
//				result = false;
//			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			in = process.getErrorStream();
			while ((read = in.read()) != -1) {
				baos.write(read);
			}
			in = process.getInputStream();
			while ((read = in.read()) != -1) {
				baos.write(read);
			}
			
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		return result;
	}
}
