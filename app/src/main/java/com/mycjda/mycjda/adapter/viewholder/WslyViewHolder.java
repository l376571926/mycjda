package com.mycjda.mycjda.adapter.viewholder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.mycjda.mycjda.R;
import com.mycjda.mycjda.bean.WslyBean;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by liyiwei
 * on 2017/4/16.
 */

public class WslyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private List<WslyBean> wslyBeanList;
    private TextView titleTv;
    private int position;

    public WslyViewHolder(View itemView, List<WslyBean> wslyBeanList) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.wslyBeanList = wslyBeanList;
        titleTv = (TextView) itemView.findViewById(R.id.title);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.position = position;
        WslyBean wslyBean = wslyBeanList.get(position);
        String topic = wslyBean.getTopic();
        String question = wslyBean.getQuestion();
        String answer = wslyBean.getAnswer();

        titleTv.setText("");

        SpannableString str = new SpannableString(topic);
        str.setSpan(new ForegroundColorSpan(Color.WHITE), 0, topic.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleTv.append(str);
        titleTv.append("\n");

        SpannableString st = new SpannableString(question);
        st.setSpan(new ForegroundColorSpan(Color.BLUE), 0, question.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleTv.append(st);
        titleTv.append("\n");

        SpannableString string1 = new SpannableString(answer);
        string1.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, answer.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleTv.append(string1);
    }

    @Override
    public void onClick(View v) {
        KLog.e(wslyBeanList.get(position).toString());
    }
}
