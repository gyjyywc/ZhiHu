package com.gyj.zhihu.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.gyj.zhihu.R;
import com.gyj.zhihu.activity.MainActivity;
import com.gyj.zhihu.adapter.MenuItemAdapter;
import com.gyj.zhihu.model.NewsBean;
import com.gyj.zhihu.model.StoriesEntity;
import com.gyj.zhihu.util.Constant;
import com.gyj.zhihu.util.HttpUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MenuItemFragment extends BaseFragment {

  private ListView lv_item;
  private TextView tv_title;
  private String urlId;
  private NewsBean newsBean;
  private SimpleDraweeView sdv_title,sdv_down;
  private Handler mHandler = new Handler();

  public MenuItemFragment(String urlId) {
    this.urlId = urlId;
  }

  @Override
  protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.news_layout, container, false);
    lv_item = (ListView) view.findViewById(R.id.lv_news_item);
    View header = inflater.inflate(R.layout.news_header, lv_item, false);
    sdv_title = (SimpleDraweeView) header.findViewById(R.id.iv_title);
    sdv_down = (SimpleDraweeView) header.findViewById(R.id.downImage);
    tv_title = (TextView) header.findViewById(R.id.tv_title);
    lv_item.addHeaderView(header);
    lv_item.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
        if (lv_item != null && lv_item.getChildCount() > 0) {
          boolean enale =
              (firstVisibleItem == 0) && (lv_item.getChildAt(firstVisibleItem).getTop() == 0);
          ((MainActivity)mActivity).setSwipeRefreshEnable(enale);
        }
      }
    });
    return view;
  }

  @Override protected void initData() {
    super.initData();
    Call mCall = new HttpUtil().getAsynHttp(Constant.THEME + urlId);
    mCall.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        final String jsonData = response.body().string();
        Gson gson = new Gson();
        newsBean = gson.fromJson(jsonData, NewsBean.class);
        mHandler.post(new Runnable() {
          @Override public void run() {
            tv_title.setText(newsBean.getDescription());
            sdv_down.setImageURI(newsBean.getImage());
            Drawable drawable = sdv_down.getDrawable();
            drawable.setColorFilter(0xffcccccc, PorterDuff.Mode.MULTIPLY);
            sdv_title.setImageDrawable(drawable);
            lv_item.setAdapter(new MenuItemAdapter(mActivity, newsBean.getStories()));
          }
        });
      }
    });
  }
}
