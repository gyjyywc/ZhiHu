package com.gyj.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gyj.zhihu.R;
import com.gyj.zhihu.model.NewsListItem;
import java.util.List;

public class NewsAdapter extends BaseAdapter{

  private List<NewsListItem> mList;
  private LayoutInflater mLayoutInflater;

  public NewsAdapter(Context context, List<NewsListItem> data) {
    this.mList = data;
    mLayoutInflater = LayoutInflater.from(context);
  }

  @Override public int getCount() {
    return mList.size();
  }

  @Override public Object getItem(int position) {
    return mList.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    TextView tv_item = null;
    if (convertView == null) {
      convertView = mLayoutInflater.inflate(R.layout.menu_item, null);
      tv_item = (TextView) convertView.findViewById(R.id.tv_item);
    }
    if (tv_item != null) {
      tv_item.setText(mList.get(position).getName());
    }
    return convertView;
  }
}