package com.gyj.zhihu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.gyj.zhihu.R;
import com.gyj.zhihu.activity.MainActivity;
import com.gyj.zhihu.adapter.MainNewsAdapter;
import com.gyj.zhihu.adapter.NewsItemAdapter;
import com.gyj.zhihu.model.Before;
import com.gyj.zhihu.model.Latest;
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
  private View header;
  private Before before;
  private String date;
  private MainNewsAdapter mAdapter;
  public boolean isLoading = false;

  private Handler handler = new Handler() {
    @Override public void handleMessage(Message msg) {
      switch (msg.what) {
        case Constant.LATESTNUM:
          String latestJsonData = (String) msg.obj;
          setFirstData(latestJsonData);
          //break;
        case Constant.BEFORENUM:
          String beforeJsonData = (String) msg.obj;
          setBeforeData(beforeJsonData);
          break;
      }
    }
  };

  @Override
  protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ((MainActivity) mActivity).setToolbarTitle("今日热闻");
    View view = inflater.inflate(R.layout.right_layout, container, false);
    lv_news = (ListView) view.findViewById(R.id.lv_item_content);
    header = inflater.inflate(R.layout.header_banner, lv_news, false);
    banner = (Banner) header.findViewById(R.id.banner);
    lv_news.addHeaderView(header);

    lv_news.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {
        if (lv_news != null && lv_news.getChildCount() > 0) {//getChildCount() 返回的是当前可见的 Item 个数
          boolean enable =
              (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop() == 0);
          ((MainActivity) mActivity).setSwipeRefreshEnable(enable);
          if (firstVisibleItem + visibleItemCount == totalItemCount && !isLoading) {
            loadMore(Constant.BEFORE + date, Constant.BEFORENUM);
          }
        }
      }
    });
    return view;
  }

  private void loadMore(String url, int beforeNum) {
    isLoading = true;
    okHttp(url, beforeNum);
  }

  @Override protected void initData() {
    okHttp(Constant.LATEST, Constant.LATESTNUM);
  }

  private void okHttp(String url, final int Num) {
    Call mCall = new HttpUtil().getAsynHttp(url);
    mCall.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        String jsonString = response.body().string();
        Message message = new Message();
        message.what = Num;
        message.obj = jsonString;
        handler.sendMessage(message);
      }
    });
  }

  private void setFirstData(String jsondata) {
    Gson gson = new Gson();
    Latest latest = gson.fromJson(jsondata, Latest.class);
    List<Latest.StoriesEntity> storiesEntities = latest.getStories();
    banner.setTopEntities(latest.getTop_stories());
    Latest.StoriesEntity topic = new Latest.StoriesEntity();
    topic.setTitle("今日热闻");
    topic.setType(Constant.TOPIC);
    storiesEntities.add(0, topic);
    mAdapter = new MainNewsAdapter(mActivity,storiesEntities);
    lv_news.setAdapter(mAdapter);
  }

  private void setBeforeData(String jsondata) {
    Gson gson = new Gson();
    before = gson.fromJson(jsondata, Before.class);
    date = before.getDate();
    List<Latest.StoriesEntity> storiesEntities = before.getStories();
    Latest.StoriesEntity topic = new Latest.StoriesEntity();
    topic.setType(Constant.TOPIC);
    topic.setTitle(convertDate(date));
    storiesEntities.add(0, topic);
    mAdapter.addList(storiesEntities);
    isLoading = false;
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
