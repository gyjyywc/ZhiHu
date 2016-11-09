package com.gyj.zhihu.model;

import java.util.List;

public class Latest {
  private List<TopStoriesEntity> top_stories;
  private List<StoriesEntity> stories;
  private String date;

  public void setTop_stories(List<TopStoriesEntity> top_stories) {
    this.top_stories = top_stories;
  }

  public void setStories(List<StoriesEntity> stories) {
    this.stories = stories;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public List<TopStoriesEntity> getTop_stories() {
    return top_stories;
  }

  public List<StoriesEntity> getStories() {
    return stories;
  }

  public String getDate() {
    return date;
  }

  public static class TopStoriesEntity {
    /**
     * id : 7048089 title : 发生类似天津爆炸事故时，该如何自救？ ga_prefix : 081309 image :
     * http://pic4.zhimg.com/494dafbd64c141fd023d4e58b3343fcb.jpg type : 0
     */
    private int id;
    private String title;
    private String ga_prefix;
    private String image;
    private int type;

    public void setId(int id) {
      this.id = id;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public void setGa_prefix(String ga_prefix) {
      this.ga_prefix = ga_prefix;
    }

    public void setImage(String image) {
      this.image = image;
    }

    public void setType(int type) {
      this.type = type;
    }

    public int getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public String getGa_prefix() {
      return ga_prefix;
    }

    public String getImage() {
      return image;
    }

    public int getType() {
      return type;
    }

    @Override public String toString() {
      return "TopStoriesEntity{"
          + "id="
          + id
          + ", title='"
          + title
          + '\''
          + ", ga_prefix='"
          + ga_prefix
          + '\''
          + ", image='"
          + image
          + '\''
          + ", type="
          + type
          + '}';
    }
  }

  public static class StoriesEntity {
    /**
     * id : 7047795 title : 央视说要干预男男性行为，具体是怎么干预法？ ga_prefix : 081310 images :
     * ["http://pic3.zhimg.com/fe27abc8f094510f2d3b4f3706108b56.jpg"] type : 0
     */
    private int id;
    private String title;
    private String ga_prefix;
    private List<String> images;
    private int type;

    public void setId(int id) {
      this.id = id;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public void setGa_prefix(String ga_prefix) {
      this.ga_prefix = ga_prefix;
    }

    public void setImages(List<String> images) {
      this.images = images;
    }

    public void setType(int type) {
      this.type = type;
    }

    public int getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public String getGa_prefix() {
      return ga_prefix;
    }

    public List<String> getImages() {
      return images;
    }

    public int getType() {
      return type;
    }

    @Override public String toString() {
      return "StoriesEntity{"
          + "id="
          + id
          + ", title='"
          + title
          + '\''
          + ", ga_prefix='"
          + ga_prefix
          + '\''
          + ", images="
          + images
          + ", type="
          + type
          + '}';
    }
  }

  @Override public String toString() {
    return "Latest{"
        + "top_stories="
        + top_stories
        + ", stories="
        + stories
        + ", date='"
        + date
        + '\''
        + '}';
  }
}
