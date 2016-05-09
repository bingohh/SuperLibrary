package com.sdau.fragment;

import java.util.ArrayList;
import java.util.List;

import com.sdau.html.HtmlUtill;
import com.sdau.listview.NewsItemBean;
import com.sdau.superlibrary.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SearchSimpleFragment extends Fragment {  
    
	private Button btnSearch;
	private EditText strText;
	private TextView tvMsg;
	
	List<NewsItemBean> dataList = null;
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
    	View searchLayout = inflater.inflate(R.layout.fragment_search_simple, container, false);
    	btnSearch=(Button)searchLayout.findViewById(R.id.btn_search_simple);
    	strText=(EditText)searchLayout.findViewById(R.id.et_search_simple);
    	tvMsg=(TextView)searchLayout.findViewById(R.id.tv_simple);	
    	btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BooksLoadAsyncTask asyncTask=new BooksLoadAsyncTask();
				asyncTask.execute();
			}
		});
        return searchLayout;  
    }  
    
    class BooksLoadAsyncTask extends AsyncTask<String, Integer, String> {

		/**
		 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
		 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
		 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
		 */
		@Override
		protected String doInBackground(String... params) {
			dataList = new ArrayList<NewsItemBean>();
			//
			dataList = HtmlUtill.getNewsList("");
			return dataList.size() + "";
		}

		/**
		 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
		 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
		 */
		@Override
		protected void onPostExecute(String result) {
			//listView.setAdapter(new NewsListViewAdapter(mMainActivity, dataList));
			//textView.setText("�첽����ִ�н���" + result);
			tvMsg.setText(result);
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









