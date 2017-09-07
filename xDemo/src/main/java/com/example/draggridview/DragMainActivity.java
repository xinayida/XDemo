package com.example.draggridview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.example.R;
import com.example.framework.DragGridView;

/**
 * @blog http://blog.csdn.net/xiaanming 
 * 
 * @author xiaanming
 *
 */
public class DragMainActivity extends Activity {
	private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_grid_main);

		DragGridView mDragGridView = (DragGridView) findViewById(R.id.dragGridView);
		for (int i = 0; i < 15; i++) {
			HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
			itemHashMap.put("item_image",R.drawable.com_tencent_open_notice_msg_icon_big);
			itemHashMap.put("item_text", "拖拽 " + Integer.toString(i));
			dataSourceList.add(itemHashMap);
		}
		
		final DragAdapter mDragAdapter = new DragAdapter(this, dataSourceList);
		
		mDragGridView.setAdapter(mDragAdapter);
	}
	

}
