package com.sdau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.sdau.bean.BookItemBean;
import com.sdau.superlibrary.R;

public class BookListViewAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    // 映射数据
    private List<BookItemBean> mDataList;
    //private long mSumTime;

    public BookListViewAdapter(Context context, List<BookItemBean> list) {
        mLayoutInflater = LayoutInflater.from(context);
        mDataList = list;
    }

    //获取数据�?    @Override
    public int getCount() {
        return mDataList.size();
    }

    // 获取对应ID项对应的Item
    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    // 获取对应项ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 文艺�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布�?���?��第二个参数�?常设置为null
            convertView = mLayoutInflater.inflate(R.layout.book_list_item, null);
            holder.bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
            holder.author = (TextView) convertView.findViewById(R.id.tv_author);
            holder.chuban = (TextView) convertView.findViewById(R.id.tv_chuban);
            holder.snum = (TextView) convertView.findViewById(R.id.tv_snum);
            holder.booknum = (TextView) convertView.findViewById(R.id.tv_booknum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 取出bean对象
        BookItemBean bean = mDataList.get(position);
        // 设置控件的数�?        
        holder.bookname.setText(bean.bookname);
        holder.author.setText(bean.author);
        holder.chuban.setText(bean.chuban);
        holder.snum.setText(bean.snum);
        holder.booknum.setText(bean.booknum);
        return convertView;
        // 文艺�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>16325820
    }

    // ViewHolder用于缓存控件
    class ViewHolder{
        public TextView bookname;
        public TextView author;
        public TextView chuban;
        public TextView snum;
        public TextView booknum;
    }
}

