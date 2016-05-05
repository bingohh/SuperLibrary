package com.sdau.activity;


import com.sdau.superlibrary.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsInfoActivity extends Activity {

	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_info);
		//String url=getIntent().getExtras("url");
		String url=getIntent().getStringExtra("url");
		webView=(WebView)findViewById(R.id.webView1);
		webView.loadUrl(url);
		
		WebSettings s = webView.getSettings();   
		s.setDisplayZoomControls(false);//设定缩放控件隐藏
		s.setBuiltInZoomControls(true);     
		s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);     
		s.setUseWideViewPort(true);     
		s.setLoadWithOverviewMode(true);     
		s.setSavePassword(true);     
		s.setSaveFormData(true);     
		s.setJavaScriptEnabled(true);     // enable navigator.geolocation     
		s.setGeolocationEnabled(true);     
		s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage     
		s.setDomStorageEnabled(true);  
		webView.requestFocus();  
		webView.setScrollBarStyle(0);
		
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}
		});
		
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		
		webView.setOnKeyListener(new View.OnKeyListener() {    
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {    
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键时的操作  
                    	webView.goBack();   //后退    
                        //webview.goForward();//前进  
                        return true;    //已处理    
                    }    
                }    
                return false;    
			}    
        });  
		
	}

}
