package com.gyj.zhihu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.gyj.zhihu.R;
import com.gyj.zhihu.adapter.NewsAdapter;
import com.gyj.zhihu.model.NewsListItem;
import com.gyj.zhihu.util.Constant;
import com.gyj.zhihu.util.HttpUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuFragment extends BaseFragment {

  private ListView mLvItem;
  private TextView tv_download, tv_main, tv_backup, tv_login;
  private LinearLayout ll_menu;

  private Handler mHandler = new Handler(){
    @Override public void handleMessage(Message msg) {
      switch (msg.what) {
        case 1:
          String jsonString = (String) msg.obj;
          List<NewsListItem> jsonData = getJsonData(jsonString);
          NewsAdapter adapter = new NewsAdapter(mActivity, jsonData);
          mLvItem.setAdapter(adapter);
          break;
      }
    }
  };

  @Override
  protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.left_layout, container, false);
    ll_menu = (LinearLayout) view.findViewById(R.id.linear_menu);
    tv_login = (TextView) view.findViewById(R.id.login_account);
    tv_backup = (TextView) view.findViewById(R.id.myCollection);
    tv_download = (TextView) view.findViewById(R.id.downLoadOffline);
    tv_main = (TextView) view.findViewById(R.id.tv_main);
    mLvItem = (ListView) view.findViewById(R.id.lv_item);
    return view;
  }

  @Override protected void initData() {
    Call mCall = new HttpUtil().getAsynHttp(Constant.THEMES);
    mCall.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        String jsonString = response.body().string();
        Message message = new Message();
        message.what = 1;
        message.obj = jsonString;
        mHandler.sendMessage(message);
      }
    });
  }

  private List<NewsListItem> getJsonData(String jsonString) {
    List<NewsListItem> mList = new ArrayList<>();
    try {
      JSONObject jsonObject;
      NewsListItem item;
      jsonObject = new JSONObject(jsonString);
      JSONArray jsonArray = jsonObject.getJSONArray("others");
      for (int i = 0; i < jsonArray.length(); i++) {//遍历jsonArray，获取每一个数据
        jsonObject = jsonArray.getJSONObject(i);
        item = new NewsListItem();
        item.setId(jsonObject.getString("id"));
        item.setName(jsonObject.getString("name"));
        mList.add(item);
        Log.d("tag", item.getName());
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return mList;
  }
}
