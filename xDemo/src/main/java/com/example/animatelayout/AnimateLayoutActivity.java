package com.example.animatelayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.R;

import java.util.Random;

/**
 * Created by Stefan on 2020/6/28.
 */
public class AnimateLayoutActivity extends Activity implements View.OnClickListener {
    private ViewContainer viewContainer;
    private MessagePopLayout messagePopLayout;
    private int[] colors = new int[]{Color.WHITE, Color.BLUE, Color.RED, Color.GREEN, Color.GRAY};
    private CharSequence[] messages = new CharSequence[]{"123", "一二三四五六七八九⑩一二三四五六七八九⑩一二三四五六七八九⑩一二三四五六七八九⑩一二三四五六七八九⑩一二三四五六七八九⑩"};
    private Random random = new Random();
    private int count = 0, msgCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_layout);
        viewContainer = findViewById(R.id.anim_layout_container);
        messagePopLayout = findViewById(R.id.anim_layout_msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anim_layout_add:
                viewContainer.addView(genView());
                break;
            case R.id.add_msg:
                messagePopLayout.addMsg(messages[msgCount % 2]);
                msgCount++;
                break;
            default:
                viewContainer.removeView(v);
                break;

        }
    }

    private View genView() {
        count++;
        TextView textView = new TextView(this);
        textView.setText("" + count);
        textView.setBackgroundColor(colors[random.nextInt(colors.length)]);
        textView.setOnClickListener(this);
        return textView;
    }
}
