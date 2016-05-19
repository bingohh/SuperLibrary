package com.sdau.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.helper.StringUtil;

import com.kris.search.KeywordsFlow;
import com.sdau.activity.BookListActivity;
import com.sdau.activity.MainActivity;
import com.sdau.activity.SearchBookActivity;
import com.sdau.adapter.BookListViewAdapter;
import com.sdau.adapter.NewsListViewAdapter;
import com.sdau.bean.BookItemBean;
import com.sdau.bean.BookListData;
import com.sdau.bean.NewsItemBean;
import com.sdau.html.HtmlUtill;
import com.sdau.superlibrary.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchSimpleFragment extends Fragment implements OnClickListener/*, OnGestureListener*/ {

	// 定义手势检测器实例
	GestureDetector detector;
	private Activity mActivity;

	/*public static final String[] keywords = { "QQ", "Sodino", "APK", "GFW", "铅笔", //
			"短信", "桌面精灵", "MacBook Pro", "平板电脑", "雅诗兰黛", //
			"卡西欧 TR-100", "笔记本", "SPY Mouse", "Thinkpad E40", "捕鱼达人", //
			"内存清理", "地图", "导航", "闹钟", "主题", //
			"通讯录", "播放器", "CSDN leak", "安全", "3D", //
			"美女", "天气", "4743G", "戴尔", "联想", //
			"欧朋", "浏览器", "愤怒的小鸟", "mmShow", "网易公开课", //
			"iciba", "油水关系", "网游App", "互联网", "365日历", //
			"脸部识别", "Chrome", "Safari", "中国版Siri", "A5处理器", //
			"iPhone4S", "摩托 ME525", "魅族 M9", "尼康 S2500" };*/
	private KeywordsFlow keywordsFlow;
	private Button btnSearch;
	private EditText etSearchStr;

	List<BookItemBean> dataList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View searchLayout = inflater.inflate(R.layout.fragment_search_simple, container, false);
		btnSearch = (Button) searchLayout.findViewById(R.id.btn_search_simple);
		etSearchStr = (EditText) searchLayout.findViewById(R.id.et_search_simple);
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strText = etSearchStr.getText().toString();
				if (!StringUtil.isBlank(strText)) {
					Intent intent = new Intent();
					intent.setClass(getContext(), BookListActivity.class);
					String html;
					try {
						html = "http://202.194.143.19/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText="
								+ URLEncoder.encode(strText, "utf-8")
								+ "&doctype=ALL&with_ebook=on&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL";
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						html = null;
					}
					intent.putExtra("html", html);
					intent.putExtra("strText", "关键词：" + strText);
					startActivity(intent);
				}
			}
		});
		
		// 创建手势检测器
		// detector = new GestureDetectorCompat (mActivity, new
		// MyGestureListener());
		keywordsFlow = (KeywordsFlow) searchLayout.findViewById(R.id.kwflow_simple);
		keywordsFlow.setDuration(800l);
		keywordsFlow.setOnItemClickListener(this);
		KeyWordsAsyncTask asyncTask=new KeyWordsAsyncTask();
		asyncTask.execute();
		
		return searchLayout;
	}


	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	@Override
	public void onClick(View v) {
		/*
		 * if (v == btnIn) { keywordsFlow.rubKeywords(); //
		 * keywordsFlow.rubAllViews(); feedKeywordsFlow(keywordsFlow, keywords);
		 * keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN); } else if (v ==
		 * btnOut) { keywordsFlow.rubKeywords(); // keywordsFlow.rubAllViews();
		 * feedKeywordsFlow(keywordsFlow, keywords);
		 * keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT); } else
		 */
		if (v instanceof TextView) {
			String keyword = ((TextView) v).getText().toString();
			// Intent intent = new Intent();
			// intent.setAction(Intent.ACTION_VIEW);
			// intent.addCategory(Intent.CATEGORY_DEFAULT);
			// intent.setData(Uri.parse("http://www.google.com.hk/#q=" +
			// keyword));
			// startActivity(intent);
			etSearchStr.setText(keyword);
			btnSearch.performClick();
			Log.e("Search", keyword);
		}
	}
	
	class KeyWordsAsyncTask extends AsyncTask<String, Integer, List<String>> {

		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected List<String> doInBackground(String... params) {
			List<String> hotKeys=HtmlUtill.getHotSearch();
			return hotKeys;
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(List<String> hotKeys) {
			String[] keywords = new String[hotKeys.size()];
			hotKeys.toArray(keywords);
			// 添加
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
			
		}

		// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
		@Override
		protected void onPreExecute() {
			
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
		}

	}

}
