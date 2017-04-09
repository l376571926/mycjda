package com.mycjda.mycjda.bean;

/**
 * Created by liyiwei on 2017/4/8.
 */

public class GjfgBean {
    private String url;
    private String title;
    private String date;

    @Override
    public String toString() {
        return "GjfgBean{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
