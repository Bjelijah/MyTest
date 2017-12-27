package com.net;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.utils.Config;
import com.net.http.CustomInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/9.
 */

public class HttpManager {
    private static HttpManager mInstance;
    private static OkHttpClient mClient;
    public static HttpManager getInstance(){
        if (mInstance==null){
            synchronized (HttpManager.class){
                if (mInstance==null){
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    public HttpManager initClient(Context context,boolean ssl){
        if (mClient==null){
            mClient = ssl?
                    SSLConection.getOKHttpsClientBuild(context)
                    .addInterceptor(new CustomInterceptor())
                    .connectTimeout(8,TimeUnit.SECONDS)
                    .readTimeout(8,TimeUnit.SECONDS)
                    .build():
                    new OkHttpClient.Builder()
                            .addInterceptor(new CustomInterceptor())
                            .connectTimeout(8, TimeUnit.SECONDS)
                            .readTimeout(8, TimeUnit.SECONDS)
                            .build();
        }
        return this;
    }


    private HttpManager(){}

    private HttpApi mHttpApi;

    public HttpApi getHttpService(){
        if (mHttpApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mClient)
                    .baseUrl(Config.IS_SSL?Config.SSL_URL:Config.URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mHttpApi = retrofit.create(HttpApi.class);
        }
        return mHttpApi;
    }









}
