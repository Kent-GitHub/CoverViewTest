package com.example.androidcoverview;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.activity_main)
public class MainActivity extends  BaseActivity{
	private List<String> mDatas;
	private BaseAdapter mAdapter;
	private int screenHeight;
	private int screenWidth;
	
	private String tag="MainActivity";
	
	@ViewById(R.id.listView)
	ListView mListView;
	
	@ViewById
	TextView setCoverTop,setCoverBottom,setCoverLeft,setCoverRight;
	
	@AfterViews
	void afterViews() {
		initDatas();
		DisplayMetrics dm = new DisplayMetrics();       
		getWindowManager().getDefaultDisplay().getMetrics(dm);       
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels; 
		mListView.setAdapter(mAdapter);
		setOnTapRefreshListener(new OnTapRefreshListener() {
			
			@Override
			public void onTapRefresh() {
				refreshingDatas();
			}
		});
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

	@Click({ R.id.btn_cover1,R.id.btn_cover2, R.id.btn_unCover})
	void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cover1:
			cover(mListView);
			mCoverView.mButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					refreshing();
					refreshingDatas2();
				}
			});
			break;
		case R.id.btn_cover2:
			cover(getTextViewNum(setCoverTop),getTextViewNum(setCoverBottom),getTextViewNum(setCoverLeft),getTextViewNum(setCoverRight));
			mCoverView.mButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					refreshing();
					refreshingDatas2();
				}
			});
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
		finished();
	}
	
	@Background
	void refreshingDatas2(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		failed();
	}
	
	
	@UiThread
	void failed(){
		refreshFailed();
	}
	
	@UiThread
	void finished(){
		refreshSucceeded();
	}
	
	private int getTextViewNum(TextView tv){
		int num=0;
		try {
			num=Integer.parseInt(tv.getText().toString());
			return num;
		} catch (Exception e) {
			
		}
		switch (tv.getId()) {
		case R.id.set_cover_bottom:
			return screenHeight;
		case R.id.set_cover_right:
			return screenWidth;
	}
		return num;
	}
	
}
