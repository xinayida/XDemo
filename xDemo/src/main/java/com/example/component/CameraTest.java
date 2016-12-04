package com.example.component;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.R;

public class CameraTest extends Activity {

	private String imgPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator + "t.jpg";

    private String videoPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "t.vc";


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_view);

	}

	public void takePic(View v) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgPath)));
		startActivityForResult(intent, 1);
	}
	
	public void takeVideo(View v){
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(videoPath)));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);//0 low , 1 high
		startActivityForResult(intent, 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Toast.makeText(this, "result " + resultCode , 1000).show();

        if(resultCode != RESULT_OK) return;
		if (requestCode == 1) {
			
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 1;
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imgPath, opts);
			int w = opts.outWidth;
			int h = opts.outHeight;

			int tarW = (int) getResources().getDisplayMetrics().density * 200;
			int tarH = tarW;
			opts.inSampleSize = Math.max(w, h) / tarW;
			opts.inJustDecodeBounds = false;
			
			Bitmap img = BitmapFactory.decodeFile(imgPath, opts);
			((ImageView)findViewById(R.id.imageView)).setImageBitmap(img);

		}else (requestCode == 2){

        }
	}
}
