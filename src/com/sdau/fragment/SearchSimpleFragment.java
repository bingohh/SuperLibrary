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

	// �������Ƽ����ʵ��
	GestureDetector detector;
	private Activity mActivity;

	/*public static final String[] keywords = { "QQ", "Sodino", "APK", "GFW", "Ǧ��", //
			"����", "���澫��", "MacBook Pro", "ƽ�����", "��ʫ����", //
			"����ŷ TR-100", "�ʼǱ�", "SPY Mouse", "Thinkpad E40", "�������", //
			"�ڴ�����", "��ͼ", "����", "����", "����", //
			"ͨѶ¼", "������", "CSDN leak", "��ȫ", "3D", //
			"��Ů", "����", "4743G", "����", "����", //
			"ŷ��", "�����", "��ŭ��С��", "mmShow", "���׹�����", //
			"iciba", "��ˮ��ϵ", "����App", "������", "365����", //
			"����ʶ��", "Chrome", "Safari", "�й���Siri", "A5������", //
			"iPhone4S", "Ħ�� ME525", "���� M9", "�῵ S2500" };*/
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
					intent.putExtra("strText", "�ؼ��ʣ�" + strText);
					startActivity(intent);
				}
			}
		});
		
		// �������Ƽ����
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
		 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
		 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
		 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
		 */
		@Override
		protected List<String> doInBackground(String... params) {
			List<String> hotKeys=HtmlUtill.getHotSearch();
			return hotKeys;
		}

		/**
		 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
		 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
		 */
		@Override
		protected void onPostExecute(List<String> hotKeys) {
			String[] keywords = new String[hotKeys.size()];
			hotKeys.toArray(keywords);
			// ���
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
			
		}

		// �÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ��������
		@Override
		protected void onPreExecute() {
			
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
		}

	}

}
