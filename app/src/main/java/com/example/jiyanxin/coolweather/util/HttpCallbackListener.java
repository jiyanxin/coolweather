package com.example.jiyanxin.coolweather.util;

/**
 * Created by JIYANXIN on 2017/4/2.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
