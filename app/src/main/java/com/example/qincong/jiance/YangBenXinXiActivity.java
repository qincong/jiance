package com.example.qincong.jiance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by qincong on 2016/12/2.
 */
public class YangBenXinXiActivity extends AppCompatActivity {
    Button queding;
    MyEditText chouyangdanwzi;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.yangbenxinxi);
        initView();
        queding=(Button)findViewById(R.id.bt_queding);
        chouyangdanwzi=(MyEditText)findViewById(R.id.chouyangdanwei);

        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.w("mylog","保存");
                    StringBuffer sb=new StringBuffer();
                    sb.append(((MyEditText)findViewById(R.id.chouyangdanwei)).getText()+"\n");
                    sb.append(((MyEditText)findViewById(R.id.yangpinmingcheng)).getText()+"\n");
                    sb.append(((MyEditText)findViewById(R.id.jiancexiangmu)).getText()+"\n");
                    sb.append(((MyEditText)findViewById(R.id.jiancebeizhu)).getText()+"\n");
                    if(((RadioButton)findViewById(R.id.ctzhishezhi1)).isChecked()) {
                        sb.append("one"+"\n");
                    }
                    else {
                        sb.append("two"+"\n");
                    }
                    RadioGroup jianceleixing=(RadioGroup)findViewById(R.id.jianceleixing);
                    if(((RadioButton)jianceleixing.getChildAt(0)).isChecked()) {
                        sb.append("one"+"\n");
                    }
                    if(((RadioButton)jianceleixing.getChildAt(1)).isChecked()) {
                        sb.append("two"+"\n");
                    }
                    if(((RadioButton)jianceleixing.getChildAt(2)).isChecked()) {
                        sb.append("three"+"\n");
                    }
                    if(((RadioButton)jianceleixing.getChildAt(3)).isChecked()) {
                        sb.append("four"+"\n");
                    }
                    RadioGroup tijiaoleixing=(RadioGroup)findViewById(R.id.tijiaoleixing);
                    if(((RadioButton)tijiaoleixing.getChildAt(0)).isChecked()) {
                        sb.append("one"+"\n");
                    }
                    if(((RadioButton)tijiaoleixing.getChildAt(1)).isChecked()) {
                        sb.append("two"+"\n");
                    }
                    FileOutputStream out=openFileOutput("yangbenxinxi.txt",MODE_PRIVATE);
                    out.write((new String(sb)).getBytes("utf-8"));
                    out.close();
                    Log.w("mylog","保存wnbi");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
    void initView() {
        File file=new File(getFilesDir()+"/yangbenxinxi.txt");
        if(!file.exists()) {
            return;
        }
        try {
            FileInputStream in=openFileInput("yangbenxinxi.txt");
            InputStreamReader reader=new InputStreamReader(in,"utf-8");
            BufferedReader bufferedReader=new BufferedReader(reader);
            ((MyEditText)findViewById(R.id.chouyangdanwei)).setText(bufferedReader.readLine());
            ((MyEditText)findViewById(R.id.yangpinmingcheng)).setText(bufferedReader.readLine());
            ((MyEditText)findViewById(R.id.jiancexiangmu)).setText(bufferedReader.readLine());
            ((MyEditText)findViewById(R.id.jiancebeizhu)).setText(bufferedReader.readLine());
            String line=bufferedReader.readLine();
            if(line.equals("one")) {
                ((RadioButton)findViewById(R.id.ctzhishezhi1)).setChecked(true);
            }
            else {
                ((RadioButton)findViewById(R.id.ctzhishezhi2)).setChecked(true);
            }
            line=bufferedReader.readLine();
            if(line.equals("one")) {
                ((RadioButton)((RadioGroup)findViewById(R.id.jianceleixing)).getChildAt(0)).setChecked(true);
            }
            if(line.equals("two")) {
                ((RadioButton)((RadioGroup)findViewById(R.id.jianceleixing)).getChildAt(1)).setChecked(true);
            }
            if(line.equals("three")) {
                ((RadioButton)((RadioGroup)findViewById(R.id.jianceleixing)).getChildAt(2)).setChecked(true);
            }
            if(line.equals("four")) {
                ((RadioButton)((RadioGroup)findViewById(R.id.jianceleixing)).getChildAt(3)).setChecked(true);
            }
            line=bufferedReader.readLine();
            if(line.equals("one")) {
                ((RadioButton)((RadioGroup)findViewById(R.id.tijiaoleixing)).getChildAt(0)).setChecked(true);
            }
            else {
                ((RadioButton)((RadioGroup)findViewById(R.id.tijiaoleixing)).getChildAt(1)).setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
