package com.example.androidcoverview;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coverviewlibrary.CoverViewTool;
import com.example.coverviewlibrary.CoverViewTool.OnTapRefreshListener;
@EActivity
public class TestActivity extends Activity{
	private TextView tv;
	private CoverViewTool mCoverViewTool;
	@ViewById
	TextView edProgressbar,edImageview,edTextview,edButton;
	
	@Click({R.id.testActy_showAll,R.id.testActy_order,R.id.testActy_margins})
	void onClick(View v){
		switch (v.getId()) {
		case R.id.testActy_showAll:
			mCoverViewTool.mCoverView.showProgressBar();
			mCoverViewTool.mCoverView.showImageView();
			mCoverViewTool.mCoverView.showTextView();
			mCoverViewTool.mCoverView.showButton();
			break;
		case R.id.testActy_order:
			mCoverViewTool.setCoverViewOrder(Integer.parseInt(edProgressbar.getText().toString()), Integer.parseInt(edImageview.getText().toString()),
					Integer.parseInt(edTextview.getText().toString()), Integer.parseInt(edButton.getText().toString()));
			break;
		case R.id.testActy_margins:
			mCoverViewTool.setMargins(mCoverViewTool.mCoverView.mTextView, 200, 300, 0, 0);
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		mCoverViewTool=new CoverViewTool(this);
		init();
		mCoverViewTool.cover(tv);
		doRefresh();
	}
	
	private void init(){
		mCoverViewTool.setOnTapRefreshListener(new OnTapRefreshListener() {
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
	
	@UiThread
	void afterRefresh(){
		mCoverViewTool.stateRefreshFailed(CoverViewTool.REFRESH_NetworkError);
	}
}
