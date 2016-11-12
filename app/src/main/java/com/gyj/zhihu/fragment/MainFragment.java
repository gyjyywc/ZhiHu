package com.gyj.zhihu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.gyj.zhihu.R;
import com.gyj.zhihu.activity.MainActivity;
import com.gyj.zhihu.adapter.MainAdapter;
import com.gyj.zhihu.model.Before;
import com.gyj.zhihu.model.Latest;
import com.gyj.zhihu.model.StoriesEntity;
import com.gyj.zhihu.util.Banner;
import com.gyj.zhihu.util.Constant;
import com.gyj.zhihu.util.HttpUtil;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainFragment extends BaseFragment {

  private ListView lv_news;
  private Banner banner;
  private Before before;
  private String date;
  private MainAdapter mAdapter;
  public boolean isLoading = false;
  private Handler mHandler = new Handler();

  @Override
  protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.right_layout, container, false);
    lv_news = (ListView) view.findViewById(R.id.lv_item_content);
    View header = inflater.inflate(R.layout.header_banner, lv_news, false);
    banner = (Banner) header.findViewById(R.id.banner);
    lv_news.addHeaderView(header);
    mAdapter = new MainAdapter(mActivity);
    lv_news.setAdapter(mAdapter);
    lv_news.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
        if (lv_news != null && lv_news.getChildCount() > 0) {//getChildCount() 返回的是当前可见的 Item 个数
          boolean enable =
              (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop() == 0);
          ((MainActivity) mActivity).setSwipeRefreshEnable(enable);
          if (enable) {
            ((MainActivity) mActivity).setToolbarTitle("首页");
          }
          if (firstVisibleItem + visibleItemCount == totalItemCount && !isLoading) {
            loadMore(Constant.BEFORE + date);
          }
        }
      }
    });
    return view;
  }

  private void loadMore(String url) {
    isLoading = true;
    Call mCall = new HttpUtil().getAsynHttp(url);
    mCall.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        final String jsonString = response.body().string();
        mHandler.post(new Runnable() {
          @Override public void run() {
            setBeforeData(jsonString);
          }
        });
      }
    });
  }

  @Override protected void initData() {
    isLoading = true;
    Call mCall = new HttpUtil().getAsynHttp(Constant.LATEST);
    mCall.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        final String jsonString = response.body().string();
        mHandler.post(new Runnable() {
          @Override public void run() {
            setFirstData(jsonString);
          }
        });
      }
    });
  }

  private void setFirstData(String jsondata) {
    Gson gson = new Gson();
    final Latest latest = gson.fromJson(jsondata, Latest.class);
    date = latest.getDate();
    banner.setTopEntities(latest.getTop_stories());
    List<StoriesEntity> storiesEntities = latest.getStories();
    StoriesEntity topic = new StoriesEntity();
    topic.setType(Constant.TOPIC);
    topic.setTitle("今日热闻");
    storiesEntities.add(0, topic);
    mAdapter.addList(storiesEntities);
    isLoading = false;
  }

  private void setBeforeData(String jsondata) {
    Gson gson = new Gson();
    before = gson.fromJson(jsondata, Before.class);
    if (before == null) {
      isLoading = false;
      return;
    }
    date = before.getDate();
    mHandler.post(new Runnable() {
      @Override public void run() {
        List<StoriesEntity> storiesEntities = before.getStories();
        StoriesEntity topic = new StoriesEntity();
        topic.setType(Constant.TOPIC);
        topic.setTitle(convertDate(date));
        storiesEntities.add(0, topic);
        mAdapter.addList(storiesEntities);
        isLoading = false;
      }
    });
  }

  private String convertDate(String date) {
    //beginIndex - 开始处的索引（包括）。
    //endIndex - 结束处的索引（不包括）。
    String result = date.substring(0, 4);
    result += "年";
    result += date.substring(4, 6);
    result += "月";
    result += date.substring(6, 8);
    result += "日";
    return result;
  }

  @Override public void onDestroy() {
    banner.removeCallbacksAndMessages();
    super.onDestroy();
  }
}
