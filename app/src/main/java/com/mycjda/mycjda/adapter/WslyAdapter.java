package com.mycjda.mycjda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mycjda.mycjda.R;
import com.mycjda.mycjda.adapter.viewholder.WslyViewHolder;
import com.mycjda.mycjda.bean.WslyBean;

import java.util.List;

/**
 * Created by liyiwei
 * on 2017/4/16.
 */

public class WslyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<WslyBean> wslyBeanList;

    public WslyAdapter(Context context, List<WslyBean> wslyBeanList) {
        this.context = context;
        this.wslyBeanList = wslyBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WslyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_double_textview, parent, false), wslyBeanList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WslyViewHolder) {
            ((WslyViewHolder) holder).onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return wslyBeanList.size();
    }
}
