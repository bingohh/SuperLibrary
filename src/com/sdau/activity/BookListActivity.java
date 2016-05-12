package com.sdau.activity;

import java.util.ArrayList;
import java.util.List;

import com.sdau.html.BookListData;
import com.sdau.html.HtmlUtill;
import com.sdau.listview.BookItemBean;
import com.sdau.listview.BookListViewAdapter;
import com.sdau.superlibrary.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

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
		
		String strText=getIntent().getStringExtra("strText");
		tv_searchText.setText("关键词："+strText);
		
		BooksLoadAsyncTask asyncTask=new BooksLoadAsyncTask();
		asyncTask.execute(strText);
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
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected BookListData doInBackground(String... params) {
			BookListData booklistdata=HtmlUtill.getBooksList(params[0]);
			return booklistdata;
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(BookListData booklistdata) {
			//listView.setAdapter(new NewsListViewAdapter(mMainActivity, dataList));
			//textView.setText("异步操作执行结束" + result);
			//tvMsg.setText(result);
			//dataList = new ArrayList<BookItemBean>();
			dataList = booklistdata.getBookList();
			tv_booknum.setText("共查询到"+booklistdata.getBookNum()+"本图书");
			listView.setAdapter(new BookListViewAdapter(mBookListActivity, dataList));
		}

		// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
		@Override
		protected void onPreExecute() {
			//textView.setText("开始执行异步线程");
			
		}

		/**
		 * 这里的Intege参数对应AsyncTask中的第二个参数
		 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
		 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			//int vlaue = values[0];
			// progressBar.setProgress(vlaue);
		}

	}
}
