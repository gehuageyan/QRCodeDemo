package com.blq.qrcode.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.blq.qrcode.R;
import com.blq.qrcode.activity.CreateQRCodeActivity;
import com.blq.qrcode.function.GenerateStyle;

public class CreateMainFragment extends BaseFragment {
    private LinearLayout smsLayout;
    private LinearLayout contactsLayout;
    private LinearLayout httpLayout;
    private LinearLayout textLayout;
    @Override
    protected int layout() {
        return R.layout.fragment_create_main;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView(View view) {
        smsLayout = (LinearLayout)view.findViewById(R.id.fl_ll_ll_sms);
        contactsLayout = (LinearLayout)view.findViewById(R.id.fl_ll_ll_contacts);
        httpLayout = (LinearLayout)view.findViewById(R.id.fl_ll_ll_http);
        textLayout = (LinearLayout)view.findViewById(R.id.fl_ll_ll_text);
    }

    @Override
    protected void bindEvent() {
        smsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(GenerateStyle.SMS);
            }
        });
        contactsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(GenerateStyle.CONTACTS);
            }
        });
        httpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(GenerateStyle.URL);
            }
        });
        textLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(GenerateStyle.TEXT);
            }
        });
    }

    private void startActivity(GenerateStyle type){
        Intent intent = new Intent(getActivity(), CreateQRCodeActivity.class);
        intent.putExtra("TYPE",type);
        startActivity(intent);
    }

}
