package com.example.androidcoverview;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.coverviewlibrary.MCoverViewTool;

@EActivity(R.layout.test_layout)
public class TestActivity extends Activity {
	@ViewById(R.id.test_tv)
	TextView tv;
	@ViewById
	TextView edProgressbar, edImageview, edTextview, edButton;
	
	private MCoverViewTool mCoverViewTool;

	@Click({ R.id.testActy_showAll, R.id.testActy_order, R.id.testActy_margins })
	void onClick(View v) {
		switch (v.getId()) {
		case R.id.testActy_showAll:
			mCoverViewTool.mProgressBar.setVisibility(View.VISIBLE);
			mCoverViewTool.mImageView.setVisibility(View.VISIBLE);
			mCoverViewTool.mTextView.setVisibility(View.VISIBLE);
			mCoverViewTool.mButton.setVisibility(View.VISIBLE);
			
			break;
		case R.id.testActy_order:
			
			break;
		case R.id.testActy_margins:
			mCoverViewTool.setMarginTop(mCoverViewTool.mTextView, 50);
			break;
		}
	}

	@AfterViews
	void afterViews(){
		mCoverViewTool=new MCoverViewTool(TestActivity.this, tv);
		doRefresh();
	}

	@Background
	void doRefresh() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		afterRefresh();
	}

	@UiThread
	void afterRefresh() {
		mCoverViewTool.showNetWork();
	}
}
