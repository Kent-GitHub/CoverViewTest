package com.example.androidcoverview;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.example.coverviewlibrary.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.activity_main)
public class MainActivity extends  BaseActivity{
	private List<String> mDatas;
	private BaseAdapter mAdapter;
	
	@ViewById(R.id.listView)
	ListView mListView;
	
	private final int REFRESH_SUCCEED =1;
	private final int REFRESH_FAILED=2;
	private final int REFRESH_NO_DATAS=3;
	
	private int refresh_result = 2;
	
	@CheckedChange({R.id.radioButton1,R.id.radioButton2,R.id.radioButton3})
	void onCheckChanged(CompoundButton button,boolean isChecked){
		if (isChecked) {
			switch (button.getId()) {
			case R.id.radioButton1: 
				refresh_result=REFRESH_SUCCEED; 
				break;
			case R.id.radioButton2: 
				refresh_result=REFRESH_FAILED; 
				break;
			case R.id.radioButton3: 
				refresh_result=REFRESH_NO_DATAS; 
				break;
			}
		}
	}
	
	@AfterViews
	void afterViews() {
		initDatas();
		mListView.setAdapter(mAdapter);
		
		setOnTapRefreshListener(new OnTapRefreshListener() {
			
			@Override
			public void onTapRefresh() {
				refreshingDatas();
			}
		});
		
		cover(mListView);
		refreshingDatas();
	}
	
	private void initDatas() {
		mDatas=new ArrayList<String>();
		for (int i = 0; i < 61; i++) {
			mDatas.add("Item : "+i);
		}
		mAdapter=new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView tv=new TextView(MainActivity.this);
				tv.setText(mDatas.get(position));
				tv.setHeight(60);
				tv.setGravity(Gravity.CENTER_VERTICAL);
				return tv;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return mDatas.get(position);
			}
			
			@Override
			public int getCount() {
				return mDatas.size();
			}
		};
	}

	@Click({ R.id.btn_cover1,R.id.btn_secActy, R.id.btn_unCover})
	void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cover1:
			cover(mListView);
			refreshingDatas();
			break;
		case R.id.btn_secActy:
			TestActivity_.intent(this).start();
			break;
		case R.id.btn_unCover:
			removeCover();
			break;
		}
	}
	
	@Background
	void refreshingDatas(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		refreshingFinish();
	}
	
	
	@UiThread
	void refreshingFinish(){
		switch (refresh_result) {
		case REFRESH_SUCCEED:
			stateRefreshSucceeded();
			break;
		case REFRESH_FAILED:
			stateRefreshFailed();
			break;
		case REFRESH_NO_DATAS:
			stateRefreshNoDatas();
			break;
		}
	}
	
}
