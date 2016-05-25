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
		 * �����Integer������ӦAsyncTask�еĵ�һ������ �����String����ֵ��ӦAsyncTask�ĵ���������
		 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
		 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
		 */
		@Override
		protected BookListData doInBackground(String... params) {
			BookListData booklistdata=HtmlUtill.getBorrowBooks(params[0]);
			return booklistdata;
		}

		/**
		 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
		 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
		 */
		@Override
		protected void onPostExecute(BookListData booklistdata) {
			if(booklistdata!=null){
				dataList = booklistdata.getBookList();
				tv_borrownum.setText("��ǰ����  "+booklistdata.getBookNum());
				listView.setAdapter(new BorrowBookListAdapter(mActivity, dataList));
			}
			
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
