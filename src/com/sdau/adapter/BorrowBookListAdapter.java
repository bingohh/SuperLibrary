package com.sdau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import com.sdau.bean.BookItemBean;
import com.sdau.superlibrary.R;
import com.sdau.superlibrary.R.color;

public class BorrowBookListAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<BookItemBean> mDataList;
    //private long mSumTime;

    public BorrowBookListAdapter(Context context, List<BookItemBean> list) {
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
            convertView = mLayoutInflater.inflate(R.layout.borrow_list_item, null);
            holder.bookname = (TextView) convertView.findViewById(R.id.tv_bookname);
            holder.author = (TextView) convertView.findViewById(R.id.tv_author);
            holder.snum = (TextView) convertView.findViewById(R.id.tv_snum);
            holder.borrowdate = (TextView) convertView.findViewById(R.id.tv_borrowdate);
            holder.returndate = (TextView) convertView.findViewById(R.id.tv_returndate);
            holder.position = (TextView) convertView.findViewById(R.id.tv_bookposition);
            holder.btnrenew=(Button)convertView.findViewById(R.id.btn_renew);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BookItemBean bean = mDataList.get(position);
        holder.bookname.setText(bean.bookname);
        holder.author.setText(bean.author);
        holder.snum.setText(bean.snum);
        holder.borrowdate.setText(bean.borrowdate);
        holder.returndate.setText(bean.returndate);
        holder.position.setText(bean.position);
        if("1".equals(bean.renewtime.trim())){
        	holder.btnrenew.setBackgroundColor(color.light);
        	holder.btnrenew.setText("ÒÑÐø½è");
        }
        return convertView;
    }

    class ViewHolder{
        public TextView bookname;
        public TextView author;
        public TextView snum;
        public TextView borrowdate;
        public TextView returndate;
        public TextView position;
        public Button btnrenew;
        
    }
}

