package com.gyj.zhihu.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

  public Call getAsynHttp(String url) {
    OkHttpClient mOkHttpClient = new OkHttpClient();
    final Request request = new Request.Builder()
        .url(Constant.BASEURL+url)
        .build();
    return mOkHttpClient.newCall(request);
  }
}
