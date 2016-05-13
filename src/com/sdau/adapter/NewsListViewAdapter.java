package com.sdau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.sdau.bean.NewsItemBean;
import com.sdau.superlibrary.R;

public class NewsListViewAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    // 映射数据
    private List<NewsItemBean> mDataList;
    //private long mSumTime;

    public NewsListViewAdapter(Context context, List<NewsItemBean> list) {
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
   /*     // 逗比�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 获取纳秒时间 更加精确
        long start = System.nanoTime();
        // 由于我们只需要将XML转化为View，并不涉及到具体的布�?���?��第二个参数�?常设置为null
        View view = mLayoutInflater.inflate(R.layout.listview_item, null);
        // 实例化控�?
        ImageView itemImage = (ImageView) view.findViewById(R.id.iv_image);
        TextView itemTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView itemContent = (TextView) view.findViewById(R.id.tv_content);
        // 取出bean对象
        ItemBean bean = mDataList.get(position);
        // 设置控件的数�?
        itemImage.setImageResource(bean.itemImageResid);
        itemTitle.setText(bean.itemTitle);
        itemContent.setText(bean.itemContent);
        long end = System.nanoTime();
        long dValue = end - start;
        mSumTime += dValue;
        // 输出每次getView消�?的时间和
        Log.d("xys", String.valueOf(mSumTime));
        return view;
        // 逗比�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>24409529
*/
        // 普�?�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // 获取纳秒时间 更加精确
//        long start = System.nanoTime();
//        if (convertView == null) {
        // 由于我们只需要将XML转化为View，并不涉及到具体的布�?���?��第二个参数�?常设置为null
//            convertView = mLayoutInflater.inflate(R.layout.item, null);
//        }
//        ImageView itemImage = (ImageView) convertView.findViewById(R.id.iv_image);
//        TextView itemTitle = (TextView) convertView.findViewById(R.id.tv_title);
//        TextView itemContent = (TextView) convertView.findViewById(R.id.tv_content);
//        // 取出bean对象
//        ItemBean bean = mDataList.get(position);
        // 设置控件的数�?
//        itemImage.setImageResource(bean.itemImageResid);
//        itemTitle.setText(bean.itemTitle);
//        itemContent.setText(bean.itemContent);
//        long end = System.nanoTime();
//        long dValue = end - start;
//        mSumTime += dValue;
        // 输出每次getView消�?的时间和
//        Log.d("xys", String.valueOf(mSumTime));
//        return convertView;
        // 普�?�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>19271161


        // 文艺�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布�?���?��第二个参数�?常设置为null
            convertView = mLayoutInflater.inflate(R.layout.news_list_item, null);
            holder.nian = (TextView) convertView.findViewById(R.id.tv_nian);
            holder.yue = (TextView) convertView.findViewById(R.id.tv_yue);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 取出bean对象
        NewsItemBean bean = mDataList.get(position);
        // 设置控件的数�?        holder.nian.setText(bean.itemNian);
        holder.yue.setText(bean.itemYue);
        holder.title.setText(bean.itemTitle);
        return convertView;
        // 文艺�?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>16325820
    }

    // ViewHolder用于缓存控件
    class ViewHolder{
        public TextView nian;
        public TextView yue;
        public TextView title;
    }
}

