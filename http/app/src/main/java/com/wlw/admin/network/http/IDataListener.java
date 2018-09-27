package com.wlw.admin.network.http;

public interface IDataListener<M> {
    void onSuccess(M m);

    void onFailure();
}
