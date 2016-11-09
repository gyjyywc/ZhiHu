package com.gyj.zhihu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.gyj.zhihu.util.Constant;
import com.gyj.zhihu.R;
import com.gyj.zhihu.util.FrescoUtil;
import com.gyj.zhihu.util.HttpUtil;
import com.gyj.zhihu.model.Bean;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends Activity {

  private static final int NUM = 1;
  private static final String PATH = FrescoUtil.DOWNLOAD_PATH + "down.jpg";
  private static String imageUrl = null;
  private static SharedPreferences mPref = null;
  private static SharedPreferences.Editor mEditor = null;

  @BindView(R.id.iv_splash) SimpleDraweeView mImageView;

  private Handler mHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      switch (msg.what) {
        case NUM:
          String url = (String) msg.obj;
          mPref = getSharedPreferences("imageUrl", MODE_PRIVATE);
          mEditor = mPref.edit();
          imageUrl = mPref.getString("img", "");
          if (imageUrl == null) {
            mEditor.putString("img", url);
            mEditor.commit();
          } else if (imageUrl != url) {
            FrescoUtil.savePicture(url, SplashActivity.this);
            mEditor.putString("img", url);
            mEditor.commit();
          }
      }
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.splash_layout);
    ButterKnife.bind(this);
    getUrl();
    initImage();
  }

  private void getUrl() {
    Call mCall = new HttpUtil().getAsynHttp(Constant.STARTIMAGE);
    mCall.enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        String result = response.body().string();
        Gson gson = new Gson();
        Bean bean = gson.fromJson(result, Bean.class);
        String url = bean.getImg();
        Message message = new Message();
        message.what = 1;
        message.obj = url;
        mHandler.sendMessage(message);
      }
    });
  }

  private void initImage() {
    File file = new File(PATH);
    if (file.exists()) {
      Uri localUrl = Uri.fromFile(file);
      mImageView.setImageURI(localUrl);
    } else {
      mImageView.setImageResource(R.drawable.start);
    }

    ScaleAnimation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        0.5f);
    animation.setFillAfter(true);
    animation.setDuration(3000);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
            android.R.anim.fade_out);
        finish();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    mImageView.startAnimation(animation);
  }
}