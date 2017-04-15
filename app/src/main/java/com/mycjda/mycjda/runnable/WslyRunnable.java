package com.mycjda.mycjda.runnable;

import com.mycjda.mycjda.OnParserFinishListener;
import com.mycjda.mycjda.bean.WslyBean;
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
 * 网上留言
 */
public class WslyRunnable implements Runnable {
    private int id;
    private OnParserFinishListener onParserFinishListener;

    public WslyRunnable(int id, OnParserFinishListener onParserFinishListener) {
        this.id = id;
        this.onParserFinishListener = onParserFinishListener;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(Constants.BASE_URL + Constants.PATH_WSZX).get();
            Elements wsly = document.getElementsByClass("wsly");
            for (Element element : wsly) {
                Elements ywjd = element.getElementsByClass("ywjd");
                List<WslyBean> wslyBeanList = new ArrayList<>();
                for (Element element1 : ywjd) {
                    String topic = null;
                    String date = null;
                    String question = null;
                    String replier = null;
                    String answer = null;

                    Elements wtbt = element1.getElementsByClass("wtbt");
                    for (Element element2 : wtbt) {//数据有问题
                        topic = element2.text();//主题：我上传的图片你们看到了吗？2016-06-22
                        Elements span = element2.getElementsByTag("span");
                        for (Element element3 : span) {
                            date = element3.text();
                        }
                    }
                    Elements p = element1.getElementsByTag("p");
                    for (Element element2 : p) {
                        question = element2.text();
                    }
                    Elements wthd = element1.getElementsByClass("wthd");
                    for (Element element2 : wthd) {
                        Elements span = element2.getElementsByTag("span");
                        for (Element element3 : span) {
                            replier = element3.text();
                        }
                        answer = element2.text();
                    }
                    WslyBean wslyBean = new WslyBean();
                    wslyBean.setTopic(topic);
                    wslyBean.setDate(date);
                    wslyBean.setQuestion(question);
                    wslyBean.setReplier(replier);
                    wslyBean.setAnswer(answer);
                    wslyBeanList.add(wslyBean);

                    KLog.e(wslyBean);
                }
                if (onParserFinishListener != null) {
                    onParserFinishListener.onParserFinish(id, wslyBeanList);
                    onParserFinishListener = null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}