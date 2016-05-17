package com.sdau.bean;

import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BookInfoBean{
	
	public String name;
    public String author;
    public String chuban;
    public String snum;
    public String ISBN;
    public String CNY;
    public String pageCount;
    public String intro;
    public String booknum;
    public String href;
    public Bitmap pic;
    public List<Map<String,String>> location;
	//private List<BookItemBean> bookList;
	
	public BookInfoBean(){}

	
}
