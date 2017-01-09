package com.example.qincong.jiance;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qincong on 2016/12/11.
 */
public class XinXiChaXun extends AppCompatActivity {
    private List<Info> infoList;
    Gson gson;
    Context context;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.info_list);
        infoList = null;
        gson = new Gson();
        try {
            InputStream inputStream = context.openFileInput("json.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String json = bufferedReader.readLine();
            if (json != null) {
                infoList = gson.fromJson(json, new TypeToken<List<Info>>() {
                }.getType());
            } else {
                infoList = new ArrayList<Info>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        for (Info info : infoList) {
            TableRow tableRow = new TableRow(getApplicationContext());
            tableLayout.addView(tableRow);
        }
    }
}

class InfoAdapter extends BaseAdapter {
    private List<Info> infoList;
    Gson gson;
    Context context;

    public InfoAdapter(Context context) {
        this.context = context;
        infoList = null;
        gson = new Gson();
        try {
            InputStream inputStream = context.openFileInput("json.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String json = bufferedReader.readLine();
            if (json != null) {
                infoList = gson.fromJson(json, new TypeToken<List<Info>>() {
                }.getType());
            } else {
                infoList = new ArrayList<Info>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return infoList.size();
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
        View view = null;
        if (position != 0) {
            view = LayoutInflater.from(context).inflate(R.layout.info_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.info_text);
            Info info = infoList.get(position);
            String line = info.img + "  " + info.result + "  " + info.chouyangdanwei + "  " + info.yangpinmingcheng + "  " + info.jiancexiangmu
                    + "  " + info.jiancebeizhu + "  " + info.CTzhishezhi + "  " + info.jianceleixing + "  " + info.tijiaoleixing + "  " + info.yonghumingcheng
                    + "  " + info.yonghuxingbie + "  " + info.youxiangdizhi + " " + info.shouji;
            textView.setText(line);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.info_layout_head, null);
            TextView textView = (TextView) view.findViewById(R.id.info_text);
            Info info = infoList.get(position);
            String line = info.img + "  " + info.result + "  " + info.chouyangdanwei + "  " + info.yangpinmingcheng + "  " + info.jiancexiangmu
                    + "  " + info.jiancebeizhu + "  " + info.CTzhishezhi + "  " + info.jianceleixing + "  " + info.tijiaoleixing + "  " + info.yonghumingcheng
                    + "  " + info.yonghuxingbie + "  " + info.youxiangdizhi + " " + info.shouji;
            textView.setText(line);
            textView = (TextView) view.findViewById(R.id.info_head);
            line = info.img + "  " + info.result + "  " + info.chouyangdanwei + "  " + info.yangpinmingcheng + "  " + info.jiancexiangmu
                    + "  " + info.jiancebeizhu + "  " + info.CTzhishezhi + "  " + info.jianceleixing + "  " + info.tijiaoleixing + "  " + info.yonghumingcheng
                    + "  " + info.yonghuxingbie + "  " + info.youxiangdizhi + " " + info.shouji;
            textView.setText(line);
        }
        return view;
    }
}