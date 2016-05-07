package com.blq.qrcode.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.blq.qrcode.R;
import com.blq.qrcode.activity.HistoryActivity;
import com.blq.qrcode.db.HistoryDb;

public class HistoryFragment extends BaseFragment {

    private Button createBtn;
    private Button scanBtn;
    @Override
    protected int layout() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView(View view) {
        createBtn = (Button)view.findViewById(R.id.fl_ll_btn_his_create);
        scanBtn = (Button)view.findViewById(R.id.fl_ll_btn_his_scan);
    }

    @Override
    protected void bindEvent() {
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                intent.putExtra(HistoryActivity.INTENT_STATUS, HistoryDb.STATUS_CREATE);
                startActivity(intent);
            }
        });
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                intent.putExtra(HistoryActivity.INTENT_STATUS, HistoryDb.STATUS_SCAN);
                startActivity(intent);
            }
        });
    }


}
