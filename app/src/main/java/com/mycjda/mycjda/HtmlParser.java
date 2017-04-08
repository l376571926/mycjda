package com.mycjda.mycjda;

import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyiwei on 2017/4/8.
 */

public class HtmlParser {
    public static final String mBaseUrl = "http://221.236.35.60/";

    /**
     * 图片收集
     * http://221.236.35.60/Collection.aspx?Mid=272#
     */
    public static void tpzsParser() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(mBaseUrl + "Collection.aspx?Mid=272").get();
                    Elements tpzs = document.getElementsByClass("tpzs");
                    List<TpzsBean> tpzsBeanList = new ArrayList<>();
                    for (Element element : tpzs) {
                        Elements li = element.getElementsByTag("li");
                        for (Element element1 : li) {
                            TpzsBean tpzsBean = new TpzsBean();
                            Elements tp = element1.getElementsByClass("tp");
                            for (Element element2 : tp) {
                                Elements a = element2.getElementsByTag("a");
                                for (Element element3 : a) {
                                    String href = element3.attr("href");
                                    tpzsBean.setImage(mBaseUrl + href);
                                }
                            }
                            Elements zjbt = element1.getElementsByClass("zjbt");
                            for (Element element2 : zjbt) {
                                String text = element2.text();
                                tpzsBean.setTitle(text);
                            }
                            Elements dz = element1.getElementsByClass("dz");
                            for (Element element2 : dz) {
                                Elements span = element2.getElementsByTag("span");
                                String text = span.text();
                                tpzsBean.setGood(text);
                                Elements strong = element2.getElementsByTag("strong");
                                String text1 = strong.text();
                                tpzsBean.setAuthor(text1);
                            }
                            KLog.e(tpzsBean.toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        MainApplication.getExecutors().submit(runnable);
    }
}
