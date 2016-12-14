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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    Gson gson;
    private List<Info> infoList = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.jiancepandu);
        gson = new Gson();
        FileInputStream inputStream = null;
        infoList=new ArrayList<Info>();
        try {
            inputStream = getApplicationContext().openFileInput("json.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String json = bufferedReader.readLine();
            if (json != null) {
                infoList = gson.fromJson(json, new TypeToken<List<Info>>() {
                }.getType());
            }
            else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            String name = null;
            try {
                name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                fileOutputStream = JianCePanDuActivity.this.openFileOutput(name, MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();

                File file = new File(getApplicationContext().getFilesDir().getPath() + "/img.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream file_img = getApplicationContext().openFileOutput("img.txt", MODE_APPEND);
                file_img.write((name + "\n").getBytes("utf-8"));
                file_img.close();
            } catch (Exception myexception) {
                myexception.printStackTrace();
            }
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);// 将图片显示在ImageView里
            Info info = new Info();
            try {
                FileInputStream inputStream = getApplicationContext().openFileInput("yangbenxinxi.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str;
                info.img = name;
                info.result = "yang";
                info.chouyangdanwei = bufferedReader.readLine();
                info.yangpinmingcheng = bufferedReader.readLine();
                info.jiancexiangmu = bufferedReader.readLine();
                info.jiancebeizhu = bufferedReader.readLine();
                info.CTzhishezhi = bufferedReader.readLine();
                info.jianceleixing = bufferedReader.readLine();
                info.tijiaoleixing = bufferedReader.readLine();
                inputStream = getApplicationContext().openFileInput("usr_info.txt");
                inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                info.yonghumingcheng = bufferedReader.readLine();
                info.yonghuxingbie = bufferedReader.readLine();
                info.youxiangdizhi = bufferedReader.readLine();
                info.shouji = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            infoList.add(info);
            String json = gson.toJson(infoList);
            FileOutputStream file_img = null;
            try {
                file_img = getApplicationContext().openFileOutput("json.txt", MODE_PRIVATE);
                file_img.write(json.getBytes("utf-8"));
                file_img.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
