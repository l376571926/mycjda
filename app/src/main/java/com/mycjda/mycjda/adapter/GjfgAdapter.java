package com.mycjda.mycjda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mycjda.mycjda.adapter.viewholder.GjfgViewHolder;
import com.mycjda.mycjda.R;
import com.mycjda.mycjda.bean.GjfgBean;

import java.util.List;

/**
 * Created by liyiwei on 2017/4/9.
 */

public class GjfgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GjfgBean> gjfgBeanList;

    public GjfgAdapter(Context context, List<GjfgBean> gjfgBeanList) {
        this.context = context;
        this.gjfgBeanList = gjfgBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GjfgViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_double_textview, parent, false), gjfgBeanList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GjfgViewHolder) {
            ((GjfgViewHolder) holder).onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return gjfgBeanList.size();
    }
}
