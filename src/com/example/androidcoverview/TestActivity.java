package com.example.androidcoverview;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.coverviewlibrary.BaseActivity;
@EActivity
public class TestActivity extends BaseActivity{
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		init();
		cover(tv);
		doRefresh();
	}
	
	private void init(){
		setOnTapRefreshListener(new OnTapRefreshListener() {
			@Override
			public void onTapRefresh() {
				doRefresh();
			}
		});
		
		tv=(TextView) findViewById(R.id.test_tv);
		
	}
	
	@Background
	void doRefresh(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		afterRefresh();
	}
	
	@org.androidannotations.annotations.UiThread
	void afterRefresh(){
		stateRefreshFailed();
	}
}
