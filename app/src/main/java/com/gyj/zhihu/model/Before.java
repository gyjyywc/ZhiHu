package com.gyj.zhihu.model;

import java.util.List;

public class Before {

  private List<Latest.StoriesEntity> stories;
  private String date;

  public void setStories(List<Latest.StoriesEntity> stories) {
    this.stories = stories;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public List<Latest.StoriesEntity> getStories() {
    return stories;
  }

  public String getDate() {
    return date;
  }


}
