package com.sdau.activity;

import com.sdau.fragment.SearchAdvancedFragment;
import com.sdau.fragment.SearchSimpleFragment;
import com.sdau.fragment.SearchTabListener;
import com.sdau.superlibrary.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SearchBookActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_book);
		setTitle("馆藏查询");
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// setOverflowShowingAlways();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab = actionBar.newTab().setText("简单查询").setTabListener(
				new SearchTabListener<SearchSimpleFragment>(this, "artist", SearchSimpleFragment.class));
		actionBar.addTab(tab);
		tab = actionBar.newTab().setText("高级检索").setTabListener(
				new SearchTabListener<SearchAdvancedFragment>(this, "album", SearchAdvancedFragment.class));
		actionBar.addTab(tab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_book, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
