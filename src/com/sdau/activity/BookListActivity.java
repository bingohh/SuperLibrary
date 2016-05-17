package com.sdau.activity;

import java.util.ArrayList;
import java.util.List;

import com.sdau.adapter.BookListViewAdapter;
import com.sdau.bean.BookItemBean;
import com.sdau.bean.BookListData;
import com.sdau.html.HtmlUrl;
import com.sdau.html.HtmlUtill;
import com.sdau.superlibrary.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BookListActivity extends Activity {

	private ListView listView;
	List<BookItemBean> dataList = null;
	private TextView tv_searchText;
	private TextView tv_booknum;
	private BookListActivity mBookListActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_list);
		mBookListActivity=this;
		
		ActionBar actionBar = getActionBar();  
		actionBar.setCustomView(R.layout.booklist_titlebar);  
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
		actionBar.setDisplayShowCustomEnabled(true); 
		
		tv_booknum=(TextView)findViewById(R.id.tv_booknum);
		tv_searchText=(TextView)findViewById(R.id.tv_searchStr);
		listView = (ListView)findViewById(R.id.lv_booklist);
		
		String html=getIntent().getStringExtra("html");
		String strText=getIntent().getStringExtra("strText");
		tv_searchText.setText(strText);
		
		BooksLoadAsyncTask asyncTask=new BooksLoadAsyncTask();
		asyncTask.execute(html);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mBookListActivity, BookInfoActivity.class);
				Log.d("TAG", dataList.get(position).href);
				intent.putExtra("href", HtmlUrl.WebRoot+dataList.get(position).href);
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_list, menu);
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
	
	 
    class BooksLoadAsyncTask extends AsyncTask<String, Integer, BookListData> {

		/**
		 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
		 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
		 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
		 */
		@Override
		protected BookListData doInBackground(String... params) {
			BookListData booklistdata=HtmlUtill.getBooksList(params[0]);
			return booklistdata;
		}

		/**
		 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
		 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
		 */
		@Override
		protected void onPostExecute(BookListData booklistdata) {
			//listView.setAdapter(new NewsListViewAdapter(mMainActivity, dataList));
			//textView.setText("�첽����ִ�н���" + result);
			//tvMsg.setText(result);
			//dataList = new ArrayList<BookItemBean>();
			dataList = booklistdata.getBookList();
			tv_booknum.setText("����ѯ��"+booklistdata.getBookNum()+"��ͼ��");
			listView.setAdapter(new BookListViewAdapter(mBookListActivity, dataList));
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
