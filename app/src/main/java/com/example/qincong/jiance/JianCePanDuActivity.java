package com.example.qincong.jiance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
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

public class JianCePanDuActivity extends AppCompatActivity {
    private BlueToothService mBTService = null;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    Gson gson;
    public static Camera camera = Camera.open();
    private List<Info> infoList = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.jiancepandu);
        gson = new Gson();
        FileInputStream inputStream = null;
        infoList = new ArrayList<Info>();
        try {
            inputStream = getApplicationContext().openFileInput("json.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String json = bufferedReader.readLine();
            if (json != null) {
                infoList = gson.fromJson(json, new TypeToken<List<Info>>() {
                }.getType());
                if (infoList == null) {
                    infoList = new ArrayList<Info>();
                }
            } else {
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
                                        JianCePanDuActivity.this, "连接成功", Toast.LENGTH_SHORT).show();

                                break;
                            case BlueToothService.FAILED_CONNECT:
                                Toast.makeText(
                                        JianCePanDuActivity.this,
                                        "连接失败", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case BlueToothService.LOSE_CONNECT:
                                switch (msg.arg2) {
                                    case -1:
                                        Toast.makeText(
                                                JianCePanDuActivity.this, "str_lose", Toast.LENGTH_SHORT).show();
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
        if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
            mBTService.OpenDevice();
        }
        if (mBTService.getState() != mBTService.STATE_CONNECTED) {
            mBTService.ConnectToDevice("00:0D:18:00:1A:53");
            Toast.makeText(JianCePanDuActivity.this, "连接中", Toast.LENGTH_SHORT).show();
        }
        ((Button) findViewById(R.id.kaishijiance)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                parameters.setExposureCompensation(3);
                parameters.set("iso", "800");
                camera.setParameters(parameters);
                camera.startPreview();
                camera.takePicture(null, null, new TakePictureCallback());
            }
        });
        ((Button) findViewById(R.id.print)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBTService.getState() != mBTService.STATE_CONNECTED) {
                    mBTService.DisConnected();
                    mBTService.ConnectToDevice("00:0D:18:00:1A:53");
                    Toast.makeText(
                            JianCePanDuActivity.this, "未连接", Toast.LENGTH_SHORT).show();
                    return;
                }

                Info info = infoList.get(infoList.size() - 1);
                mBTService.PrintCharacters("公司名称：XXXX\r\n" + "用户名称：" + info.yonghumingcheng + "\n抽样单位：" + info.chouyangdanwei);
                mBTService.PrintCharacters("\n样品名称：" + info.yangpinmingcheng + "\n检测项目" + info.jiancexiangmu + "\n检测备注：" + info.jiancebeizhu + "\n检测结果：" + info.result+"\n\n\n");
            }
        });
    }

    private final class TakePictureCallback implements Camera.PictureCallback {
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //bitmap = Bitmap.createBitmap(bitmap, 200, 570, 300, 100);
            doBitmap(bitmap);
        }
    }

    private final class TakePictureCallbackYang implements Camera.PictureCallback {
        public void onPictureTaken(byte[] data, Camera cam1era) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            bitmap = Bitmap.createBitmap(bitmap, 300, 570, 100, 100);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            doBitmapYang(bitmap);
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
        }
    }

    private final class TakePictureCallbackYin implements Camera.PictureCallback {
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            bitmap = Bitmap.createBitmap(bitmap, 200, 570, 300, 100);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            doBitmapYin(bitmap);
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBTService.getState() == mBTService.STATE_CONNECTED) {
            mBTService.DisConnected();
            Toast.makeText(JianCePanDuActivity.this, "断开连接", Toast.LENGTH_SHORT).show();
        }
    }

    protected void doBitmapYang(Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        String name = null;
        try {
            name = "bz_yang.jpg";
            fileOutputStream = JianCePanDuActivity.this.openFileOutput(name, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception myexception) {
            myexception.printStackTrace();
        }
    }

    protected void doBitmapYin(Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        String name = null;
        try {
            name = "bz_yin.jpg";
            fileOutputStream = JianCePanDuActivity.this.openFileOutput(name, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception myexception) {
            myexception.printStackTrace();
        }
    }

    protected void doBitmap(Bitmap bitmap) {
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
        Bitmap bitmap_a = Bitmap.createBitmap(bitmap, 560, 370, 100, 50);
        Bitmap bitmap_b = Bitmap.createBitmap(bitmap, 560, 420, 100, 50);
        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap_a);// 将图片显示在ImageView里


        Info info = new Info();
        try {
            FileInputStream inputStream = getApplicationContext().openFileInput("yangbenxinxi.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            info.img = name;
            Double res = BitmapCompare.similarity(bitmap_a, bitmap_b);
            String line = res.toString();
            if (res < 80) {
                info.result = "阳性";
            } else {
                info.result = "阴性";
            }
            Toast.makeText(getApplicationContext(), info.result + "值:" + res, Toast.LENGTH_SHORT).show();
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
