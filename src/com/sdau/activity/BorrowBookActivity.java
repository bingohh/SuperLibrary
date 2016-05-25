package com.sdau.activity;

import java.util.List;

import com.sdau.adapter.BorrowBookListAdapter;
import com.sdau.bean.BookItemBean;
import com.sdau.bean.BookListData;
import com.sdau.html.HtmlUtill;
import com.sdau.superlibrary.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class BorrowBookActivity extends Activity {

	private ListView listView;
	private TextView tv_borrownum;
	List<BookItemBean> dataList = null;
	BorrowBookActivity mActivity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_borrow_book);
		listView = (ListView)findViewById(R.id.lv_borrow_book);
		tv_borrownum=(TextView)findViewById(R.id.tv_borrow_num);
		BooksLoadAsyncTask asyncTask=new BooksLoadAsyncTask();
		asyncTask.execute("http://202.194.143.19/reader/book_lst.php");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.borrow_renew, menu);
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
			BookListData booklistdata=HtmlUtill.getBorrowBooks(params[0]);
			return booklistdata;
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(BookListData booklistdata) {
			if(booklistdata!=null){
				dataList = booklistdata.getBookList();
				tv_borrownum.setText("当前借阅  "+booklistdata.getBookNum());
				listView.setAdapter(new BorrowBookListAdapter(mActivity, dataList));
			}
			
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
