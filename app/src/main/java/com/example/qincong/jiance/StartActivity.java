package com.example.qincong.jiance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by qincong on 2016/12/2.
 */
public class StartActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.item3).findViewById(R.id.txt)).setText("样本信息");
        ((TextView) findViewById(R.id.item2).findViewById(R.id.txt)).setText("信息查询");
        ((TextView) findViewById(R.id.item4).findViewById(R.id.txt)).setText("图片历史");
        ((TextView) findViewById(R.id.item5).findViewById(R.id.txt)).setText("用户信息");
        ((TextView) findViewById(R.id.item6).findViewById(R.id.txt)).setText("关于我们");
        findViewById(R.id.item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, JianCePanDuActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, XinXiChaXun.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,YangBenXinXiActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.item4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, PhotoWall.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.item5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, UsrInformation.class);
                startActivity(intent);
            }
        });
    }
}
