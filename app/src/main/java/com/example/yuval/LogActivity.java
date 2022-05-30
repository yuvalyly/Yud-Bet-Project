package com.example.yuval;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {
    ListView lv_log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        lv_log = (ListView)findViewById(R.id.lv_log);
        LogListAdapter log_adapter = new LogListAdapter(LogActivity.this, 0, 0, GameActivity.logMoves);
        lv_log.setAdapter(log_adapter);

    }
}