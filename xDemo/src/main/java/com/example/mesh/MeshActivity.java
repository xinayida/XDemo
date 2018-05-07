package com.example.mesh;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.R;

/**
 * Created by ww on 2017/9/20.
 */

public class MeshActivity extends Activity {

    private AnimView mSampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);

        mSampleView = new AnimView(this);
        mSampleView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        mSampleView.setImageResource(R.drawable.screenshot);
        Button btn = new Button(this);
        btn.setText("Up");
        btn.setTextSize(20.0f);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, -2));
        btn.setOnClickListener(new View.OnClickListener() {
            boolean mReverse = false;

            @Override
            public void onClick(View v) {
                if (mSampleView.startAnim(true, mReverse)) {
                    mReverse = !mReverse;
                }
            }
        });
        Button down = new Button(this);
        down.setText("Down");
        down.setTextSize(20.0f);
        down.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, -2));
        down.setOnClickListener(new View.OnClickListener() {
            boolean mReverse = false;

            @Override
            public void onClick(View v) {
                if (mSampleView.startAnim(false, mReverse)) {
                    mReverse = !mReverse;
                }
            }
        });

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.addView(btn);
        linearLayout.addView(down);
        linearLayout.addView(mSampleView);

        setContentView(linearLayout);
    }
}