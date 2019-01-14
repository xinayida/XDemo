package com.example.rotatelayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

public class RotateLayerActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		RotateLayer mRotateLayer = new RotateLayer(this,null);
//		ImageView view = new ImageView(this);
//		view.setBackgroundResource(R.drawable.background_1);
//		mRotateLayer.addView(view);
//
//		view = new ImageView(this);
//		view.setBackgroundResource(R.drawable.background_2);
//		mRotateLayer.addView(view);
//
//		view = new ImageView(this);
//		view.setBackgroundResource(R.drawable.background_3);
//		mRotateLayer.addView(view);
//
//		view = new ImageView(this);
//		view.setBackgroundResource(R.drawable.background_4);
//		mRotateLayer.addView(view);
//
//		setContentView(mRotateLayer);
        setContentView(R.layout.activity_rotate_3d);
        ViewGroup viewById = (ViewGroup) findViewById(R.id.roat_main);
        for (int i = 0; i < viewById.getChildCount(); i++) {
            if (viewById.getChildAt(i) instanceof TextView) {
                TextView tx = (TextView) viewById.getChildAt(i);
                tx.setOnClickListener(this);
            }
        }
        RecyclerView reclerview = (RecyclerView) findViewById(R.id.test_recylerview);
        reclerview.setLayoutManager(new GridLayoutManager(reclerview.getContext(), Integer.valueOf(2)));
        reclerview.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(RotateLayerActivity.this);
                RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(textView) {
                };
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView).setText("item=====" + position);
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, ((TextView) v).getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
