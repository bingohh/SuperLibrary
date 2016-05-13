package com.sdau.bean;


public class NewsItemBean {

    //public int itemImageResid;
    public String itemNian;
    public String itemYue;
    public String itemTitle;
    public String itemUrl;

    public NewsItemBean(String itemNian,String itemYue, String itemTitle,String itemUrl) {
        this.itemNian = itemNian;
        this.itemTitle = itemTitle;
        this.itemYue = itemYue;
        this.itemUrl=itemUrl;
    }
}
