package com.sdau.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

import com.sdau.activity.BookListActivity;
import com.sdau.activity.MainActivity;
import com.sdau.activity.SearchBookActivity;
import com.sdau.html.HtmlUtill;
import com.sdau.listview.BookItemBean;
import com.sdau.listview.BookListViewAdapter;
import com.sdau.listview.NewsItemBean;
import com.sdau.listview.NewsListViewAdapter;
import com.sdau.superlibrary.R;

import android.content.Intent;
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
	private EditText etSearchStr;
	private TextView tvMsg;
	//private String bookname;
	
	private SearchBookActivity sBookActivity;
	//private ListView listView;
	List<BookItemBean> dataList = null;
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
    	View searchLayout = inflater.inflate(R.layout.fragment_search_simple, container, false);
    	sBookActivity = (SearchBookActivity)getActivity();
    	btnSearch=(Button)searchLayout.findViewById(R.id.btn_search_simple);
    	etSearchStr=(EditText)searchLayout.findViewById(R.id.et_search_simple);
    	tvMsg=(TextView)searchLayout.findViewById(R.id.tv_simple);	
    	btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strText=etSearchStr.getText().toString();
				//if(!StringUtil.isBlank(strText)){
					Intent intent =new Intent();
					intent.setClass(getContext(), BookListActivity.class);
					intent.putExtra("strText", "д╙ят");
					startActivity(intent);
				//}
			}
		});
        return searchLayout;  
    }  
   
  
}  









