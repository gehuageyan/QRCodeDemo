package com.blq.qrcode.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blq.qrcode.R;
import com.blq.qrcode.db.HistoryDb;
import com.blq.qrcode.function.CopyText;
import com.blq.qrcode.function.GenerateStyle;
import com.blq.qrcode.util.ContactsUtil;
import com.blq.snblib.util.MLog;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * 扫描二维码后会进入该界面
 */
public class AnalyzeActivity extends BaseActivity {

    private TextView navigation_title;
    private String[] navigationTitles={"短信","通讯录","网址","文本"};

    private ImageView headImgView;
    private int[] headImgs={R.mipmap.icon_sms,R.mipmap.icon_contacts,
            R.mipmap.icon_http,R.mipmap.icon_text};

    private ImageView content1ImgView;
    private int[] content1Img ={R.mipmap.icon_sms_content1,R.mipmap.icon_contacts_content1,
            R.mipmap.icon_http_smalll,R.mipmap.icon_text_small};

    private TextView content1TextView;

    private LinearLayout content2Group;
    private ImageView content2ImgView;
    private int[] content2Img ={R.mipmap.icon_sms_content2,R.mipmap.icon_contacts_content2,
            R.mipmap.icon_http_smalll,R.mipmap.icon_text_small};
    private TextView content2TextView;

    private Button sendBtn;
    private String[] sendBtnText = {"发送短信","添加到手机通讯录","浏览器打开","复制文本"};

    private GenerateStyle mStyle;
    private String content;
    private View.OnClickListener sendClickListener;
    @Override
    protected int contentView() {
        return R.layout.activity_analyze;
    }

    @Override
    protected void initDate() {
        Intent intent = getIntent();
        String type =intent.getStringExtra("TYPE");//通过TYPE这个key来接受传入的数据的类型
        content = intent.getStringExtra("CONTENT");//通过CONTENT这个key来接受传入的数据内容
        for (GenerateStyle style:GenerateStyle.values()){
            if(type.equals(style.getTitle())){
                mStyle=style;//匹配传入的type是那种类型
                break;
            }
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
        navigation_title = (TextView)findViewById(R.id.analyze_title);

        headImgView = (ImageView)findViewById(R.id.analyze_img_head);

        content1ImgView = (ImageView)findViewById(R.id.ll_ll_img_content1);
        content1TextView=(TextView)findViewById(R.id.ll_ll_tv_content1);
        content2Group= (LinearLayout)findViewById(R.id.ll_ll_content2_group);
        content2ImgView=(ImageView)findViewById(R.id.ll_ll_img_content2);
        content2TextView=(TextView)findViewById(R.id.ll_ll_tv_content2);

        sendBtn = (Button)findViewById(R.id.ll_btn_send);
        MLog.e("mytype="+mStyle+";content:"+content);
        final String[] q = content.split("\\|\\|");//解析通过 “||”来分割的内容
        switch (mStyle){
            case SMS:
                initView(0,q[0],q[1]);
                sendClickListener=new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //如果是短信类型的就设置按钮的功能为调用系统短信发送短信
                        Uri smsToUri = Uri.parse("smsto:"+q[0]);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                        intent.putExtra("sms_body", q[1]);
                        startActivity(intent);
                    }
                };
                break;
            case CONTACTS:
                initView(1,q[0],q[1]);
                sendClickListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果是通讯录类型的就直接添加到通讯录中
                    ContactsUtil.addContacts(AnalyzeActivity.this,q[0],q[1]);
                    Toast.makeText(AnalyzeActivity.this,"添加通讯录成功",Toast.LENGTH_LONG).show();
                }
            };
                break;
            case URL:
                initView(2,q[0],"");
                sendClickListener=new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //如果是网页地址类型按钮功能就是调用系统默认浏览器打开url
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(content));
                        startActivity(intent);
                    }
                };
                break;
            case TEXT:
                initView(3,q[0],"");
                sendClickListener=new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //如果是文本类型按钮功能就改为点击复制
                        CopyText.copy(content,AnalyzeActivity.this);
                        Toast.makeText(AnalyzeActivity.this,"复制成功",Toast.LENGTH_LONG).show();
                    }
                };
                break;
        }
    }
    private void initView(int i,String content1,String content2){
        navigation_title.setText(navigationTitles[i]);
        headImgView.setImageResource(headImgs[i]);
        content1ImgView.setImageResource(content1Img[i]);
        content2ImgView.setImageResource(content2Img[i]);
        content1TextView.setText(content1);
        content2TextView.setText(content2);
        if(i==2||i==3){
            content2Group.setVisibility(View.GONE);
        }
        sendBtn.setText(sendBtnText[i]);

        HistoryDb db = new HistoryDb(this);//将扫描的数据写入到数据库
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String tim = sf.format(date);
        db.addHistory(HistoryDb.STATUS_SCAN,mStyle.getTitle(),content1,content2,tim);
    }

    @Override
    protected void bindEvent() {
        sendBtn.setOnClickListener(sendClickListener);
    }

    @Override
    protected void execute() {

    }
}
