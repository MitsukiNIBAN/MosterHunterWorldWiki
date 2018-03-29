package com.satou.wiki.http;

import com.satou.wiki.constant.ResponseCode;

import java.util.IllegalFormatCodePointException;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public class RequestHelper {
    private OkHttpClient client;

    private static class RequestHelperHolder {
        private static final RequestHelper INSTANCE = new RequestHelper();
    }

    private RequestHelper() {
        client = new OkHttpClient();
    }

    public static final RequestHelper getInstance() {
        return RequestHelperHolder.INSTANCE;
    }

    public Observable<String> get(String url) {
        Observable<String> observable = Observable.create(emitter -> {
            Request request = new Request.Builder()
                    .url(url)//请求接口。如果需要传参拼接到接口后面。
                    .build();//创建Request 对象
            Response response = null;
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                if (response.code() == ResponseCode.SUCCESS.getCode()) {
                    emitter.onNext(response.body().string());
                } else {

                }
            }
        });

        return observable;
    }
}
