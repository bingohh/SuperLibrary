package com.sdau.html;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sdau.listview.NewsItemBean;



public class HtmlUtill {
	
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
