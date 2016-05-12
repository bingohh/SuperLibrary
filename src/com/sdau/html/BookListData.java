package com.sdau.html;

import java.util.List;

import com.sdau.listview.BookItemBean;

public class BookListData{
	private String bookNum;
	private List<BookItemBean> bookList;
	
	public BookListData(){}
	
	public String getBookNum() {
		return bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}

	public List<BookItemBean> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookItemBean> bookList) {
		this.bookList = bookList;
	}

	public BookListData(String bookNum,List<BookItemBean> bookList){
		this.bookNum=bookNum;
		this.bookList=bookList;
	}
}
