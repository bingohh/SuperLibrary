package com.sdau.fragment;

import com.sdau.superlibrary.R;

import android.app.ActionBar.LayoutParams;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchAdvancedFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View searchAdvancedLayout = inflater.inflate(R.layout.fragment_search_advanced, container, false);
		/*TextView textView=new TextView(getActivity());
		textView.setText("Advanced Search Fragment");
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout layout=new LinearLayout(getActivity());
		LayoutParams params =new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layout.addView(textView,params);*/
		return searchAdvancedLayout;
	}
	
}










