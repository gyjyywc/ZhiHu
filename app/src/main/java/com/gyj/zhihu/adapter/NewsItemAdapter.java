package com.gyj.zhihu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyj.zhihu.R;
import com.gyj.zhihu.model.Latest;
import java.util.List;

public class NewsItemAdapter extends BaseAdapter {

  private List<Latest.StoriesEntity> entities;
  private Context context;

  public NewsItemAdapter(Context context, List<Latest.StoriesEntity> items) {
    this.context = context;
    entities = items;
    Fresco.initialize(context);
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
    ViewHolder viewHolder = null;
    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = LayoutInflater.from(context).inflate(R.layout.news_item, null);
      viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
      viewHolder.iv_title = (SimpleDraweeView) convertView.findViewById(R.id.iv_title);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    Latest.StoriesEntity entity = entities.get(position);
    viewHolder.tv_title.setText(entity.getTitle());
    if (entity.getImages() != null) {
      viewHolder.iv_title.setVisibility(View.VISIBLE);
      viewHolder.iv_title.setImageURI(entity.getImages().get(0));
    } else {
      viewHolder.iv_title.setVisibility(View.GONE);
    }
    return convertView;
  }

  public static class ViewHolder {
    TextView tv_title;
    SimpleDraweeView iv_title;
  }
}
