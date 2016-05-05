package com.sdau.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sdau.superlibrary.R;
import com.sdau.fragment.AboutMeFragment;
import com.sdau.fragment.ChatsFragment;
import com.sdau.fragment.ContactsFragment;
import com.sdau.fragment.DiscoverFragment;
import com.sdau.fragment.OnFragmentInteractionListener;
import com.sdau.gradient.GradientIconView;
import com.sdau.gradient.GradientTextView;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends FragmentActivity
	implements View.OnClickListener, ViewPager.OnPageChangeListener, OnFragmentInteractionListener {

	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private List<GradientIconView> mTabIconIndicator = new ArrayList<GradientIconView>();
	private List<GradientTextView> mTabTextIndicator = new ArrayList<GradientTextView>();
	private GradientIconView mChatsIconView;
	private GradientIconView mContactsIconView;
	private GradientIconView mDiscoverIconView;
	private GradientIconView mAboutMeIconView;

	private GradientTextView mTvChats;
	private GradientTextView mTvContacts;
	private GradientTextView mTvDiscover;
	private GradientTextView mTvAboutMe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("超级图书馆");
		ActionBar actionBar =getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);//禁用ActionBar左侧箭头导航功能
		setOverflowShowingAlways();
		initView();
		/*actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab=actionBar
				.newTab()
				.setText("artistString")
				.setTabListener(new TabListener<ArtistFragment>(this,"artist",ArtistFragment.class) );
		actionBar.addTab(tab);
		tab=actionBar
				.newTab()
				.setText("albumString")
				.setTabListener(new TabListener<AlbumFragment>(this,"album",AlbumFragment.class) );
		actionBar.addTab(tab);*/
	}
	
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		mChatsIconView = (GradientIconView) findViewById(R.id.id_iconfont_chat);
		mChatsIconView.setOnClickListener(this);
		mTabIconIndicator.add(mChatsIconView);
		mChatsIconView.setIconAlpha(1.0f);

		mContactsIconView = (GradientIconView) findViewById(R.id.id_iconfont_friend);
		mContactsIconView.setOnClickListener(this);
		mTabIconIndicator.add(mContactsIconView);

		mDiscoverIconView = (GradientIconView) findViewById(R.id.id_iconfont_faxian);
		mDiscoverIconView.setOnClickListener(this);
		mTabIconIndicator.add(mDiscoverIconView);

		mAboutMeIconView = (GradientIconView) findViewById(R.id.id_iconfont_me);
		mAboutMeIconView.setOnClickListener(this);
		mTabIconIndicator.add(mAboutMeIconView);

		mTvChats = (GradientTextView) findViewById(R.id.id_chats_tv);
		mTvChats.setOnClickListener(this);
		mTabTextIndicator.add(mTvChats);
		mTvChats.setTextViewAlpha(1.0f);

		mTvContacts = (GradientTextView) findViewById(R.id.id_contacts_tv);
		mTvContacts.setOnClickListener(this);
		mTabTextIndicator.add(mTvContacts);

		mTvDiscover = (GradientTextView) findViewById(R.id.id_discover_tv);
		mTvDiscover.setOnClickListener(this);
		mTabTextIndicator.add(mTvDiscover);

		mTvAboutMe = (GradientTextView) findViewById(R.id.id_about_me_tv);
		mTvAboutMe.setOnClickListener(this);
		mTabTextIndicator.add(mTvAboutMe);

		initFragments();
	}

	private void initFragments() {
		mTabs.add(ChatsFragment.newInstance("", ""));
		mTabs.add(ContactsFragment.newInstance("", ""));
		mTabs.add(DiscoverFragment.newInstance("", ""));
		mTabs.add(AboutMeFragment.newInstance("", ""));

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs() {
		resetOtherTabIcons();
		resetOtherTabText();
	}

	/**
	 * 重置其他的Tab icon
	 */
	private void resetOtherTabIcons() {
		for (int i = 0; i < mTabIconIndicator.size(); i++) {
			mTabIconIndicator.get(i).setIconAlpha(0);
		}
	}

	/**
	 * 重置其他的Tab text
	 */
	private void resetOtherTabText() {
		for (int i = 0; i < mTabTextIndicator.size(); i++) {
			mTabTextIndicator.get(i).setTextViewAlpha(0);
		}
	}

	private void setOverflowShowingAlways()
	{
		try
		{
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		/*MenuItem searchItem =menu.findItem(R.id.action_search);
		searchItem.setOnActionExpandListener(new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				// TODO Auto-generated method stub
				Log.d("TAG", "on expand");  
				return false;
			}
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				// TODO Auto-generated method stub
				Log.d("TAG", "on expand");  
				return false;
			}
		});
		SearchView searchView=(SearchView)searchItem.getActionView();
		
		return  super.onCreateOptionsMenu(menu);*/
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {  
	    case R.id.action_add:  
	        Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();  
	        return true;  
	    /*case R.id.action_search:  
	        Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();  
	        return true;  */
	   /* case R.id.action_settings:  
	        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();  
	        return true;  */
	    default:  
	        return super.onOptionsItemSelected(item);  
	    }  
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		if (positionOffset > 0) {
			GradientIconView iconLeft = mTabIconIndicator.get(position);
			GradientIconView iconRight = mTabIconIndicator.get(position + 1);

			GradientTextView textLeft = mTabTextIndicator.get(position);
			GradientTextView textRight = mTabTextIndicator.get(position + 1);

			iconLeft.setIconAlpha(1 - positionOffset);
			textLeft.setTextViewAlpha(1 - positionOffset);
			iconRight.setIconAlpha(positionOffset);
			textRight.setTextViewAlpha(positionOffset);
		}
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetOtherTabs();
		switch (v.getId()) {
		case R.id.id_iconfont_chat:
		case R.id.id_chats_tv:
			mTabIconIndicator.get(0).setIconAlpha(1.0f);
			mTabTextIndicator.get(0).setTextViewAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_iconfont_friend:
		case R.id.id_contacts_tv:
			mTabIconIndicator.get(1).setIconAlpha(1.0f);
			mTabTextIndicator.get(1).setTextViewAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_iconfont_faxian:
		case R.id.id_discover_tv:
			mTabIconIndicator.get(2).setIconAlpha(1.0f);
			mTabTextIndicator.get(2).setTextViewAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_iconfont_me:
		case R.id.id_about_me_tv:
			mTabIconIndicator.get(3).setIconAlpha(1.0f);
			mTabTextIndicator.get(3).setTextViewAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		}
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}
}
