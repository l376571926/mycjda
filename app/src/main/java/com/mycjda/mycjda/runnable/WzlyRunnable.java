package com.mycjda.mycjda.runnable;

import com.mycjda.mycjda.OnParserFinishListener;

/**
 * Created by liyiwei on 2017/4/9.
 */

public class WzlyRunnable implements Runnable {
    private int id;
    private OnParserFinishListener onParserFinishListener;

    public WzlyRunnable(int id, OnParserFinishListener onParserFinishListener) {
        this.id = id;
        this.onParserFinishListener = onParserFinishListener;
    }

    @Override
    public void run() {

    }
}
