package com.sdau.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

import com.sdau.activity.MainActivity;
import com.sdau.activity.SearchBookActivity;
import com.sdau.html.HtmlUtill;
import com.sdau.listview.BookItemBean;
import com.sdau.listview.BookListViewAdapter;
import com.sdau.listview.NewsItemBean;
import com.sdau.listview.NewsListViewAdapter;
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
import android.widget.ListView;
import android.widget.TextView;


public class SearchSimpleFragment extends Fragment {  
    
	private Button btnSearch;
	private EditText strText;
	private TextView tvMsg;
	private String bookname;
	
	private SearchBookActivity sBookActivity;
	private ListView listView;
	List<BookItemBean> dataList = null;
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
    	View searchLayout = inflater.inflate(R.layout.fragment_search_simple, container, false);
    	sBookActivity = (SearchBookActivity)getActivity();
    	listView = (ListView) searchLayout.findViewById(R.id.lv_booklist);
    	
    	btnSearch=(Button)searchLayout.findViewById(R.id.btn_search_simple);
    	strText=(EditText)searchLayout.findViewById(R.id.et_search_simple);
    	tvMsg=(TextView)searchLayout.findViewById(R.id.tv_simple);	
    	btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bookname=strText.getText().toString();
				if(!StringUtil.isBlank(bookname)){
					BooksLoadAsyncTask asyncTask=new BooksLoadAsyncTask();
					asyncTask.execute(bookname);
				}
			}
		});
        return searchLayout;  
    }  
    
    class BooksLoadAsyncTask extends AsyncTask<String, Integer, String> {

		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected String doInBackground(String... params) {
			dataList = new ArrayList<BookItemBean>();
			//
			dataList = HtmlUtill.getBooksList(params[0]);
			return dataList.size() + "";
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(String result) {
			//listView.setAdapter(new NewsListViewAdapter(mMainActivity, dataList));
			//textView.setText("异步操作执行结束" + result);
			tvMsg.setText(result);
			listView.setAdapter(new BookListViewAdapter(sBookActivity, dataList));
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









