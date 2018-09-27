package com.wlw.admin.network.http;

import java.io.InputStream;

public interface IHttpListener {
    void onSuccess(InputStream inputStream);

    void onFailure();
}
