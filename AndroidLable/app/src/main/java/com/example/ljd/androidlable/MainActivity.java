package com.example.ljd.androidlable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ljd.androidlable.adapter.TabBaseAdapter;
import com.example.ljd.androidlable.widget.TabGroupLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> datas = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabGroupLayout tab = (TabGroupLayout) findViewById(R.id.tab);
        datas.add("成龙");
        datas.add("苍老师");
        datas.add("燕麦");
        datas.add("糖");
        datas.add("杯子");
        datas.add("开个们吧");
        datas.add("看电视");
        datas.add("睡觉啊");
        datas.add("不要");
        datas.add("下班");

        TabBaseAdapter adapter = new TabBaseAdapter(this,datas) ;
        tab.setAdapter(adapter);

        tab.setItemClickListener(new TabGroupLayout.TabOnItemClickListener() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(MainActivity.this, "点击了第"+position+"个", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
