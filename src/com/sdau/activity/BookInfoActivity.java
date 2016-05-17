package com.sdau.activity;

import java.util.Map;

import com.sdau.adapter.BookListViewAdapter;
import com.sdau.bean.BookInfoBean;
import com.sdau.bean.BookListData;
import com.sdau.html.HtmlUtill;
import com.sdau.superlibrary.R;
import com.sdau.superlibrary.R.id;
import com.sdau.superlibrary.R.layout;
import com.sdau.superlibrary.R.menu;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class BookInfoActivity extends Activity {

	private BookInfoBean book;
	private TextView tv_bookname;
	
	private TextView tv_bookauthor;
	private TextView tv_bookchuban;
	private TextView tv_bookISBN;
	private TextView tv_bookCNY;
	private TextView tv_bookpageCount;
	private TextView tv_bookIntro;

	private LinearLayout ll_bookauthor;
	private LinearLayout ll_bookchuban;
	private LinearLayout ll_bookISBN;
	private LinearLayout ll_bookCNY;
	private LinearLayout ll_bookpageCount;
	private LinearLayout ll_bookIntro;
	
	private ImageView img_bookpic;
	private LinearLayout booklayout;
	private BookInfoActivity mActivity = this;
	
	private TableLayout tl_bookinfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_info);
		booklayout=(LinearLayout)findViewById(R.id.ll_bookinfo);
		tv_bookname=(TextView)findViewById(R.id.tv_bookinfo_name);
		img_bookpic=(ImageView)findViewById(R.id.img_bookinfo_pic);
		
		tv_bookauthor=(TextView)findViewById(R.id.tv_bookinfo_author);
		tv_bookchuban=(TextView)findViewById(R.id.tv_bookinfo_chuban);
		tv_bookISBN=(TextView)findViewById(R.id.tv_bookinfo_ISBN);
		tv_bookCNY=(TextView)findViewById(R.id.tv_bookinfo_CNY);
		tv_bookpageCount=(TextView)findViewById(R.id.tv_bookinfo_pageCount);
		tv_bookIntro=(TextView)findViewById(R.id.tv_bookinfo_intro);
		
		ll_bookauthor=(LinearLayout)findViewById(R.id.ll_bookinfo_author);
		ll_bookchuban=(LinearLayout)findViewById(R.id.ll_bookinfo_chuban);
		ll_bookISBN=(LinearLayout)findViewById(R.id.ll_bookinfo_ISBN);
		ll_bookCNY=(LinearLayout)findViewById(R.id.ll_bookinfo_CNY);
		ll_bookpageCount=(LinearLayout)findViewById(R.id.ll_bookinfo_pageCount);
		ll_bookIntro=(LinearLayout)findViewById(R.id.ll_bookinfo_intro);

		
		tl_bookinfo=(TableLayout)findViewById(R.id.tl_bookinfo);
		String href=getIntent().getStringExtra("href");
		BookInfoAsyncTask asyncTask=new BookInfoAsyncTask();
		asyncTask.execute(href);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_info, menu);
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
	
	class BookInfoAsyncTask extends AsyncTask<String, Integer, BookInfoBean> {

		/**
		 * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 */
		@Override
		protected BookInfoBean doInBackground(String... params) {
			BookInfoBean bookinfo=HtmlUtill.getBookInfo(params[0]);
			return bookinfo;
		}

		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
		 */
		@Override
		protected void onPostExecute(BookInfoBean bookinfo) {
			//http://img11.360buyimg.com/n1/s200x200_jfs/t2101/56/2749269734/29922/cea1372f/57189d83N67aafc9f.jpg
			book = bookinfo;
			tv_bookname.setText(book.name);
			mActivity.setTitle(book.name);
			if(book.pic!=null){
				img_bookpic.setImageBitmap(book.pic);
				img_bookpic.setScaleType(ScaleType.FIT_CENTER);
			}else{
				img_bookpic.setVisibility(8);
			}
			if(book.author!=null){
				tv_bookauthor.setText(book.author);
			}else{
				ll_bookauthor.setVisibility(8);
			}
			
			if(book.chuban!=null){
				tv_bookchuban.setText(book.chuban);
			}else{
				ll_bookchuban.setVisibility(8);
			}
			
			if(book.ISBN!=null){
				tv_bookISBN.setText(book.ISBN);
			}else{
				ll_bookISBN.setVisibility(8);
			}
			
			if(book.CNY!=null){
				tv_bookCNY.setText(book.CNY);
			}else{
				ll_bookCNY.setVisibility(8);
			}
			
			if(book.pageCount!=null){
				tv_bookpageCount.setText(book.pageCount);
			}else{
				ll_bookpageCount.setVisibility(8);
			}
			
			if(book.intro!=null){
				tv_bookIntro.setText(book.intro);
			}else{
				ll_bookIntro.setVisibility(8);
			}
			
			if(book.location!=null&&book.location.size()>0){
				TableRow tableRow = null;
				TextView textView = null;
				tableRow = new TableRow(mActivity); 
				
		        textView = new TextView(mActivity); 
		        textView.setText("馆藏地");
		        textView.setPadding(10, 3, 0, 3);
		        tableRow.addView(textView);
		        
		        textView = new TextView(mActivity); 
		        textView.setText("借阅状态");
		        textView.setPadding(10, 3, 0, 3);
		        tableRow.addView(textView);
		        tl_bookinfo.addView(tableRow);
				for(Map<String,String> bookLoc : book.location){
					tableRow = new TableRow(mActivity); 
					
//					textView = new TextView(mActivity); 
//					textView.setText(bookLoc.get("booksnum"));
//					textView.setTextAppearance(mActivity, R.style.booklocation);
//					textView.setGravity(Gravity.LEFT);
//					textView.setPadding(5, 3, 10, 3);
//			        tableRow.addView(textView);
			        
			        textView = new TextView(mActivity);  
			        textView.setText(bookLoc.get("booksite"));
			        textView.setTextAppearance(mActivity, R.style.booklocation);
			       // textView.setGravity(Gravity.LEFT);
			        textView.setPadding(5, 3, 5, 3);
			        textView.setSingleLine(false);
			        tableRow.addView(textView);
			        
			        textView = new TextView(mActivity);  
			        textView.setText(bookLoc.get("bookstate"));
			        textView.setTextAppearance(mActivity, R.style.booklocation);
			       // textView.setGravity(Gravity.CENTER);
			        textView.setPadding(5, 3, 5, 3);
			        textView.setSingleLine(false);
			        tableRow.addView(textView);
			        
			        
			       // tableRow.setGravity(Gravity.NO_GRAVITY);
					tl_bookinfo.addView(tableRow);
				}
			}else{
				tl_bookinfo.setVisibility(8);
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
		}

	}
}
