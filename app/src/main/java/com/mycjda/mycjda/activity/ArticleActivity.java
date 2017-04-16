package com.mycjda.mycjda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mycjda.mycjda.OnParserFinishListener;
import com.mycjda.mycjda.R;
import com.mycjda.mycjda.bean.ZwnrBean;
import com.mycjda.mycjda.runnable.ZwnrRunnable;
import com.socks.library.KLog;

import java.util.List;

/**
 * 城市简介，达标获奖
 */
public class ArticleActivity extends AppCompatActivity {

    private TextView mTileView;
    private ImageView mImageView;
    private TextView mContentView;
    private int mRunnableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        mTileView = (TextView) findViewById(R.id.titleView);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mContentView = (TextView) findViewById(R.id.contentView);

        String path = getIntent().getStringExtra("path");
        mRunnableId = getIntent().getIntExtra("id", 0);

        new ZwnrRunnable(mRunnableId, path, new OnParserFinishListener() {
            @Override
            public void onParserFinish(int id, List list) {
                final List<ZwnrBean> list1 = list;
                KLog.e(list1.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ZwnrBean zwnrBean = list1.get(0);
                        String title = zwnrBean.getTitle();
                        String from = zwnrBean.getFrom();
                        String author = zwnrBean.getAuthor();
                        String date = zwnrBean.getDate();
                        String count = zwnrBean.getCount();
                        List<String> contentList = zwnrBean.getContentList();

                        mTileView.append(title + "\n");
                        mTileView.append(from + "\n");
                        mTileView.append(author + "\n");
                        mTileView.append(date + "\n");
                        mTileView.append(count + "\n");

                        for (String s : contentList) {

                            if (s.contains("http")) {
                                mImageView.setVisibility(View.VISIBLE);
                                Glide.with(ArticleActivity.this)
                                        .load(s)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(mImageView);
                            } else {
                                mContentView.append(s + "\n");
                            }
                        }
                    }
                });
            }
        });
    }
}
