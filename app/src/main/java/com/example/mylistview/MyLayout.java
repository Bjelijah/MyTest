package com.example.mylistview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MyLayout extends LinearLayout {

    private LayoutInflater mInflater;

    public MyLayout(Context context) {
        this(context,null);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mInflater = LayoutInflater.from(context);
    }
}
