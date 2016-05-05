package com.blq.qrcode.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blq.qrcode.R;
import com.blq.qrcode.util.SMSUtil;

/**
 * 获取手机短信信息
 */
public class SmsActivity extends BaseActivity {
    private Button btn;
    private TextView list;
    private SMSUtil smsUtil;
    private String info;
    @Override
    protected int contentView() {
        return R.layout.activity_sms;
    }

    @Override
    protected void initDate() {
        smsUtil = new SMSUtil(this, SMSUtil.SMS_URI_ALL);
    }

    @Override
    protected void initView() {
        btn = (Button)findViewById(R.id.btn);
        list = (TextView)findViewById(R.id.list);
    }

    @Override
    protected void bindEvent() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info = smsUtil.getSmsInfo().toString();
                list.setText(info);
            }
        });
    }

    @Override
    protected void execute() {

    }

}
