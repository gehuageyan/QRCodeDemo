package com.blq.qrcode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.blq.snblib.util.MLog;

/**
 * 类描述
 * activity的基类
 * @author SSNB
 *         date 2016/5/2 17:36
 */
public abstract class BaseActivity extends Activity{
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView());
        MLog.e("Activity",this.getClass().getSimpleName());
        initDate();
        initView();
        bindEvent();
        execute();

    }
    protected abstract int contentView();
    protected abstract void initDate();
    protected abstract void initView();
    protected abstract void bindEvent();
    protected abstract void execute();
}
