package com.blq.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blq.qrcode.R;
import com.blq.qrcode.function.GenerateQRCode;
import com.blq.qrcode.function.GenerateStyle;
import com.blq.qrcode.util.BitmapUtil;
import com.blq.snblib.util.MLog;

import java.io.File;

/**
 * 创建二维码
 */
public class CreateQRCodeActivity extends BaseActivity {

    private GenerateStyle type;

    private ImageView headImgView;
    private int[] headImgs={R.mipmap.icon_sms,R.mipmap.icon_contacts,
            R.mipmap.icon_http,R.mipmap.icon_text};

    private TextView headTitleView;
    private String[] headTitles = {"短信","通讯录","网址","文本"};

    private TextView content1TitleView;
    private String[] content1Titles={"短信1","通信录1","网址1","文本1"};

    private LinearLayout content2Group;
    private TextView content2TitleView;
    private String[] content2Titles={"短信2","通信录2","网址2","文本2"};

    private EditText content1;
    private EditText content2;

    private Button createBtn;

    private ImageView qrImgView;
    private String qrImgSrc;

    private Button fenxiangBtn;

    @Override
    protected int contentView() {
        return R.layout.activity_create_qrcode;
    }

    @Override
    protected void initDate() {
        Intent intent = getIntent();
        type = (GenerateStyle) intent.getSerializableExtra("TYPE");
        MLog.e("type:"+type.toString());
    }

    @Override
    protected void initView() {
        headImgView = (ImageView)findViewById(R.id.ll_ll_img_head);
        headTitleView = (TextView)findViewById(R.id.ll_ll_tv_title);

        content1TitleView = (TextView)findViewById(R.id.ll_ll_tv_content1_title);
        content1 = (EditText)findViewById(R.id.ll_ll_et_content1);

        content2Group = (LinearLayout)findViewById(R.id.ll_ll_content2_group);
        content2TitleView = (TextView)findViewById(R.id.ll_ll_tv_content2_title);
        content2 = (EditText)findViewById(R.id.ll_ll_et_content2);

        createBtn = (Button)findViewById(R.id.qr_create);
        qrImgView = (ImageView)findViewById(R.id.ll_ll_img_qr);
        fenxiangBtn = (Button)findViewById(R.id.ll_ll_fenxiang);

        switch (type){
            case SMS:
                initView(0);
                break;
            case CONTACTS:
                initView(1);
                break;
            case URL:
                initView(2);
                break;
            case TEXT:
                initView(3);
                break;
        }
    }
    private void initView(int i){
        headImgView.setImageResource(headImgs[i]);
        headTitleView.setText(headTitles[i]);
        content1TitleView.setText(content1Titles[i]);
        content2TitleView.setText(content2Titles[i]);
        if (i==2||i==3){
            content2Group.setVisibility(View.GONE);
        }

    }

    @Override
    protected void bindEvent() {
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateQRCode();
            }
        });
        fenxiangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                File f = new File(qrImgSrc);
                if (f != null && f.exists() && f.isFile()) {
                    shareIntent.setType("image/jpg");
                    Uri u = Uri.fromFile(f);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, u);
                }else{
                    Toast.makeText(CreateQRCodeActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(Intent.createChooser(shareIntent,"分享二维码"));
            }
        });
    }

    private void CreateQRCode() {
        String con1 = content1.getText().toString();
        String con2 = content2.getText().toString();

        GenerateQRCode generateQRCode = new GenerateQRCode(con1+GenerateQRCode.contentCut+con2, type);
        generateQRCode.setSize(400,400);
        Bitmap qrBitmap =generateQRCode.getQRCodeBitmap();
        qrImgSrc=BitmapUtil.saveBitmap(qrBitmap,"ewima"+System.currentTimeMillis()+".jpg");
        qrImgView.setImageBitmap(qrBitmap);
    }

    @Override
    protected void execute() {

    }
}
