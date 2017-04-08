package com.mycjda.mycjda;

/**
 * 图片征集http://221.236.35.60/Collection.aspx?Mid=272
 * Created by liyiwei on 2017/4/8.
 */

public class TpzsBean {
    private String image;//图片
    private String title;//标题
    private String good;//赞
    private String author;//摄影者

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "TpzsBean{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", good='" + good + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
