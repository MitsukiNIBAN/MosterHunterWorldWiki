package com.satou.wiki.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;

import io.reactivex.Observable;


/**
 * Created by Mitsuki on 2018/3/29.
 */

public class RequestHelper {

    private static class RequestHelperHolder {
        private static final RequestHelper INSTANCE = new RequestHelper();
    }

    private RequestHelper() {
    }

    public static final RequestHelper getInstance() {
        return RequestHelperHolder.INSTANCE;
    }

    public Observable<Response<String>> get(String url) {
        return OkGo.<String>get(url)
                .converter(new StringConvert())
                .adapt(new ObservableResponse<>());
    }
}
