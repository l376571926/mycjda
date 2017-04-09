package com.mycjda.mycjda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycjda.mycjda.R;
import com.mycjda.mycjda.adapter.viewholder.TpzsViewHolder;
import com.mycjda.mycjda.bean.TpzsBean;

import java.util.List;

/**
 * Created by liyiwei on 2017/4/9.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<TpzsBean> tpzsBeanList;

    public MyAdapter(Context context, List<TpzsBean> tpzsBeanList) {
        this.context = context;
        this.tpzsBeanList = tpzsBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item_image_list, parent, false);
        return new TpzsViewHolder(rootView, tpzsBeanList);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TpzsViewHolder) {
            ((TpzsViewHolder) holder).onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return tpzsBeanList.size();
    }
}
