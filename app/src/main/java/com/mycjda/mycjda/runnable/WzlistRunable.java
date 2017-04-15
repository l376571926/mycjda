package com.mycjda.mycjda.runnable;

import android.text.TextUtils;

import com.mycjda.mycjda.MainApplication;
import com.mycjda.mycjda.OnParserFinishListener;
import com.mycjda.mycjda.bean.GjfgBean;
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
 * 国家法规,审查公千,表格下载
 */
public class WzlistRunable implements Runnable {
    private int id;
    private String requestPath;
    private OnParserFinishListener onParserFinishListener;

    public WzlistRunable(int id, String requestPath, OnParserFinishListener onParserFinishListener) {
        this.id = id;
        this.requestPath = requestPath;
        this.onParserFinishListener = onParserFinishListener;
        MainApplication.getExecutors().submit(this);
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(Constants.BASE_URL + requestPath).get();
            Elements wzlist = document.getElementsByClass("wzlist");
            List<GjfgBean> gjfgBeanList = new ArrayList<>();
            for (Element element : wzlist) {
                Elements li = element.getElementsByTag("li");
                for (Element element1 : li) {
                    Elements a = element1.getElementsByTag("a");
                    String url = null;
                    String title = null;
                    String date = null;
                    for (Element element2 : a) {
                        String href = element2.attr("href");
                        url = Constants.BASE_URL + href;
                        title = element2.text();
                    }
                    Elements span = element1.getElementsByTag("span");
                    for (Element element2 : span) {
                        Elements span1 = element2.getElementsByTag("span");
                        for (Element element3 : span1) {
                            date = element3.text();
                        }
                    }
                    if (!TextUtils.isEmpty(url)) {
                        GjfgBean gjfgBean = new GjfgBean();
                        gjfgBean.setUrl(url);
                        gjfgBean.setTitle(title);
                        gjfgBean.setDate(date);
                        gjfgBeanList.add(gjfgBean);

                        KLog.e(gjfgBean.toString());
                    }
                }
            }
            if (onParserFinishListener != null) {
                onParserFinishListener.onParserFinish(id, gjfgBeanList);
                onParserFinishListener = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}