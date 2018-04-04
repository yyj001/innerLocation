package com.ish.mapmoving;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by ish on 2017/10/7.
 */

public class MyFrameLayout extends FrameLayout{
    Scroller mScroller;

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(mScroller == null){
            mScroller = new Scroller(context);
        }
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 开始滑动
     */
    public void startMove(int x , int y){
        mScroller.startScroll(mScroller.getFinalX(),mScroller.getFinalY(),x,y,300);
        invalidate();
    }
}
