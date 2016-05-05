package com.blq.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blq.snblib.util.MLog;


public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MLog.e("Fragment",this.getClass().getSimpleName());
        View view = inflater.inflate(layout(), container, false);
        initDate();
        initView(view);
        bindEvent();
        return view;
    }

    protected abstract int layout();

    protected abstract void initDate();

    protected abstract void initView(View view);

    protected abstract void bindEvent();

}
