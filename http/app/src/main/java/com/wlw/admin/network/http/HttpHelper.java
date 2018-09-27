package com.wlw.admin.network.http;

public class HttpHelper {
    public static <T, M> void sendJsonRequest(T requestInfo, String url, Class<M> response, IDataListener<M> dataListener) {
        IHttpListener httpListener = new JsonHttpListener<>(response, dataListener);
        IHttpRequest httpRequest = new JsonHttpRequest();
        HttpTask<T> httpTask = new HttpTask<>(requestInfo, url, httpRequest, httpListener);
        ThreadPoolManager.getOurInstance().execute(httpTask);
    }
}
