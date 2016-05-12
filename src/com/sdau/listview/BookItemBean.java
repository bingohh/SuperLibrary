package com.sdau.listview;


public class BookItemBean {

    //public int itemImageResid;
    public String bookname;
    public String author;
    public String chuban;
    public String snum;
    public String booknum;

    public BookItemBean(String bookname,String author, String chuban,String snum,String booknum) {
        this.bookname = bookname;
        this.author = author;
        this.chuban = chuban;
        this.snum=snum;
        this.booknum=booknum;
    }
    
    public BookItemBean(){}
    
}
