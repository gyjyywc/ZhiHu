package com.gyj.zhihu.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
  protected Activity mActivity;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mActivity = getActivity();
    return initView(inflater, container, savedInstanceState);
  }

  protected abstract View initView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState);

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initData();
  }

  protected void initData() {

  }

  @Override public void onDestroy() {
    super.onDestroy();
    mActivity = null;
  }
}
