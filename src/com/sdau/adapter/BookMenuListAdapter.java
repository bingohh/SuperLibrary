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

public class BookMenuListAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<BookItemBean> mDataList;
    //private long mSumTime;

    public BookMenuListAdapter(Context context, List<BookItemBean> list) {
        mLayoutInflater = LayoutInflater.from(context);
        mDataList = list;
    }

    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.book_menu_item, null);
            holder.bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
            holder.author = (TextView) convertView.findViewById(R.id.tv_author);
            holder.booknum = (TextView) convertView.findViewById(R.id.tv_booknum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BookItemBean bean = mDataList.get(position);
        holder.bookname.setText(bean.bookname);
        holder.author.setText(bean.author);
        holder.booknum.setText(bean.booknum);
        return convertView;
    }

    class ViewHolder{
        public TextView bookname;
        public TextView author;
        public TextView booknum;
    }
}

