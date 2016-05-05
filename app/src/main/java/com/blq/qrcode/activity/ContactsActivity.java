package com.blq.qrcode.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blq.qrcode.R;
import com.blq.qrcode.util.ContactsUtil;

/**
 * 获取联系人信息
 */
public class ContactsActivity extends BaseActivity {

    private Button btn;
    private Button btn2;
    private TextView list;
    private ContactsUtil contactsUtil;
    @Override
    protected int contentView() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initDate() {
        contactsUtil = new ContactsUtil(this);
    }

    @Override
    protected void initView() {
        btn = (Button)findViewById(R.id.btn);
        list = (TextView)findViewById(R.id.list);
        btn2 = (Button)findViewById(R.id.btn_2);
    }

    @Override
    protected void bindEvent() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsUtil.testReadAllContacts();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsUtil.getPhoneContacts();
            }
        });
    }

    @Override
    protected void execute() {

    }
}
