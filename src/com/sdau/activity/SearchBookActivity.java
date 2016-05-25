package com.sdau.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.astuetz.PagerSlidingTabStrip;
import com.sdau.fragment.SearchAdvancedFragment;
import com.sdau.fragment.SearchSimpleFragment;
import com.sdau.superlibrary.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class SearchBookActivity extends FragmentActivity {

	/**
	 * 聊天界面的Fragment
	 */
	private SearchSimpleFragment chatFragment;

	/**
	 * 发现界面的Fragment
	 */
	private SearchAdvancedFragment foundFragment;


	/**
	 * PagerSlidingTabStrip的实例
	 */
	private PagerSlidingTabStrip tabs;

	/**
	 * 获取当前屏幕的密度
	 */
	private DisplayMetrics dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_book);
		setOverflowShowingAlways();
		dm = getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs.setViewPager(pager);
		setTabsValue();
		/*setTitle("馆藏查询");
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// setOverflowShowingAlways();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab = actionBar.newTab().setText("简单查询").setTabListener(
				new SearchTabListener<SearchSimpleFragment>(this, "artist", SearchSimpleFragment.class));
		actionBar.addTab(tab);
		tab = actionBar.newTab().setText("高级检索").setTabListener(
				new SearchTabListener<SearchAdvancedFragment>(this, "album", SearchAdvancedFragment.class));
		actionBar.addTab(tab);*/
	}
	
	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 18, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "简单查询", "高级检索" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (chatFragment == null) {
					chatFragment = new SearchSimpleFragment();
				}
				return chatFragment;
			case 1:
				if (foundFragment == null) {
					foundFragment = new SearchAdvancedFragment();
				}
				return foundFragment;
			/*case 2:
				if (contactsFragment == null) {
					contactsFragment = new ContactsFragment();
				}
				return contactsFragment;*/
			default:
				return null;
			}
		}

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
	

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		for ( MyOntouchListener listener : touchListeners )
		{
			listener.onTouchEvent( event );
		}
		return super.onTouchEvent( event );
	}
	
	private ArrayList<MyOntouchListener> touchListeners = new ArrayList<SearchBookActivity.MyOntouchListener>();


	public void registerListener(MyOntouchListener listener)
	{
		touchListeners.add( listener );
	}


	public void unRegisterListener(MyOntouchListener listener)
	{
		touchListeners.remove( listener );
	}

	public interface MyOntouchListener
	{
		public void onTouchEvent(MotionEvent event);
	}
	*/
	
}
