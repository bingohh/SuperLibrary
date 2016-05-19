package com.sdau.html;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sdau.bean.BookInfoBean;
import com.sdau.bean.BookItemBean;
import com.sdau.bean.BookListData;
import com.sdau.bean.NewsItemBean;

import android.graphics.BitmapFactory;

public class HtmlUtill {

	public static String bookNum = "";
	
	public static List<String> getHotSearch(){
		List<String> hotSearchs=new ArrayList<String>();
		Document document = null;
		String href="http://202.194.143.19/opac/ajax_topten.php";
		try {
			document = Jsoup.connect(href).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements hotStrEles=document.getElementsByTag("a");
		if(hotStrEles.size()>0){
			for (Element e : hotStrEles) { 
				hotSearchs.add(e.text());
			}
			return hotSearchs;
		}else{
			return null;
		}
	}

	public static BookInfoBean getBookInfo(String href) {
		// TODO Auto-generated method stub
		BookInfoBean book = new BookInfoBean();
		Document document = null;
		Document bookinfoDoc = null;
		try {
			document = Jsoup.connect(href).get();
			href=HtmlUrl.WebRoot+document.select("#tabs1 ul li a").get(1).attr("href");
			bookinfoDoc = Jsoup.connect(href).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] tempStrs;
		Elements bookinfoEs = bookinfoDoc.select("ul");
		String bno = "";
		for (Element e : bookinfoEs) { 
			tempStrs = null;
			bno = e.getElementsByTag("b").text();// 机读格式前面的标号
			tempStrs = e.text().split("\\|"); 
			if ("010".equals(bno)) {// ISBN | CNY
				for (String str : tempStrs) {
					if ("a".equals(str.split(" ")[0])) {// ISBN
						book.ISBN = str.split(" ")[1];
					}
					if ("d".equals(str.split(" ")[0])) {// 价格
						book.CNY = str.split(" ")[1];
					}
				}
			}
			if ("200".equals(bno)) {// 书名 | 作者
				for (String str : tempStrs) {
					if ("a".equals(str.split(" ")[0])) {// 书名
						book.name = str.split(" ")[1];
					}
					if ("f".equals(str.split(" ")[0])) {// 作者
						book.author = str.substring(2, str.length());
					}
				}
			}
			if ("210".equals(bno)) {// 出版信息
				book.chuban = "";
				for(int i=1;i<tempStrs.length;i++){
					if(i != 1) book.chuban += ",";
					book.chuban += tempStrs[i].split(" ")[1];
				}
			}
			if("215".equals(bno)){//页数
				for (String str : tempStrs) {
					if ("a".equals(str.split(" ")[0])) {
						book.pageCount = str.substring(2, str.length());
					}
				}
			}
			if("330".equals(bno)){//简介
				for (String str : tempStrs) {
					if ("a".equals(str.split(" ")[0])) {
						book.intro = str.substring(2, str.length());
					}
				}
			}
		}
		
		//书架信息
		Element table = document.select("#tabs2 table").get(0);
		Elements trs=table.getElementsByClass("whitetext");
		if(trs!=null&&trs.size()>0){
			Elements tds=null;
			List<Map<String,String>> locationList=new ArrayList<Map<String,String>>();
			Map<String,String> locationMap=null;
			for(int i=0;i<trs.size();i++){
				tds=trs.get(i).getElementsByTag("td");
				locationMap=new HashMap<String,String>();
				locationMap.put("booksnum", tds.get(0).text());
				locationMap.put("bookcode", tds.get(1).text());
				tds.get(3).getElementsByTag("a").remove();//去掉架位信息
				locationMap.put("booksite", tds.get(3).text());
				locationMap.put("bookstate", tds.get(4).text());
				locationList.add(locationMap);
			}
			book.location = locationList;
		}
		
		//从京东搜索图片
		if(book.ISBN!=null){
			href="http://search.jd.com/Search?enc=utf-8&keyword="+book.ISBN.replace("-", "");
			try {
				bookinfoDoc = Jsoup.connect(href).get();
				Elements bookEles = bookinfoDoc.select("#J_goodsList ul li");
				if(bookEles!=null&&bookEles.size()>0){
					String url = "http:"+bookEles.get(1).select(".p-img img").attr("src");
					book.pic=BitmapFactory.decodeStream(new URL(url).openStream());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		

		return book;
	}

	// 查询书目列表
	public static BookListData getBooksList(String html) {
		List<BookItemBean> datalist = new ArrayList<BookItemBean>();
		// strText="莫言";
		Document document = null;
		try {
			/*String html = "http://202.194.143.19/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText="
					+ URLEncoder.encode(strText, "utf-8")
					+ "&doctype=ALL&with_ebook=on&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&location=ALL";*/
			document = Jsoup.connect(html).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements es = document.getElementsByClass("book_list_info");
		if(es.size()>0){
			bookNum = document.select(".search_form strong").get(0).text();
			BookItemBean item = null;
			String[] tempStr1, tempStr2;
			// String date_nian="",date_yr="",title="",href="";//href="";
			for (Element e : es) {
				item = new BookItemBean();
				item.bookname = e.select("h3 a").text();
				item.href = e.select("h3 a").attr("href");
				tempStr1 = e.select("h3").text().split(" ");
				item.snum = tempStr1[tempStr1.length - 1];
				item.booknum = e.select("p span").text();
				e.select("p span").remove();
				tempStr2 = e.getElementsByTag("p").text().split(" ");
				item.author = "";
				for (int i = 0; i < tempStr2.length - 3; i++) {
					item.author += tempStr2[i];
				}
				item.chuban = tempStr2[tempStr2.length - 3];
				datalist.add(item);
			}
			return new BookListData(bookNum, datalist);
		}else{
			return null;
		}
		
	}

	// 通知公告列表
	public static List<NewsItemBean> getNewsList(String html) {
		List<NewsItemBean> datalist = new ArrayList<NewsItemBean>();
		html = "http://202.194.143.55:81/sms/opac/news/showNewsList.action?type=1&xc=4&pageSize=20";
		Document document = null;
		try {
			document = Jsoup.connect(html).get();
			Elements es = document.getElementsByTag("li");
			NewsItemBean item = null;
			String date = "", date_nian = "", date_yr = "";// href="";
			for (Element e : es) {
				date = e.getElementsByTag("em").text();
				date_nian = date.substring(1, 5);
				date_yr = date.substring(6, date.length() - 2).replace("月", "-");
				item = new NewsItemBean(date_nian, date_yr, e.getElementsByClass("title").text(),
						e.getElementsByTag("a").attr("href"));
				// Map<String, String> map = new HashMap<String, String>();
				// map.put("title", e.getElementsByClass("title").text());
				/*
				 * map.put("href", "http://www.baidu.com" +
				 * e.getElementsByTag("a").attr("href"));
				 */
				datalist.add(item);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
