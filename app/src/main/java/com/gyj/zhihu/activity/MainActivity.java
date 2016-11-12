package com.gyj.zhihu.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.gyj.zhihu.R;
import com.gyj.zhihu.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

  private String curId;
  private long exitTime = 0;

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.fl_content) FrameLayout fl_content;
  @BindView(R.id.drawerlayout) DrawerLayout mDrawerLayout;
  @BindView(R.id.sr) SwipeRefreshLayout sr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initView();
    loadLatest();
  }

  private void initView() {
    mToolbar.setTitle("首页");
    setSupportActionBar(mToolbar);
    sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        replaceFragment();
        sr.setRefreshing(false);
      }
    });

    final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
        mToolbar, R.string.app_name, R.string.app_name);
    mDrawerLayout.addDrawerListener(drawerToggle);
    drawerToggle.syncState();
  }

  public void loadLatest() {
    getSupportFragmentManager().beginTransaction().
        setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).
        replace(R.id.fl_content, new MainFragment(), "latest").
        commit();
    curId = "latest";
  }

  public void replaceFragment() {
    if (curId.equals("latest")) {
      getSupportFragmentManager().beginTransaction()
          .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
          .replace(R.id.fl_content, new MainFragment(), "latest")
          .commit();
    }
  }

  public void closeMenu() {
    mDrawerLayout.closeDrawers();
  }

  public void setCurId(String id) {
    curId = id;
  }

  public void setToolbarTitle(String text) {
    mToolbar.setTitle(text);
  }

  public void setSwipeRefreshEnable(boolean enable) {
    sr.setEnabled(enable);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_mode:
        Toast.makeText(this, "点击了夜间设置", Toast.LENGTH_SHORT).show();
        break;
      case R.id.action_setting:
        Toast.makeText(this, "点击了设置选项", Toast.LENGTH_SHORT).show();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if ((System.currentTimeMillis() - exitTime) > 2000) {
        Toast.makeText(getApplicationContext(), "再按一次退出当前界面", Toast.LENGTH_SHORT).show();
        exitTime = System.currentTimeMillis();
      } else {
        finish();
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
}