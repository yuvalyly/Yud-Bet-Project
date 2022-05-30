package com.example.yuval;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity implements View.OnClickListener {

    private String TAG = InfoActivity.class.getSimpleName();
    TextView tv_description;
    Button btn_info_activity;
    Button btn_physical;
    LinearLayout ll_buttons_categories;
    LinearLayout ll_buttons_physical;
    Button btn_magic;
    LinearLayout ll_magic;
    Button btn_items;
    LinearLayout ll_items;
    Button btn_log;
    Button btn_att_heavy;
    Button btn_att_quick;
    Button btn_att_deception;
    Button btn_brace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tv_description = (TextView)findViewById(R.id.tv_description);
        ll_buttons_physical= (LinearLayout)findViewById(R.id.ll_buttons_physical);
        ll_buttons_categories= (LinearLayout)findViewById(R.id.ll_buttons_categories);
        ll_magic = (LinearLayout)findViewById(R.id.ll_magic);
        ll_items = (LinearLayout)findViewById(R.id.ll_items);
        btn_physical = (Button)findViewById(R.id.btn_physical);
        btn_items = (Button)findViewById(R.id.btn_items);
        btn_magic = (Button)findViewById(R.id.btn_magic);
        btn_log = (Button)findViewById(R.id.btn_log);
        btn_info_activity=(Button)findViewById(R.id.btn_info_activity);
        btn_info_activity.setOnClickListener(this);
        btn_att_heavy = (Button)findViewById(R.id.btn_att_heavy);
        btn_att_quick = (Button)findViewById(R.id.btn_att_quick);
        btn_att_deception = (Button)findViewById(R.id.btn_att_deception);
        btn_brace = (Button)findViewById(R.id.btn_brace);
        btn_physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_buttons_categories.setVisibility(View.GONE);
                ll_buttons_physical.setVisibility(View.VISIBLE);

            }
        });
        btn_magic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_buttons_categories.setVisibility(View.GONE);
                ll_magic.setVisibility(View.VISIBLE);
            }
        });
        btn_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_buttons_categories.setVisibility(View.GONE);
                ll_items.setVisibility(View.VISIBLE);
            }
        });
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_description.setText("Shows the history of the current battle. Useful for figuring out the enemy's attack pattern.");
            }
        });
        btn_att_heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tv_description.setText(new MoveHeavyAtt().getDescription());
            }
        });
        btn_att_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tv_description.setText(new MoveQuickAtt().getDescription());
            }
        });
        btn_att_deception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tv_description.setText(new MoveAttDeception().getDescription());
            }
        });
        btn_brace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View view)
            {
                tv_description.setText(new MoveBrace().getDescription());
            }
        });
        setWindowParams();
    }

    public void setWindowParams() {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.dimAmount = 0;
        wlp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        getWindow().setAttributes(wlp);
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        ll_items.setVisibility(View.GONE);
        ll_magic.setVisibility(View.GONE);
        ll_buttons_physical.setVisibility(View.GONE);
        ll_buttons_categories.setVisibility(View.VISIBLE);
    }
}