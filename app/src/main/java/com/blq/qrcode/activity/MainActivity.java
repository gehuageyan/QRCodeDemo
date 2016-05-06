package com.blq.qrcode.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blq.qrcode.R;
import com.blq.qrcode.fragment.AboutFragment;
import com.blq.qrcode.fragment.CreateMainFragment;
import com.blq.qrcode.fragment.HistoryFragment;
import com.blq.qrcode.fragment.ScanCodeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private ViewPager mViewPager;

    private TextView titleView;
    private String[] titles = {"扫描二维码","生成二维码","历史记录","关于"};

    private LinearLayout[] tabLayouts;
    private int[] tabDefaultImg = {R.mipmap.icon_tab_scan_default,R.mipmap.icon_tab_create_default,
            R.mipmap.icon_tab_history_default,R.mipmap.icon_tab_about_default};
    private int[] tabSelectImg = {R.mipmap.icon_tab_scan_select,R.mipmap.icon_tab_create_select,
            R.mipmap.icon_tab_history_select,R.mipmap.icon_tab_about_select};
    private String[] tabTitle={"扫码","生成","历史","关于"};
    private ColorStateList tabColorDefault;
    private ColorStateList tabColorSelect ;

    private List<Fragment> fragmentList;
    private ScanCodeFragment oneFragment;
    private CreateMainFragment twoFragment;
    private HistoryFragment threeFragment;
    private AboutFragment fourFragment;

    private int currentTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        initDate();
        initView();
        getDate();
        bindEvent();
    }

    /**
     * 初始化需要用到的数据
     */
    private void initDate() {
        fragmentList = new ArrayList<>();
        oneFragment = new ScanCodeFragment();
        twoFragment = new CreateMainFragment();
        threeFragment = new HistoryFragment();
        fourFragment = new AboutFragment();

        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);

        LinearLayout scanLayout = null;
        LinearLayout createLayout = null;
        LinearLayout historyLayout= null;
        LinearLayout aboutLayout= null;
        tabLayouts = new LinearLayout[]{scanLayout,createLayout,historyLayout,aboutLayout};
        tabColorDefault = getResources().getColorStateList(R.color.tab_text_default);
        tabColorSelect = getResources().getColorStateList(R.color.tab_text_select);
    }

    /**
     * 初始化view
     */
    private void initView() {
        titleView = (TextView)findViewById(R.id.main_title);
        mViewPager = (ViewPager)findViewById(R.id.main_vp_content);
        mViewPager.setOffscreenPageLimit(3);
        tabLayouts[0] = (LinearLayout)findViewById(R.id.navigation_item_scan);

        tabLayouts[1] = (LinearLayout)findViewById(R.id.navigation_item_create);

        tabLayouts[2] = (LinearLayout)findViewById(R.id.navigation_item_history);
        tabLayouts[3] = (LinearLayout)findViewById(R.id.navigation_item_about);

        for (int i=0;i<tabLayouts.length;i++){
            initTab(i);
            tabLayouts[i].setOnClickListener(this);
        }

    }

    /**
     * 初始化底部导航栏
     * @param index 需要初始化的导航栏的索引
     */
    private void initTab(int index) {
        ((TextView)tabLayouts[index].findViewById(R.id.ll_tv_title)).setText(tabTitle[index]);
        updateTab(index,false);
    }

    /**
     * 更新底部导航栏的视图
     * @param index 导航栏的索引
     * @param isSelect 是否被选中(true选中/false未选中)
     */
    private void updateTab(int index,boolean isSelect){
        titleView.setText(titles[index]);
        ((ImageView)tabLayouts[index].findViewById(R.id.ll_img_head)).setImageResource(isSelect?tabSelectImg[index]:tabDefaultImg[index]);
        ((TextView)tabLayouts[index].findViewById(R.id.ll_tv_title)).setTextColor(isSelect?tabColorSelect:tabColorDefault);
    }

    /**
     * 获取数据，加载fragment到viewPager
     */
    private void getDate() {
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(1);
        updateTab(1,true);
    }


    /**
     * 绑定事件
     */
    private void bindEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTabView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 更新底部导航栏的状态
     * @param newCurrentTab 最新移动到的导航栏
     */
    private void updateTabView(int newCurrentTab) {
        updateTab(currentTab,false);
        updateTab(newCurrentTab,true);
        currentTab=newCurrentTab;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigation_item_scan:
                mViewPager.setCurrentItem(0,true);
                break;
            case R.id.navigation_item_create:
                mViewPager.setCurrentItem(1,true);
                break;
            case R.id.navigation_item_history:
                mViewPager.setCurrentItem(2,true);
                break;
            case R.id.navigation_item_about:
                mViewPager.setCurrentItem(3,true);
                break;

        }
    }

    class MainFragmentPagerAdapter extends FragmentPagerAdapter{

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}
