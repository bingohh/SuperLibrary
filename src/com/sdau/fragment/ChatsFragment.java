package com.sdau.fragment;

import java.util.ArrayList;
import java.util.List;

import com.sdau.superlibrary.R;
import com.sdau.activity.BorrowBookActivity;
import com.sdau.activity.MainActivity;
import com.sdau.activity.NewsInfoActivity;
import com.sdau.activity.SearchBookActivity;
import com.sdau.adapter.NewsListViewAdapter;
import com.sdau.bean.NewsItemBean;
import com.sdau.html.HtmlUtill;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link ChatsFragment#newInstance} factory method
 * to create an instance of this fragment.
 */
public class ChatsFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private MainActivity mMainActivity;
	//private TextView textView;
	private ListView listView;
	List<NewsItemBean> dataList = null;
	private ImageView searchBookImg;
	private ImageView BorrowBookImg;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ChatsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ChatsFragment newInstance(String param1, String param2) {
		ChatsFragment fragment = new ChatsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment; 
	}

	public ChatsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.fragment_chats, container, false);
		// ProgressBarAsyncTask asyncTask = new ProgressBarAsyncTask(textView,
		// progressBar);
		// asyncTask.execute(1000);
		listView = (ListView) messageLayout.findViewById(R.id.lv_main);
		// progressBar =
		// (ProgressBar)messageLayout.findViewById(R.id.progressBar1);
		//textView = (TextView) messageLayout.findViewById(R.id.textView1);
		mMainActivity = (MainActivity) getActivity();
		NewsLoadAsyncTask asyncTask = new NewsLoadAsyncTask();
		asyncTask.execute();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getContext(), NewsInfoActivity.class);
				Log.d("TAG", dataList.get(position).itemUrl);
				// Toast.makeText(mMainActivity,
				// dataList.get(position).itemUrl,Toast.LENGTH_SHORT).show();
				// Toast.makeText(mMainActivity,
				// dataList.get(position).itemTitle, Toast.LENGTH_LONG);
				intent.putExtra("url", dataList.get(position).itemUrl);
				startActivity(intent);
			}

		});
		//�ݲز�ѯ
		searchBookImg=(ImageView)messageLayout.findViewById(R.id.img_search);
		searchBookImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent();
				intent.setClass(getContext(), SearchBookActivity.class);
				startActivity(intent);
			}
		});
		
		//��������
		BorrowBookImg=(ImageView)messageLayout.findViewById(R.id.img_lend);
		BorrowBookImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent();
				intent.setClass(getContext(), BorrowBookActivity.class);
				startActivity(intent);
			}
		});

		return messageLayout;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	class NewsLoadAsyncTask extends AsyncTask<String, Integer, List<NewsItemBean>> {

		/*
		 * public NewsLoadAsyncTask(MainActivity mMainActivity,ListView
		 * listView,TextView textView) { super(); this.textView = textView; //
		 * this.progressBar = progressBar; this.listView=listView;
		 * this.mMainActivity=mMainActivity; }
		 */

		/**
		 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
		 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
		 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
		 */
		@Override
		protected List<NewsItemBean> doInBackground(String... params) {
			//dataList = new ArrayList<NewsItemBean>();
			//
			dataList = HtmlUtill.getNewsList("");
			return dataList;
		}

		/**
		 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
		 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
		 */
		@Override
		protected void onPostExecute(List<NewsItemBean> result) {
			if(result.size()>0)
				listView.setAdapter(new NewsListViewAdapter(mMainActivity, result));
			//textView.setText("�첽����ִ�н���" + result);
		}

		// �÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ��������
		@Override
		protected void onPreExecute() {
			//textView.setText("��ʼִ���첽�߳�");
		}

		/**
		 * �����Intege������ӦAsyncTask�еĵڶ�������
		 * ��doInBackground�������У���ÿ�ε���publishProgress�������ᴥ��onProgressUpdateִ��
		 * onProgressUpdate����UI�߳���ִ�У����п��Զ�UI�ռ���в���
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			//int vlaue = values[0];
			// progressBar.setProgress(vlaue);
		}

	}

}
