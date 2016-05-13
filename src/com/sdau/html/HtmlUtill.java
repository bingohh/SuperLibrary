package com.sdau.html;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.sdau.bean.BookItemBean;
import com.sdau.bean.BookListData;
import com.sdau.bean.NewsItemBean;



public class HtmlUtill {
	
	public static String bookNum="";
	
	public static BookListData getBooksList(String strText){
		List<BookItemBean> datalist = new ArrayList<BookItemBean>();
		//strText="ÄªÑÔ";
		Document document = null;
		try {
			String html="http://202.194.143.19/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText="+URLEncoder.encode(strText,"utf-8")+"&doctype=ALL&with_ebook=on&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL";
			document = Jsoup.connect(html).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements es = document.getElementsByClass("book_list_info");
		bookNum=document.select(".search_form strong").get(0).text();
		BookItemBean item=null;
		String[] tempStr1,tempStr2;
	//	String date_nian="",date_yr="",title="",href="";//href="";
		for (Element e : es) {
			item=new BookItemBean();
			item.bookname=e.select("h3 a").text();
			item.href=e.select("h3 a").attr("href");
			tempStr1=e.select("h3").text().split(" ");
			item.snum=tempStr1[tempStr1.length-1];
			item.booknum=e.select("p span").text();
			e.select("p span").remove();
			tempStr2=e.getElementsByTag("p").text().split(" ");
			item.author="";
			for(int i=0;i<tempStr2.length-3;i++){
				item.author+=tempStr2[i];
			}
			item.chuban=tempStr2[tempStr2.length-3]; 
			datalist.add(item);
		} 
		return new BookListData(bookNum,datalist);
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



