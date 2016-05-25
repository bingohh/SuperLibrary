package com.sdau.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

import com.sdau.activity.BookListActivity;
import com.sdau.activity.MainActivity;
import com.sdau.activity.SearchBookActivity;
import com.sdau.adapter.BookListViewAdapter;
import com.sdau.adapter.NewsListViewAdapter;
import com.sdau.bean.BookItemBean;
import com.sdau.bean.NewsItemBean;
import com.sdau.html.HtmlUtill;
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
	//private TextView tvMsg;
	//private String bookname;
	
	//private SearchBookActivity sBookActivity;
	//private ListView listView;
	List<BookItemBean> dataList = null;
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
    	View searchLayout = inflater.inflate(R.layout.fragment_search_simple, container, false);
    	//sBookActivity = (SearchBookActivity)getActivity();
    	btnSearch=(Button)searchLayout.findViewById(R.id.btn_search_simple);
    	etSearchStr=(EditText)searchLayout.findViewById(R.id.et_search_simple);
    	//tvMsg=(TextView)searchLayout.findViewById(R.id.tv_simple);	
    	btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strText=etSearchStr.getText().toString();
				if(!StringUtil.isBlank(strText)){
					Intent intent =new Intent();
					intent.setClass(getContext(), BookListActivity.class);
					String html;
					try {
						html = "http://202.194.143.19/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText="
								+ URLEncoder.encode(strText, "utf-8")
								+ "&doctype=ALL&with_ebook=on&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL";
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						html=null;
					}
					intent.putExtra("html", html);
					intent.putExtra("strText", "¹Ø¼ü´Ê£º"+strText);
					startActivity(intent);
				}
			}
		});
        return searchLayout;  
    }  
   
  
}  









