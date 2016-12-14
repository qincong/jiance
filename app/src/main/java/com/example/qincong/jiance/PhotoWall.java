package com.example.qincong.jiance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

/**
 * Created by qincong on 2016/12/11.
 */
public class PhotoWall extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.photo_wall);
        GridView gridView=(GridView)findViewById(R.id.grid_view);
        gridView.setAdapter(new PhotoWallAdapter(getApplicationContext()));
    }
}
