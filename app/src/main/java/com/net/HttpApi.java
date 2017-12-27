package com.net;

import com.net.bean.UserNonce;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/9.
 */

public interface HttpApi {
    @GET("howell/ver10/user_authentication/Authentication/Nonce")
    Observable<UserNonce> getUserNonce(@Query("UserName")String userName);


}
