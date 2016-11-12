package com.gyj.zhihu.model;

import java.util.List;

public class NewsBean {
  private List<StoriesEntity> stories;
  private String description;
  private String background;
  private int color;
  private String name;
  private String image;
  private List<Editors> editorses;

  public List<StoriesEntity> getStories() {
    return stories;
  }

  public void setStories(List<StoriesEntity> stories) {
    this.stories = stories;
  }

  public String getDescription() {
    return description;
  }

  public void setDescriptione(String description) {
    this.description = description;
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String background) {
    this.background = background;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public List<Editors> getEditorses() {
    return editorses;
  }

  public void setEditorses(List<Editors> editorses) {
    this.editorses = editorses;
  }

  public static class Editors{
    private String url;
    private String bio;
    private int id;
    private String avatar;
    private String name;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getBio() {
      return bio;
    }

    public void setBio(String bio) {
      this.bio = bio;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getAvatar() {
      return avatar;
    }

    public void setAvatar(String avatar) {
      this.avatar = avatar;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
