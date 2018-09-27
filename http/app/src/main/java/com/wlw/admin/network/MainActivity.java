package com.wlw.admin.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wlw.admin.network.http.HttpHelper;
import com.wlw.admin.network.http.IDataListener;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_text);
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    private void requestData() {
        String url = "http://www.hongjimeng.net/AppV2/public/IndexReturn1212";
        HttpHelper.sendJsonRequest("", url, HomeBean.class, new IDataListener<HomeBean>() {
            @Override
            public void onSuccess(HomeBean homeBean) {
                textView.setText(JSON.toJSONString(homeBean));
                Toast.makeText(MainActivity.this, homeBean.getJsondata().getItemList().get(0).getItemName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

            }
        });
        Runnable r = () -> {
        };
        OnListener l = () -> Log.e("执行了", "---------");
        l.OnClick();
    }
}
