package com.wlw.admin.network.http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class HttpTask<T> implements Runnable {
    private IHttpRequest httpRequest;
    public HttpTask(T requestInfo, String url, IHttpRequest httpRequest, IHttpListener httpListener) {
        this.httpRequest = httpRequest;

        this.httpRequest.setUrl(url);
        this.httpRequest.setHttpCallBack(httpListener);
        if (requestInfo != null) {
            String requestContent = JSON.toJSONString(requestInfo);
            try {
                httpRequest.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        httpRequest.execute();
    }
}
