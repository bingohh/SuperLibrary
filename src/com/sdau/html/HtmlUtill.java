package com.sdau.html;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sdau.listview.BookItemBean;
import com.sdau.listview.NewsItemBean;



public class HtmlUtill {
	
	public static List<BookItemBean> getBooksList(String bookname){
		List<BookItemBean> datalist = new ArrayList<BookItemBean>();
		//bookname="ÄªÑÔ";
		Document document = null;
		try {
			String html="http://202.194.143.19/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText="+URLEncoder.encode(bookname,"utf-8")+"&doctype=ALL&with_ebook=on&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL";
			document = Jsoup.connect(html).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements es = document.getElementsByClass("book_list_info");
		BookItemBean item=null;
		String date="",date_nian="",date_yr="",title="",href="";//href="";
		for (Element e : es) {
			//date=e.getElementsByTag("em").text();
			//date_nian=date.substring(1, 5);
			//date_yr=date.substring(6, date.length()-2).replace("ÔÂ", "-");
			title=e.getElementsByTag("a").text();
			href=e.getElementsByTag("a").attr("href"); 
			item=new BookItemBean("aaaaa", "bbb","ccc","","ddd");
			//Map<String, String> map = new HashMap<String, String>();
			//map.put("title", e.getElementsByClass("title").text());
			/*map.put("href", "http://www.baidu.com"
					+ e.getElementsByTag("a").attr("href"));*/
			datalist.add(item);
		} 
		return datalist;
	}
	
	public static List<NewsItemBean> getNewsList(String html){
		List<NewsItemBean> datalist = new ArrayList<NewsItemBean>();
		html="http://202.194.143.55:81/sms/opac/news/showNewsList.action?type=1&xc=4&pageSize=20";
		Document document = null;
		try {
			document = Jsoup.connect(html).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		Elements es = document.getElementsByTag("li");
		NewsItemBean item=null;
		String date="",date_nian="",date_yr="";//href="";
		for (Element e : es) {
			date=e.getElementsByTag("em").text();
			date_nian=date.substring(1, 5);
			date_yr=date.substring(6, date.length()-2).replace("ÔÂ", "-");
			item=new NewsItemBean(date_nian, date_yr,e.getElementsByClass("title").text(),e.getElementsByTag("a").attr("href"));
			//Map<String, String> map = new HashMap<String, String>();
			//map.put("title", e.getElementsByClass("title").text());
			/*map.put("href", "http://www.baidu.com"
					+ e.getElementsByTag("a").attr("href"));*/
			datalist.add(item);
		} 
		return datalist;
	}
	
	public String getHtmlString(String urlString) {  
	    try {  
	        URL url = new URL(urlString);  
	        URLConnection ucon = url.openConnection();  
	        InputStream instr = ucon.getInputStream();  
	        BufferedInputStream bis = new BufferedInputStream(instr);  
	        ByteArrayBuffer baf = new ByteArrayBuffer(500);  
	        int current = 0;  
	        while ((current = bis.read()) != -1) {  
	            baf.append((byte) current);  
	        }  
	        return EncodingUtils.getString(baf.toByteArray(), "gbk");  
	    } catch (Exception e) {  
	        return "";  
	    }  
	}  
}
