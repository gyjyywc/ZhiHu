package com.gyj.zhihu.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyj.zhihu.R;
import com.gyj.zhihu.model.Latest;
import java.util.ArrayList;
import java.util.List;

public class Banner extends FrameLayout {
  private List<Latest.TopStoriesEntity> topStoriesEntities;
  private int count;
  private Context context;
  private ViewPager mViewPager;
  private boolean isAutoPlay;
  private int currentItem;
  private LinearLayout linear_dot;
  private List<ImageView> list_dots;
  private Handler mHandler = new Handler();
  private List<View> views;

  public Banner(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
    this.topStoriesEntities = new ArrayList<>();
    initFresco(context);
    initData();
  }

  public Banner(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public Banner(Context context) {
    this(context, null);
  }

  private void initData() {
    views = new ArrayList<>();
    list_dots = new ArrayList<>();
  }

  public void setTopEntities(List<Latest.TopStoriesEntity> topEntities) {
    this.topStoriesEntities = topEntities;
    setImagesUrl(topEntities);
  }


  private void setImagesUrl(List<Latest.TopStoriesEntity> topEntities) {
    initLayout();
    initImgFromNet(topEntities);
    showTime();
  }

  private void initLayout() {
    views.clear();
    View view = LayoutInflater.from(context).inflate(
        R.layout.banner_layout, this, true);
    mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    linear_dot = (LinearLayout) view.findViewById(R.id.linear_dot);
    linear_dot.removeAllViews();
  }

  private void initImgFromNet(List<Latest.TopStoriesEntity> topEntities) {
    count = topEntities.size();
    for (int i = 0; i < count; i++) {
      ImageView iv_dot = new ImageView(context);
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT,
          LinearLayout.LayoutParams.WRAP_CONTENT);
      params.leftMargin = 5;
      params.rightMargin = 5;
      iv_dot.setImageResource(R.mipmap.dot_blur);
      linear_dot.addView(iv_dot, params);
      list_dots.add(iv_dot);
    }
    list_dots.get(0).setImageResource(R.mipmap.dot_focus);

    for (int i = 0; i <= count + 1; i++) {
      View view = LayoutInflater.from(context).inflate(
          R.layout.banner_content_layout, null);
      SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.iv_title);
      SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.downImage);
      TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
      simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
      if (i == 0) {
        image.setImageURI(Uri.parse(topStoriesEntities.get(count - 1).getImage()));
        Drawable drawable = image.getDrawable();
        drawable.setColorFilter(0xffcccccc, PorterDuff.Mode.MULTIPLY);
        simpleDraweeView.setImageDrawable(drawable);

        tv_title.setText(topStoriesEntities.get(count - 1).getTitle());
      } else if (i == count + 1) {
        image.setImageURI(Uri.parse(topStoriesEntities.get(0).getImage()));
        Drawable drawable = image.getDrawable();
        drawable.setColorFilter(0xffcccccc, PorterDuff.Mode.MULTIPLY);
        simpleDraweeView.setImageDrawable(drawable);

        tv_title.setText(topStoriesEntities.get(0).getTitle());
      } else {
        image.setImageURI(Uri.parse(topStoriesEntities.get(i - 1).getImage()));
        Drawable drawable = image.getDrawable();
        drawable.setColorFilter(0xffcccccc, PorterDuff.Mode.MULTIPLY);
        simpleDraweeView.setImageDrawable(drawable);

        tv_title.setText(topStoriesEntities.get(i - 1).getTitle());
      }
      views.add(view);
    }
  }


  private void showTime() {
    mViewPager.setAdapter(new BannerPagerAdapter());
    mViewPager.setFocusable(true);
    mViewPager.setCurrentItem(1);
    currentItem = 1;
    mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    startPlay();
  }

  private void startPlay() {
    isAutoPlay = true;
    mHandler.postDelayed(task, 2000);
  }

  private void initFresco(Context context) {
    Fresco.initialize(context);
  }

  private final Runnable task = new Runnable() {

    @Override
    public void run() {
      if (isAutoPlay) {
        currentItem = currentItem % (count + 1) + 1;
        if (currentItem == 1) {
          mViewPager.setCurrentItem(currentItem, false);
          mHandler.post(task);
        } else {
          mViewPager.setCurrentItem(currentItem);
          mHandler.postDelayed(task, 3000);
        }
      } else {
        mHandler.postDelayed(task, 5000);
      }
    }
  };

  class BannerPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
      return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      container.addView(views.get(position));
      return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(views.get(position));
    }
  }

  class MyOnPageChangeListener implements OnPageChangeListener {

    @Override
    public void onPageScrollStateChanged(int arg0) {
      switch (arg0) {
        case 1:
          isAutoPlay = false;
          break;
        case 2:
          isAutoPlay = true;
          break;
        case 0:
          if (mViewPager.getCurrentItem() == 0) {
            mViewPager.setCurrentItem(count, false);
          } else if (mViewPager.getCurrentItem() == count + 1) {
            mViewPager.setCurrentItem(1, false);
          }
          currentItem = mViewPager.getCurrentItem();
          isAutoPlay = true;
          break;
      }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
      for (int i = 0; i < list_dots.size(); i++) {
        if (i == arg0 - 1) {
          list_dots.get(i).setImageResource(R.mipmap.dot_focus);
        } else {
          list_dots.get(i).setImageResource(R.mipmap.dot_blur);
        }
      }
    }
  }

  public void removeCallbacksAndMessages() {
    mHandler.removeCallbacksAndMessages(null);
    context = null;
  }
}

