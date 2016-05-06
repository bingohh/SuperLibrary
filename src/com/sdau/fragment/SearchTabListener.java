package com.sdau.fragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class SearchTabListener<T extends Fragment> implements ActionBar.TabListener{

	private Fragment mFragment;
	private final Activity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	public SearchTabListener(Activity activity,String tag,Class<T> clz){
		mActivity=activity;
		mTag=tag;
		mClass=clz;
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(mFragment==null){
			mFragment=Fragment.instantiate(mActivity, mClass.getName());
			ft.add(android.R.id.content, mFragment,mTag);
		}else{
			ft.attach(mFragment);
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if(mFragment !=null){
			ft.detach(mFragment);
		}
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
