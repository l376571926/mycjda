package com.mycjda.mycjda.bean;

import java.util.List;

/**
 * Created by liyiwei on 2017/4/9.
 */

public class WznrBean {
    private String title;
    private String from;
    private String author;
    private String date;
    private String count;
    private List<String> contentList;

    @Override
    public String toString() {
        return "WznrBean{" +
                "title='" + title + '\'' +
                ", from='" + from + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", count='" + count + '\'' +
                ", contentList=" + contentList +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }
}
