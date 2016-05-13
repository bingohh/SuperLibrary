package com.sdau.bean;


public class BookItemBean {

    //public int itemImageResid;
    public String bookname;
    public String author;
    public String chuban;
    public String snum;
    public String booknum;
    public String href;

    public BookItemBean(String bookname,String author, String chuban,String snum,String booknum,String href) {
        this.bookname = bookname;
        this.author = author;
        this.chuban = chuban;
        this.snum=snum;
        this.booknum=booknum;
        this.href=href;
    }
    
    public BookItemBean(){}
    
}
