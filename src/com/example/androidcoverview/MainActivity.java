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

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coverviewlibrary.CoverViewTool;
import com.example.coverviewlibrary.CoverViewTool.OnTapRefreshListener;

@EActivity(R.layout.activity_main)
public class MainActivity extends  Activity{
	private List<String> mDatas;
	private BaseAdapter mAdapter;
	private CoverViewTool mCoverViewTool;
	@ViewById(R.id.listView)
	ListView mListView;
	
	private int refresh_result = CoverViewTool.REFRESH_NetworkError;
	
	@CheckedChange({R.id.radioButton1,R.id.radioButton2,R.id.radioButton3,R.id.radioButton4})
	void onCheckChanged(CompoundButton button,boolean isChecked){
		if (isChecked) {
			switch (button.getId()) {
			case R.id.radioButton1: 
				refresh_result=CoverViewTool.REFRESH_Succeed; 
				break;
			case R.id.radioButton2: 
				refresh_result=CoverViewTool.REFRESH_NetworkError; 
				break;
			case R.id.radioButton3: 
				refresh_result=CoverViewTool.REFRESH_NoDatas; 
				break;
			case R.id.radioButton4:
				refresh_result=CoverViewTool.REFRESH_Crash;
			}
		}
	}
	
	@AfterViews
	void afterViews() {
		initDatas();
		mCoverViewTool=new CoverViewTool(this);
		
		mListView.setAdapter(mAdapter);
		
		mCoverViewTool.setOnTapRefreshListener(new OnTapRefreshListener() {
			
			@Override
			public void onTapRefresh() {
				refreshingDatas();
			}
		});
		
		mCoverViewTool.cover(mListView);
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
			mCoverViewTool.cover(mListView);
			refreshingDatas();
			break;
		case R.id.btn_secActy:
			TestActivity_.intent(this).start();
			break;
		case R.id.btn_unCover:
			mCoverViewTool.removeCover(false);
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
		case CoverViewTool.REFRESH_Succeed:
			mCoverViewTool.stateRefreshSucceeded();
			break;
		case CoverViewTool.REFRESH_NetworkError:
			mCoverViewTool.stateRefreshFailed(CoverViewTool.REFRESH_NetworkError);
			break;
		case CoverViewTool.REFRESH_NoDatas:
			mCoverViewTool.stateRefreshFailed(CoverViewTool.REFRESH_NoDatas);
			break;
		case CoverViewTool.REFRESH_Crash:
			mCoverViewTool.stateRefreshFailed(CoverViewTool.REFRESH_Crash);
			break;
		}
	}
	
}
