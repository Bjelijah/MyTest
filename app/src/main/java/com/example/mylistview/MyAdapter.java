package com.example.mylistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.howell.mytest.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    List<String> mList;
    Context mContext;
    MyAdapter(Context context, List<String> l){
        mList = l;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LinearLayout.inflate(mContext, R.layout.item,null);

        return v;
    }
}
