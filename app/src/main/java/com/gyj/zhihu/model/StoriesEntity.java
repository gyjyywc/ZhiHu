package com.gyj.zhihu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wwjun.wang on 2015/8/14.
 */
public class StoriesEntity implements Serializable{

    private int id;
    private String title;
    private List<String> images;
    private int type;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
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

//    public String getGa_prefix() {
//        return ga_prefix;
//    }

    public List<String> getImages() {
        return images;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "StoriesEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                /*", ga_prefix='" + ga_prefix + '\'' +*/
                ", images=" + images +
                ", type=" + type +
                '}';
    }
}
