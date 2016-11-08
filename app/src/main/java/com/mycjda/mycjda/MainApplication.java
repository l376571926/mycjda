package com.mycjda.mycjda;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by liyiwei
 * on 2016/11/8.
 */

public class MainApplication extends Application {
    private static final int SET_CONNECTION_TIMEOUT = 5 * 1000;
    private static final int SET_SOCKET_TIMEOUT = 20 * 1000;

    @Override
    public void onCreate() {
        super.onCreate();
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(this));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(SET_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(SET_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(SET_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
