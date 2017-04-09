package com.mycjda.mycjda.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mycjda.mycjda.R;
import com.mycjda.mycjda.bean.TpzsBean;

import java.util.List;

/**
 * Created by liyiwei on 2017/4/9.
 */

public class TpzsViewHolder extends RecyclerView.ViewHolder {
    private List<TpzsBean> tpzsBeanList;
    private Context context;
    private final ImageView imageView;
    private final TextView titleTv;
    private final TextView authorTv;
    private final TextView goodTv;

    public TpzsViewHolder(View itemView, List<TpzsBean> tpzsBeanList) {
        super(itemView);
        context = itemView.getContext();
        this.tpzsBeanList = tpzsBeanList;
        imageView = (ImageView) itemView.findViewById(R.id.image);
        titleTv = (TextView) itemView.findViewById(R.id.title);
        goodTv = (TextView) itemView.findViewById(R.id.good);
        authorTv = (TextView) itemView.findViewById(R.id.author);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TpzsBean tpzsBean = tpzsBeanList.get(position);
        String image = tpzsBean.getImage();
        String title = tpzsBean.getTitle();
        String good = tpzsBean.getGood();
        String author = tpzsBean.getAuthor();

        Glide.with(context)
                .load(image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        titleTv.setText(title);
        goodTv.setText(good);
        authorTv.setText(author);
    }
}
