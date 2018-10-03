package com.example.lrcview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.lrcview.ILrcView;
import com.example.lrcview.ILrcViewListener;
import com.example.lrcview.R;

import java.util.List;

public class LrcRecyclerView extends RecyclerView implements ILrcView {
    List<LrcRow> mList;
    LrcAdapter mAdapter;
    Context mContext;
    LinearLayoutManager mManager;
    private ILrcViewListener mLrcViewListener;
    float mCurProgress;



    public LrcRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public LrcRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public LrcRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    public LrcRecyclerView setLrc(List<LrcRow> lrcRows) {
        mList = lrcRows;
        if (mAdapter!=null)mAdapter.setData(lrcRows);
        return this;
    }

    public LrcRecyclerView seekLrcToTime(long time) {
        LrcRow cur = null;
        if (mAdapter!=null)cur = set2Time(time);
      //  if (mLrcViewListener!=null)mLrcViewListener.onLrcSeeked(0,cur);
        return this;
    }

    public LrcRecyclerView setListener(ILrcViewListener l) {
        mLrcViewListener = l;
        return this;
    }

    public LrcRecyclerView init(Context c, LrcAdapter adapter){
        mAdapter = adapter;
        setAdapter(mAdapter);
        mContext = c;
        mManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        setLayoutManager(mManager);
     //   setNestedScrollingEnabled(false);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return this;
    }

    @Override
    public LrcRecyclerView init(){
        if (mAdapter==null)
            mAdapter = new LrcAdapter(mContext);
        Log.i("123","new adapter ok");
        setAdapter(mAdapter);
        Log.i("123","set adapter ok");
        mManager = new LinearLayoutManager(mContext);
        setLayoutManager(mManager);
        Log.i("123","set adapter ok layout manager ok");
       // setNestedScrollingEnabled(false);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        Log.i("123","init ok");
        return this;
    }


    public LrcRow  set2Time(long time){
        Log.i("123","set 2 time  "+time);
        LrcRow cur = null,next=null;
        for (int i=0;i<mList.size();i++){

            cur = mList.get(i);
            next = i + 1 == mList.size() ? null : mList.get(i + 1);

            if ((time >= cur.time && next != null && time < next.time)
                    || (time > cur.time && next == null)){

                if (cur.isCur==false) {
                 //   Log.i("123","i:"+i+"   cur="+cur.time+"   next="+next.time);
                    cur.isCur = true;
                    mAdapter.notifyItemRangeChanged(i - 1, 2);
                    //scrollToPosition(i);
                    if (mManager!=null){
                        Log.i("123","manager scroll pos");
                        mManager.scrollToPositionWithOffset(i,50);
                    }
                }else{
                    //逐字变化

                    if (next!=null) {
                        mCurProgress = (time - cur.time) / (float)(next.time - cur.time);
                        Log.i("123","mCurProgress="+mCurProgress);
                        if (mCurProgress>0.05) {
                            mAdapter.notifyItemChanged(i);
                        }
                    }else{

                    }
                }
                return cur;
            }else{
                cur.isCur = false;
            }
        }
        return cur;
    }


    class LrcAdapter extends RecyclerView.Adapter{
        List<LrcRow> mList;
        Context mContext;

        LrcAdapter(Context c){
            mContext = c;
        }

        public void setData(List<LrcRow> l){
            mList = l;
            notifyDataSetChanged();
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lrc,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LrcRow row = mList.get(position);
            init((ViewHolder) holder,row);
        }
        private void init(ViewHolder h,LrcRow b){
            h.tv.setText(b.content);
            if (b.isCur){
//                h.tv.setTextColor(mContext.getResources().getColor(R.color.text_highlight));
                h.tv.setTextOriginColor(mContext.getResources().getColor(R.color.text_highlight));
                h.tv.setTextChangeColor(mContext.getResources().getColor(R.color.text_read));
                Log.e("123","init  set progress:"+mCurProgress);
                h.tv.setProgress(mCurProgress);
//                h.tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.highlight_text_size));

                h.tv.setTextSize(getResources().getDimensionPixelSize(R.dimen.highlight_text_size));
            }else{
//                h.tv.setTextColor(mContext.getResources().getColor(R.color.text_normal));
                h.tv.setTextOriginColor(mContext.getResources().getColor(R.color.text_normal));
                h.tv.setTextChangeColor(mContext.getResources().getColor(R.color.text_normal));
//                h.tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.normal_text_size));
                h.tv.setTextSize(getResources().getDimensionPixelSize(R.dimen.normal_text_size));
            }
        }


        @Override
        public int getItemCount() {
            return mList==null?0:mList.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            ColorText tv;
            public ViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.item_lrc);
            }
        }
    }


}
