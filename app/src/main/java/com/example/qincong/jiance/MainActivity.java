package com.example.qincong.jiance;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        String line=BitmapCompare.similarity(getFilesDir()+"/bz_yang.jpg",getFilesDir()+"/20161211_020635.jpg").toString();
//        Toast.makeText(getApplicationContext(),line,Toast.LENGTH_SHORT).show();
        try{

            FileInputStream inputStream=getApplicationContext().openFileInput("img.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String str=bufferedReader.readLine();
            str=bufferedReader.readLine();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        final EditText editText = (EditText) findViewById(R.id.usr_name);
        final EditText edit_password = (EditText) findViewById(R.id.usr_password);
        Button login_btn=(Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrName = editText.getText().toString();
                String password = edit_password.getText().toString();
                if (true || usrName.equals("admin") && password.equals("admin")) {
                    Intent intent=new Intent(MainActivity.this,StartActivity.class);
                    startActivity(intent);
                }
                else  {
                    Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    editText.clearComposingText();
                    edit_password.clearComposingText();
                }
            }
        });
    }
}
