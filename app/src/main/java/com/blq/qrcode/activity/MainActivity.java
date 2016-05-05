package com.blq.qrcode.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.blq.qrcode.R;

public class MainActivity extends BaseActivity {

    private Button smsActivityBtn;
    private Button contactsActivityBtn;
    @Override
    protected int contentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {
        smsActivityBtn = (Button)findViewById(R.id.sms_btn);
        contactsActivityBtn = (Button)findViewById(R.id.contacts_btn);
    }

    @Override
    protected void bindEvent() {
        smsActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
        contactsActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CreateQRCodeActivity.class));
            }
        });
    }

    @Override
    protected void execute() {

    }
}
