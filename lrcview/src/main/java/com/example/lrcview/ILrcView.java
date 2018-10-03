package com.example.lrcview;


import com.example.lrcview.view.LrcRow;

import java.util.List;

/**
 * 展示歌词的接口
 */
public interface ILrcView {
    ILrcView init();


    /**
     * 设置要展示的歌词行集合
     */
    ILrcView setLrc(List<LrcRow> lrcRows);

    /**
     * 音乐播放的时候调用该方法滚动歌词，高亮正在播放的那句歌词
     */
    ILrcView seekLrcToTime(long time);
    /**
     * 设置歌词拖动时候的监听类
     */
    ILrcView setListener(ILrcViewListener l);
}
