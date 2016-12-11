package com.example.qincong.jiance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by qincong on 2016/12/2.
 */
public class JianCePanDuActivity extends AppCompatActivity {
    private BlueToothService mBTService = null;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.jiancepandu);
        mBTService = new BlueToothService(this, new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:// 蓝牙连接状态
                        switch (msg.arg1) {
                            case BlueToothService.STATE_CONNECTED:// 已经连接
                                break;
                            case BlueToothService.STATE_CONNECTING:// 正在连接
                                break;
                            case BlueToothService.STATE_LISTEN:
                            case BlueToothService.STATE_NONE:
                                break;
                            case BlueToothService.SUCCESS_CONNECT:
                                Toast.makeText(
                                        JianCePanDuActivity.this, "a", Toast.LENGTH_SHORT).show();
//                                vg.getChildAt(0).setVisibility(View.GONE);
//                                vg.getChildAt(1).setVisibility(View.GONE);
//                                vg.getChildAt(2).setVisibility(View.VISIBLE);
//                                vg.getChildAt(2).setFocusable(true);
//                                vg.getChildAt(2).setFocusableInTouchMode(true);

                                break;
                            case BlueToothService.FAILED_CONNECT:
                                Toast.makeText(
                                        JianCePanDuActivity.this,
                                        "str_faileconnect", Toast.LENGTH_SHORT)
                                        .show();
//                                vg.getChildAt(0).setVisibility(View.VISIBLE);
//                                vg.getChildAt(1).setVisibility(View.VISIBLE);
//                                vg.getChildAt(2).setVisibility(View.GONE);
//                                vg.getChildAt(2).setFocusable(false);
                                break;
                            case BlueToothService.LOSE_CONNECT:
                                switch (msg.arg2) {
                                    case -1:
                                        Toast.makeText(
                                                JianCePanDuActivity.this, "str_lose", Toast.LENGTH_SHORT).show();
//                                        vg.getChildAt(0).setVisibility(View.VISIBLE);
//                                        vg.getChildAt(1).setVisibility(View.VISIBLE);
//                                        vg.getChildAt(2).setVisibility(View.GONE);
//                                        vg.getChildAt(2).setFocusable(false);
                                        break;
                                    case 0:
                                        break;
                                }
                        }
                        break;
                    case MESSAGE_READ:
                        // sendFlag = false;//缓冲区已满
                        break;
                    case MESSAGE_WRITE:// 缓冲区未满
                        // sendFlag = true;
                        break;

                }
            }
        });
        mBTService.ConnectToDevice("00:0D:18:00:1A:53");

        ((Button) findViewById(R.id.kaishijiance)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
        ((Button) findViewById(R.id.print)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBTService.getState() != mBTService.STATE_CONNECTED) {
                    Toast.makeText(
                            JianCePanDuActivity.this, "未连接", Toast.LENGTH_SHORT).show();
                    return;
                }

                mBTService.PrintCharacters("公司名称：XXXX\r\n及访问呢" + "\r\n");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream fileOutputStream = null;
            try {
                String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";
                fileOutputStream = JianCePanDuActivity.this.openFileOutput(name,MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.close();

                File file=new File(getApplicationContext().getFilesDir().getPath()+"/img.txt");
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream file_img= getApplicationContext().openFileOutput("img.txt", MODE_PRIVATE);
                file_img.write(name.getBytes("utf-8"));
                file_img.close();
            } catch (Exception myexception) {
                myexception.printStackTrace();
            }

            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);// 将图片显示在ImageView里
        }
    }
}
