package com.sdau.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.helper.StringUtil;

import com.sdau.activity.BookListActivity;
import com.sdau.superlibrary.R;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchAdvancedFragment extends Fragment {

	private EditText cet_bookname;
	private EditText cet_bookauthor;
	private EditText cet_bookchubanshe;
	private EditText cet_bookisbn;
	private EditText cet_bookcallno;
	
	private Button btnSearch;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//http://202.194.143.19/opac/openlink.php?title=timing&publisher=chubanshe&author=zuozhe&isbn=ISBN&series=&callno=suoshuhao&keyword=&year=&doctype=ALL&lang_code=ALL&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL&with_ebook=on
		View searchLayout = inflater.inflate(R.layout.fragment_search_advanced, container, false);
		btnSearch=(Button)searchLayout.findViewById(R.id.btn_search_advanced);
		//etSearchStr=(EditText)searchLayout.findViewById(R.id.cet_advanced_bookauthor);
		cet_bookname=(EditText)searchLayout.findViewById(R.id.cet_advanced_bookname);
		cet_bookauthor=(EditText)searchLayout.findViewById(R.id.cet_advanced_bookauthor);
		cet_bookchubanshe=(EditText)searchLayout.findViewById(R.id.cet_advanced_bookchubanshe);
		cet_bookisbn=(EditText)searchLayout.findViewById(R.id.cet_advanced_bookisbn);
		cet_bookcallno=(EditText)searchLayout.findViewById(R.id.cet_advanced_callno);
		
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strBookname=cet_bookname.getText().toString();
				String strBookauthor=cet_bookauthor.getText().toString();
				String strBookpublisher=cet_bookchubanshe.getText().toString();
				String strBookisbn=cet_bookisbn.getText().toString();
				String strBookcallno=cet_bookcallno.getText().toString();
				String html=String.format("http://202.194.143.19/opac/openlink.php?title=%s&publisher=%s&author=%s&isbn=%s&series=&callno=%s&keyword=&year=&doctype=ALL&lang_code=ALL&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL&with_ebook=on"
									, strBookname,strBookpublisher,strBookauthor,strBookisbn,strBookcallno);
				if(StringUtil.isBlank(strBookname)||StringUtil.isBlank(strBookauthor)||StringUtil.isBlank(strBookpublisher)||StringUtil.isBlank(strBookisbn)||StringUtil.isBlank(strBookcallno)){
					Intent intent =new Intent();
					intent.setClass(getContext(), BookListActivity.class);
					intent.putExtra("html", html);
					intent.putExtra("strText", "¸ß¼¶¼ìË÷");
					startActivity(intent);
				}else{
					//Toast.makeText(, text, duration)
				}
			}
		});
		return searchLayout;
	}
	
}










