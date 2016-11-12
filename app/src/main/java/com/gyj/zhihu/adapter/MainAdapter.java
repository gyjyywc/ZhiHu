package com.gyj.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyj.zhihu.R;
import com.gyj.zhihu.model.StoriesEntity;
import com.gyj.zhihu.util.Constant;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends BaseAdapter {

  private List<StoriesEntity> entities;
  private Context context;

  public MainAdapter(Context context) {
    this.context = context;
    this.entities = new ArrayList<>();
  }

  public void addList(List<StoriesEntity> items) {
    this.entities.addAll(items);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return entities.size();
  }

  @Override
  public Object getItem(int position) {
    return entities.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = LayoutInflater.from(context).inflate(R.layout.main_news_item, parent, false);
      viewHolder.tv_topic = (TextView) convertView.findViewById(R.id.tv_topic);
      viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
      viewHolder.iv_title = (SimpleDraweeView) convertView.findViewById(R.id.iv_title);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    StoriesEntity entity = entities.get(position);
    if (entity.getType() == Constant.TOPIC) {
      viewHolder.tv_title.setVisibility(View.GONE);
      viewHolder.iv_title.setVisibility(View.GONE);
      viewHolder.tv_topic.setVisibility(View.VISIBLE);
      viewHolder.tv_topic.setText(entity.getTitle());
    } else {
      viewHolder.tv_topic.setVisibility(View.GONE);
      viewHolder.tv_title.setVisibility(View.VISIBLE);
      viewHolder.iv_title.setVisibility(View.VISIBLE);
      viewHolder.tv_title.setText(entity.getTitle());
      viewHolder.iv_title.setImageURI(entity.getImages().get(0));
    }
    return convertView;
  }

  public static class ViewHolder {
    TextView tv_topic;
    TextView tv_title;
    SimpleDraweeView iv_title;
  }
}
