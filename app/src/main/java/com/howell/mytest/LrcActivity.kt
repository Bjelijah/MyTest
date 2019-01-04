package com.howell.mytest

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.lrcview.ILrcView
import com.example.lrcview.ILrcViewListener
import com.example.lrcview.view.DefaultLrcBuilder
import com.example.lrcview.view.LrcRecyclerView
import com.example.lrcview.view.LrcRow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import java.lang.reflect.Array.getLength
import android.content.res.AssetFileDescriptor



class LrcActivity :AppCompatActivity(){
    @BindView(R.id.rv) lateinit var mLrcView: LrcRecyclerView






    private val mPalyTimerDuration = 100
    var mLrc :String ?=null
    var mPlayer:MediaPlayer?=null
    private var mTimer: Timer? = null
    private var mTask: TimerTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lrc)
        ButterKnife.bind(this)
        Log.i("123","bind view ok")
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLrcPlay()
    }

    fun initData(){
        val lrc = getFromAssets("test.lrc")
        Log.i("123","get lrc ok")
        val builder = DefaultLrcBuilder()
        val rows = builder.getLrcRows(lrc)
        Log.i("123","get lrc row ok")
        mLrcView.init()
                .setListener { newPosition, row -> //手动滑到这里的
                    //TODO media player 设置位置
                    mPlayer?.seekTo(row.time.toInt())
                }
                .setLrc(rows)
       // beginLrcPlay()


    }

    /**
     * 从assets目录下读取歌词文件内容
     * @param fileName
     * @return
     */
    fun getFromAssets(fileName: String): String {
        Log.i("123","start from assets file=$fileName")
        try {
            val inputReader = InputStreamReader(resources.assets.open(fileName))
            val bufReader = BufferedReader(inputReader)
            var line = ""
            var result = ""

            BufferedReader(inputReader).use {r->
                r.lineSequence().forEach {
                    if (!it.equals("")) {
                        result += it + "\r\n"
                    }

                }
            }
            Log.i("123","result=$result")
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.i("123","get from assets ok")
        return ""
    }



    @OnClick(R.id.btn_start) fun onClickStart(){
        beginLrcPlay()
    }

    @OnClick(R.id.btn_stop) fun onClickStop(){
        stopLrcPlay()
    }

    @OnClick(R.id.btn_restart) fun onClickrestart(){

    }


    fun beginLrcPlay() {
        mPlayer = MediaPlayer()
        try {

            val descriptor = this.assets.openFd("test.mp3")
            mPlayer?.setDataSource(descriptor.fileDescriptor, descriptor.startOffset,
                    descriptor.length)

            descriptor.close()


            mPlayer?.setOnCompletionListener { mp ->
                mp.release()
            }
         //   mPlayer?.setDataSource(assets.openFd("test.mp3").fileDescriptor)
            //准备播放歌曲监听
            mPlayer?.setOnPreparedListener { mp ->
                //准备完毕
                mp.start()
                if (mTimer == null) {
                    mTimer = Timer()
                    mTask = LrcTask()
                    mTimer?.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration.toLong())
                }
            }
            mPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            //歌曲播放完毕监听
            mPlayer?.setOnCompletionListener { stopLrcPlay() }
            //准备播放歌曲
            mPlayer?.prepare()
            //开始播放歌曲
            mPlayer?.start()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * 停止展示歌曲
     */
    fun stopLrcPlay() {
        mTimer?.cancel()
        mTimer = null

        mPlayer?.stop()
    }


    internal inner class LrcTask : TimerTask() {
        override fun run() {
            //获取歌曲播放的位置
            val timePassed = mPlayer?.currentPosition?.toLong()
            this@LrcActivity.runOnUiThread {
                //滚动歌词
                mLrcView.seekLrcToTime(timePassed?:0)
            }

        }
    }
}