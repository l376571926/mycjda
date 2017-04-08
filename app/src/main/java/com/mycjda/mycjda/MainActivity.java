package com.mycjda.mycjda;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

/**
 * 主页
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String mBaseUrl = "http://221.236.35.60/";
    private WebView webView;
    private PopupWindow mPopWindow;
    private ListView listView;
    private List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);

        findViewById(R.id.leftBtn).setOnClickListener(this);
        findViewById(R.id.centerBtn).setOnClickListener(this);
        findViewById(R.id.rightBtn).setOnClickListener(this);

        webView.loadUrl("http://mp.weixin.qq.com/s?__biz=MzIyODIzNTE5Mg==&mid=100000013&idx=1&sn=1bdbfec1e2a866d53e9495747782ed3b&mpshare=1&scene=23&srcid=1101ynQXBCOrWtgvOfixZ4Bp#rd");

        listView = new ListView(this);
        listView.setBackgroundColor(Color.WHITE);
        stringList = new ArrayList<>();
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            KLog.e(url + " " + message);
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            KLog.e(fileChooserParams);
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    };
    private int forwardCount;
    private FileCallBack fileCallBack = new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "tempDoc.doc") {
        @Override
        public void onError(Call call, Exception e, int id) {
            KLog.e(e.getMessage());
        }

        @Override
        public void onResponse(File response, int id) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(response);
            intent.setDataAndType(uri, "application/msword");
            startActivity(intent);
        }
    };
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            KLog.e(url);
            if (url.endsWith(".doc")) {
                OkHttpUtils.get()
                        .url(url)
                        .build()
                        .execute(fileCallBack);
            } else {
                forwardCount++;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                showPopwindow(findViewById(R.id.leftBtn));
                break;
            case R.id.centerBtn:
                showPopwindow(findViewById(R.id.centerBtn));
                break;
            case R.id.rightBtn:
                showPopwindow(findViewById(R.id.rightBtn));
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (forwardCount == 0) {
            DoubleClickExitApp.getInstance().onKeyDown(this);
        } else {
            if (webView.canGoBack()) {
                webView.goBack();
                forwardCount--;
            }
        }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow(View view) {
        if (stringList != null) {
            if (stringList.size() != 0) {
                stringList.clear();
            }
        }
        String[] stringArray = new String[0];
        switch (view.getId()) {
            case R.id.leftBtn:
                stringArray = getResources().getStringArray(R.array.module1_array);
                break;
            case R.id.centerBtn:
                stringArray = getResources().getStringArray(R.array.module2_array);
                break;
            case R.id.rightBtn:
                stringArray = getResources().getStringArray(R.array.module3_array);
                break;
            default:
                break;
        }
        Collections.addAll(stringList, stringArray);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.single_textview, stringList));
        listView.setOnItemClickListener(this);
        mPopWindow = new PopupWindow(listView, view.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(listView);
        mPopWindow.setBackgroundDrawable(new GradientDrawable());//不设置背景无法disMiss
        mPopWindow.showAsDropDown(view, 0, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPopWindow.dismiss();
        if (view instanceof TextView) {
            CharSequence text = ((TextView) view).getText();
            switch (String.valueOf(text)) {
                //第一栏
                case "预约查档":
                    webView.loadUrl("http://221.236.35.60/ArchiveSearch.aspx?Mid=276");
                    break;
                case "表格下载":
                    webView.loadUrl("http://221.236.35.60/Download.aspx?Mid=251");
                    GjfgRunable runable = new GjfgRunable(251, "Download.aspx?Mid=251", new OnParserFinishListener() {
                        @Override
                        public void onParserFinish(int id, List list) {
                            if (list.size() != 0) {
                                if (list.get(0) instanceof GjfgBean) {
                                    List<GjfgBean> tpzsBeanList = list;
                                    KLog.e("国家法规，数据解析完成---》" + id + " " + tpzsBeanList.size());
                                }
                            }
                        }
                    });
                    MainApplication.getExecutors().submit(runable);
                    break;
                case "网上咨询":
                    webView.loadUrl("http://221.236.35.60/Send.aspx?Mid=270");
                    WslyRunnable wslyRunnable = new WslyRunnable(270, new OnParserFinishListener() {
                        @Override
                        public void onParserFinish(int id, List list) {
                            if (list.size() != 0) {
                                if (list.get(0) instanceof WslyBean) {
                                    List<WslyBean> tpzsBeanList = list;
                                    KLog.e("网上留言，数据解析完成---》" + id + " " + tpzsBeanList.size());
                                }
                            }
                        }
                    });
                    MainApplication.getExecutors().submit(wslyRunnable);
                    break;
                case "办事指南":
                    webView.loadUrl("http://mp.weixin.qq.com/s?__biz=MzIyODIzNTE5Mg==&mid=100000002&idx=1&sn=7298da84ff738740fac73852bc7a12f7&mpshare=1&scene=23&srcid=1101hjqSUdBsEeMfv03nDgxz#rd");
                    break;
                case "档案报建":
                    webView.loadUrl("http://221.236.35.60:8012/UserLoginGather.aspx");
                    break;

                //第二栏
                case "政策介绍":
                    webView.loadUrl("http://221.236.35.60/News.aspx?Mid=110");//115也是这个页面
                    GjfgRunable gjfgRunable = new GjfgRunable(110, "News.aspx?Mid=110", new OnParserFinishListener() {
                        @Override
                        public void onParserFinish(int id, List list) {
                            if (list.size() != 0) {
                                if (list.get(0) instanceof GjfgBean) {
                                    List<GjfgBean> tpzsBeanList = list;
                                    KLog.e("国家法规，数据解析完成---》" + id + " " + tpzsBeanList.size());
                                }
                            }
                        }
                    });
                    MainApplication.getExecutors().submit(gjfgRunable);
                    break;
                case "成果展示":
                    webView.loadUrl("http://221.236.35.60/Periodical.aspx?Mid=274");
                    break;
                case "照片收集":
                    webView.loadUrl("http://221.236.35.60/Collection.aspx?Mid=272");
                    HtmlParser.tpzsParser();
                    MainApplication.getExecutors().submit(new TpzsRunable(272, new OnParserFinishListener() {
                        @Override
                        public void onParserFinish(int id, List list) {
                            if (list.size() != 0) {
                                if (list.get(0) instanceof TpzsBean) {
                                    List<TpzsBean> tpzsBeanList = list;
                                    KLog.e("图片展示，数据解析完成---》" + id + " " + tpzsBeanList.size());
                                }
                            }
                        }
                    }));
                    break;
                case "城建文化":
                    webView.loadUrl("http://mp.weixin.qq.com/s?__biz=MzIyODIzNTE5Mg==&mid=100000013&idx=1&sn=1bdbfec1e2a866d53e9495747782ed3b&mpshare=1&scene=23&srcid=1101ynQXBCOrWtgvOfixZ4Bp#rd");
                    break;
                case "问卷调查":
                    webView.loadUrl("http://221.236.35.60/SadcList.aspx?Mid=271");
                    break;

                //第三栏
                case "达标获奖":
                    webView.loadUrl("http://mp.weixin.qq.com/s?__biz=MzIyODIzNTE5Mg==&mid=100000004&idx=1&sn=c640e252c9dd45508ef0d033941003a6&mpshare=1&scene=23&srcid=1101UYEBIW9knlfj8CSEpg4l#rd");
                    break;
                case "最新通告":
                    webView.loadUrl("http://221.236.35.60/News.aspx?Mid=268");
                    break;
                case "联系方式":
                    webView.loadUrl("http://mp.weixin.qq.com/s?__biz=MzIyODIzNTE5Mg==&mid=100000011&idx=1&sn=5bc5cb45b8c3e068157ecb91442d257a&mpshare=1&scene=23&srcid=1101KUdzlR7Dk6IoeMjctm0m#rd");
                    break;
                case "地理位置":
                    Toast.makeText(this, "功能待添加", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "功能待添加", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 网上留言
     */
    private static class WslyRunnable implements Runnable {
        private int id;
        private OnParserFinishListener onParserFinishListener;

        public WslyRunnable(int id, OnParserFinishListener onParserFinishListener) {
            this.id = id;
            this.onParserFinishListener = onParserFinishListener;
        }

        @Override
        public void run() {
            try {
                Document document = Jsoup.connect(mBaseUrl + "Send.aspx?Mid=270").get();
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


    /**
     * 国家法规
     */
    private static class GjfgRunable implements Runnable {
        private int id;
        private String requestPath;
        private OnParserFinishListener onParserFinishListener;

        public GjfgRunable(int id, String requestPath, OnParserFinishListener onParserFinishListener) {
            this.id = id;
            this.requestPath = requestPath;
            this.onParserFinishListener = onParserFinishListener;
        }

        @Override
        public void run() {
            try {
                Document document = Jsoup.connect(mBaseUrl + requestPath).get();
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
                            url = mBaseUrl + href;
                            title = element2.attr("title");
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


    /**
     * 图片展示
     */
    private static class TpzsRunable implements Runnable {
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
                Document document = Jsoup.connect(mBaseUrl + "Collection.aspx?Mid=272").get();
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
                                url = mBaseUrl + href;
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

    public interface OnParserFinishListener {
        void onParserFinish(int id, List list);
    }
}