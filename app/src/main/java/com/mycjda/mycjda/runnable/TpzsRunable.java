package com.mycjda.mycjda.runnable;

import android.text.TextUtils;

import com.mycjda.mycjda.MainApplication;
import com.mycjda.mycjda.OnParserFinishListener;
import com.mycjda.mycjda.bean.TpzsBean;
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
 * 图片展示
 */
public class TpzsRunable implements Runnable {
    private int id;
    private OnParserFinishListener onParserFinishListener;

    public TpzsRunable(int id, OnParserFinishListener onParserFinishListener) {
        this.id = id;
        this.onParserFinishListener = onParserFinishListener;
        MainApplication.getExecutors().submit(this);
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(Constants.BASE_URL + "Collection.aspx?Mid=272").get();
            Elements tpzs = document.getElementsByClass("tpzs");
            List<TpzsBean> tpzsBeanList = new ArrayList<>();
            for (Element element : tpzs) {
                Elements li = element.getElementsByTag("li");
                for (Element element1 : li) {
                    String url = null;
                    String title = null;
                    String date = null;
                    String good = null;
                    String author = null;

                    Elements tp = element1.getElementsByClass("tp");
                    for (Element element2 : tp) {
                        Elements a = element2.getElementsByTag("a");
                        for (Element element3 : a) {
                            String href = element3.attr("href");
                            url = Constants.BASE_URL + href;
                        }
                    }
                    Elements zjbt = element1.getElementsByClass("zjbt");
                    for (Element element2 : zjbt) {
                        title = element2.text();
                    }
                    Elements dz = element1.getElementsByClass("dz");
                    for (Element element2 : dz) {
                        Elements span = element2.getElementsByTag("span");
                        good = span.text();
                        Elements strong = element2.getElementsByTag("strong");
                        author = strong.text();
                    }
                    if (!TextUtils.isEmpty(url)) {
                        TpzsBean tpzsBean = new TpzsBean();
                        tpzsBean.setImage(url);
                        tpzsBean.setTitle(title);
                        tpzsBean.setGood(good);
                        tpzsBean.setAuthor(author);
                        tpzsBeanList.add(tpzsBean);

                        KLog.e(tpzsBean.toString());
                    }
                }
            }
            if (onParserFinishListener != null) {
                onParserFinishListener.onParserFinish(id, tpzsBeanList);
                onParserFinishListener = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}