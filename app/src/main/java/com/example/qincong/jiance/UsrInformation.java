package com.example.qincong.jiance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class UsrInformation extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.user_information);
        initView();
        ((Button)findViewById(R.id.bt_push)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer sb=new StringBuffer();
                sb.append(((MyEditText)findViewById(R.id.usr_name)).getText()+"\n");
                if(((RadioButton)(((RadioGroup)findViewById(R.id.usr_sex)).getChildAt(1))).isChecked()) {
                    sb.append("one\n");
                }
                else {
                    sb.append("two\n");
                }
                sb.append(((MyEditText)findViewById(R.id.email)).getText()+"\n");
                sb.append(((MyEditText)findViewById(R.id.phone)).getText()+"\n");
                try {
                    FileOutputStream out=openFileOutput("usr_info.txt",MODE_PRIVATE);
                    out.write((new String(sb)).getBytes("utf-8"));
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void initView()  {
        File file=new File(getFilesDir()+"/usr_info.txt");
        if(!file.exists()) {
            return;
        }
        try {
            FileInputStream in=openFileInput("usr_info.txt");
            InputStreamReader reader=new InputStreamReader(in,"utf-8");
            BufferedReader bufferedReader=new BufferedReader(reader);
            ((MyEditText)findViewById(R.id.usr_name)).setText(bufferedReader.readLine());
            if(bufferedReader.readLine().equals("one")) {
                ((RadioButton)(((RadioGroup)findViewById(R.id.usr_sex)).getChildAt(1))).setChecked(true);
            }
            else {
                ((RadioButton)(((RadioGroup)findViewById(R.id.usr_sex)).getChildAt(2))).setChecked(true);
            }
            ((MyEditText)findViewById(R.id.email)).setText(bufferedReader.readLine());
            ((MyEditText)findViewById(R.id.phone)).setText(bufferedReader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
