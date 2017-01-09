package com.example.qincong.jiance;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.info_list);
        infoList = null;
        gson = new Gson();
        try {
            InputStream inputStream = getApplicationContext().openFileInput("json.txt");
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

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, r.getDisplayMetrics());
        for (Info info : infoList) {
            final TableRow tableRow = new TableRow(getApplicationContext());

            TextView textView1=new TextView(getApplication());
            textView1.setText(info.chouyangdanwei);
            textView1.setTextSize(20);
            textView1.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
            tableRow.addView(textView1);
            textView1=new TextView(getApplication());
            textView1.setText(info.yangpinmingcheng);
            textView1.setTextSize(20);
            textView1.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
            tableRow.addView(textView1);
            textView1=new TextView(getApplication());
            textView1.setText(info.jiancexiangmu);
            textView1.setTextSize(20);
            textView1.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
            tableRow.addView(textView1);
            textView1=new TextView(getApplication());
            textView1.setText(info.result);
            textView1.setTextSize(20);
            textView1.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
            tableRow.addView(textView1);
            textView1=new TextView(getApplication());
            textView1.setText(info.yonghumingcheng);
            textView1.setTextSize(20);
            textView1.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics()));
            tableRow.addView(textView1);
            Button bt_delete=new Button(this);
            bt_delete.setText("删除");
            bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow tr=(TableRow)v.getParent();
                    int index=((ViewGroup)(tr.getParent())).indexOfChild(tr);
                    String filePath=getApplicationContext().getFilesDir().getPath()+"/"+infoList.get(index-1).img;
                    File file = new File(filePath);
                    if(file.exists()) {
                        deleteFile(infoList.get(index-1).img);
                        Log.w("删除图片","已删除");
                    }
                    infoList.remove(index-1);
                    String json = gson.toJson(infoList);
                    FileOutputStream file_img = null;
                    try {
                        file_img = getApplicationContext().openFileOutput("json.txt", MODE_PRIVATE);
                        file_img.write(json.getBytes("utf-8"));
                        file_img.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    tableLayout.removeViewAt(index);
                }
            });
            tableRow.addView(bt_delete);

            tableLayout.addView(tableRow);
            Log.w("qc", info.chouyangdanwei );
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