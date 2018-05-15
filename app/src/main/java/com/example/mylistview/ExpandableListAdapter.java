package com.example.mylistview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.howell.mytest.R;

import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER = 0;
    public static final int CHILD1 = 1;
    public static final int CHILD2 = 2;

    private List<Item> data;

    ExpandableListAdapter(List<Item> data){
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        Context context = parent.getContext();
        switch (viewType){
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_header,parent,false);


        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ListHeaderViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv;
        public TextView title;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.header_btn_iv);
            title = itemView.findViewById(R.id.header_title);
        }
    }

    public static class Item{
        public int type;
        public String text;
        public List<Item> subChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }

}
