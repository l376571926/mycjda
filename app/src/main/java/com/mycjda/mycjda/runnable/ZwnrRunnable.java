package com.mycjda.mycjda.runnable;

import com.mycjda.mycjda.MainApplication;
import com.mycjda.mycjda.OnParserFinishListener;
import com.mycjda.mycjda.bean.ZwnrBean;
import com.mycjda.mycjda.other.Constants;
import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 城市简介
 * Created by liyiwei
 * on 2017/4/15.
 */

public class ZwnrRunnable implements Runnable {
    private int id;
    private OnParserFinishListener onParserFinishListener;

    public ZwnrRunnable(int id, OnParserFinishListener onParserFinishListener) {
        this.id = id;
        this.onParserFinishListener = onParserFinishListener;
        MainApplication.getExecutors().submit(this);
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(Constants.BASE_URL + Constants.PATH_CJWH).get();
            ZwnrBean zwnrBean = new ZwnrBean();
            Elements zwnr = document.getElementsByClass("zwnr");
            for (Element element : zwnr) {
                Elements h2 = element.getElementsByTag("h2");
                for (Element element1 : h2) {
                    String title = element1.text();
                    KLog.e(title);
                    zwnrBean.setTitle(title);
                }
            }
            Elements wzly = document.getElementsByClass("wzly");
            for (Element element1 : wzly) {
                Elements span = element1.getElementsByTag("span");
                for (int i = 0; i < span.size(); i++) {
                    Element element2 = span.get(i);
                    String text = element2.text();
                    KLog.e(text);
                    switch (i) {
                        case 0:
                            zwnrBean.setFrom(text);
                            break;
                        case 1:
                            zwnrBean.setAuthor(text);
                            break;
                        case 2:
                            zwnrBean.setDate(text);
                            break;
                        case 3:
                            zwnrBean.setCount(text);
                            break;
                        default:
                            break;
                    }
                }
            }
            Elements content_wz = document.getElementsByClass("content_wz");
            for (Element element1 : content_wz) {
                List<String> stringList = new ArrayList<>();
                Elements img = element1.getElementsByTag("img");
                for (Element element3 : img) {
                    String src = element3.attr("src");
                    stringList.add(Constants.BASE_URL + src);
//                    KLog.e("src--------->" + src);
                }
                Elements div = element1.getElementsByTag("div");
                for (int i = 1; i < div.size(); i++) {
                    Element element = div.get(i);
                    String text = element.text();
//                    KLog.e("content_wz---->" + text);
                    stringList.add(text);
                }
                zwnrBean.setContentList(stringList);
            }
            KLog.e(zwnrBean.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
