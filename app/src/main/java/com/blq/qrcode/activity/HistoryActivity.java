package com.blq.qrcode.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blq.qrcode.Model.HistoryModel;
import com.blq.qrcode.R;
import com.blq.qrcode.adapter.HistoryAdapter;
import com.blq.qrcode.db.HistoryDb;

import java.util.List;

public class HistoryActivity extends BaseActivity {

    public static final String INTENT_STATUS="status";

    private int status=0;
    private TextView titleView;
    private String[] titles={"生成历史","扫描历史"};

    private ListView listView;
    private List<HistoryModel> listData;
    private HistoryAdapter adapter;

    private TextView addMore;

    private HistoryDb historyDb;
    private int pager = 1;
    @Override
    protected int contentView() {
        return R.layout.activity_history;
    }

    @Override
    protected void initDate() {
        Intent intent = getIntent();
        status=intent.getIntExtra(INTENT_STATUS,0);

        historyDb = new HistoryDb(this);
        listData = historyDb.selectHistory(status,pager);
        if(listData.size()<10){
            addMore.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView() {
        findViewById(R.id.ll_ll_img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView =(TextView)findViewById(R.id.create_title);
        listView = (ListView)findViewById(R.id.ll_lv_history);
        addMore = (TextView)findViewById(R.id.ll_tv_add_more);
    }

    @Override
    protected void bindEvent() {
        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager++;
                List<HistoryModel> list=historyDb.selectHistory(status,pager);
                if(list.size()<10){
                    addMore.setVisibility(View.GONE);
                }
                listData.addAll(list);
                adapter.updateView(listData);
            }
        });
    }

    @Override
    protected void execute() {
        titleView.setText(titles[status]);
        adapter = new HistoryAdapter(this,listData);

        listView.setAdapter(adapter);
    }
}
