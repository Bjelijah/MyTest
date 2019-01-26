//
// Created by itufo on 2019/1/26.
//
#include <jni.h>
#include "include/xyz_mercs_soundsend_JniUtil.h"
#include <android/log.h>


#define LOG_TAG "jni"
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, fmt, ##args)

extern "C"
JNIEXPORT void JNICALL Java_xyz_mercs_soundsend_JniUtil_init
(JNIEnv *env, jclass clz, jstring url, jint port, jboolean isSSL){
    const char * url_ = env->GetStringUTFChars(url,0);

    LOGI("new url=%s  port=%d  isssl=%d",url_,port,isSSL);
    env->ReleaseStringUTFChars(url,url_);
}

extern "C"
JNIEXPORT void JNICALL Java_xyz_mercs_soundsend_JniUtil_deinit
        (JNIEnv *, jclass){

}