package com.wlw.admin.network.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonHttpListener<M> implements IHttpListener {
    Class<M> responseClass;
    IDataListener<M> iDataListener;
    Handler handler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<M> responseClass, IDataListener<M> iDataListener) {
        this.responseClass = responseClass;
        this.iDataListener = iDataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content = getContent(inputStream);
        final M response = JSON.parseObject(content, responseClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (iDataListener != null)
                    iDataListener.onSuccess(response);
            }
        });
    }


    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (iDataListener != null)
                    iDataListener.onFailure();
            }
        });
    }

    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

}
