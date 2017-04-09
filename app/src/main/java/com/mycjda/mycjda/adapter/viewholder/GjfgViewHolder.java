package com.mycjda.mycjda.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mycjda.mycjda.R;
import com.mycjda.mycjda.bean.GjfgBean;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by liyiwei on 2017/4/9.
 */

public class GjfgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private List<GjfgBean> gjfgBeanList;
    private TextView titleTv;
    private TextView dateTv;
    private int position;

    public GjfgViewHolder(View itemView, List<GjfgBean> gjfgBeanList) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.gjfgBeanList = gjfgBeanList;
        titleTv = (TextView) itemView.findViewById(R.id.title);
        dateTv = (TextView) itemView.findViewById(R.id.date);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.position = position;
        GjfgBean gjfgBean = gjfgBeanList.get(position);
        String title = gjfgBean.getTitle();
        String url = gjfgBean.getUrl();
        String date = gjfgBean.getDate();

        titleTv.setText(title);
        dateTv.setText(date);
    }

    @Override
    public void onClick(View v) {
        KLog.e(gjfgBeanList.get(position).toString());
    }
}
