package com.example.qincong.jiance;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qincong on 2016/12/11.
 */
public class PhotoWallAdapter extends BaseAdapter {
    List<String> stringList=new ArrayList<String>();
    Context context;
    public PhotoWallAdapter(Context context) {
        try {
            this.context=context;
            FileInputStream fileInputStream=context.openFileInput("img.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"utf-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            while((line=bufferedReader.readLine())!=null) {
                stringList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout=(LinearLayout) LayoutInflater.from(context).inflate(R.layout.photo_layout,null);
        ImageView imageView=(ImageView)linearLayout.findViewById(R.id.photo);
        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(context.openFileInput(stringList.get(position))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TextView textView=(TextView)linearLayout.findViewById(R.id.img_title);
        textView.setText(stringList.get(position));
        return linearLayout;
    }
}
