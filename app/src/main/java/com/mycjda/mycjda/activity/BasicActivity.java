package com.mycjda.mycjda.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mycjda.mycjda.MainApplication;
import com.mycjda.mycjda.OnParserFinishListener;
import com.mycjda.mycjda.R;
import com.mycjda.mycjda.adapter.GjfgAdapter;
import com.mycjda.mycjda.adapter.MyAdapter;
import com.mycjda.mycjda.bean.GjfgBean;
import com.mycjda.mycjda.bean.TpzsBean;
import com.mycjda.mycjda.other.Constants;
import com.mycjda.mycjda.runnable.TpzsRunable;
import com.mycjda.mycjda.runnable.WzlistRunable;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

public class BasicActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<TpzsBean> tpzsBeanList;
    private MyAdapter myAdapter;
    private List<GjfgBean> gjfgBeanList;
    private GjfgAdapter gjfgAdapter;
    private int mRunnableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                finish();
            }
        });

        String path = getIntent().getStringExtra("path");
        mRunnableId = getIntent().getIntExtra("id", 0);
        KLog.e(path + " " + mRunnableId);

        switch (String.valueOf(path)) {
            case Constants.PATH_ZPSJ:
                tpzsBeanList = new ArrayList<>();
                myAdapter = new MyAdapter(BasicActivity.this, tpzsBeanList);
                mRecyclerView.setAdapter(myAdapter);
                TpzsRunable tpzsRunable = new TpzsRunable(mRunnableId, new OnParserFinishListener() {
                    @Override
                    public void onParserFinish(int id, final List list) {
                        if (list.size() != 0) {
                            if (list.get(0) instanceof TpzsBean) {
                                KLog.e("图片展示，数据解析完成---》" + id + " " + ((List<TpzsBean>) list).size());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        BasicActivity.this.tpzsBeanList.clear();
                                        BasicActivity.this.tpzsBeanList.addAll(list);
                                        myAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                });
                MainApplication.getExecutors().submit(tpzsRunable);
                break;
            case Constants.PATH_BGXZ:
            case Constants.PATH_ZXTG:
            case Constants.PATH_ZCJS:
                gjfgBeanList = new ArrayList<>();
                gjfgAdapter = new GjfgAdapter(BasicActivity.this, gjfgBeanList);
                mRecyclerView.setAdapter(gjfgAdapter);
                new WzlistRunable(mRunnableId, path, new OnParserFinishListener() {
                    @Override
                    public void onParserFinish(int id, final List list) {
                        if (list.size() != 0) {
                            if (list.get(0) instanceof GjfgBean) {
                                KLog.e("图片展示，数据解析完成---》" + id + " " + ((List<GjfgBean>) list).size());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        BasicActivity.this.gjfgBeanList.clear();
                                        BasicActivity.this.gjfgBeanList.addAll(list);
                                        gjfgAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
