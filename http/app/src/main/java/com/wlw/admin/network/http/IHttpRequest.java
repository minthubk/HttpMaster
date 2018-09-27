package com.wlw.admin.network.http;

public interface IHttpRequest {
    void setUrl(String url);

    void setRequestData(byte[] requestData);

    void execute();

    void setHttpCallBack(IHttpListener httpListener);
}
