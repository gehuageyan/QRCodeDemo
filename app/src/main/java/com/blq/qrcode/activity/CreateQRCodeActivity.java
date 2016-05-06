package com.blq.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
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

    private static final String qrPrefix="qrcode";

    //外部传入的生成类型
    private GenerateStyle type;

    //导航栏相关
    private ImageView navigationBackBtn;
    private TextView navigationTitleView;
    private String[] navigationTitles={"生成短信","生成通讯录","生成网址","生成文本"};

    //头部相关
    private ImageView headImgView;
    private int[] headImgs={R.mipmap.icon_sms,R.mipmap.icon_contacts,
            R.mipmap.icon_http,R.mipmap.icon_text};
    private TextView headTitleView;
    private String[] headTitles = {"短信","通讯录","网址","文本"};

    //主题内容相关
    private ImageView content1ImgView;
    private int[] content1Img ={R.mipmap.icon_sms_content1,R.mipmap.icon_contacts_content1,
            R.mipmap.icon_http_smalll,R.mipmap.icon_text_small};
    private EditText content1;

    private LinearLayout content2Group;
    private ImageView content2ImgView;

    private int[] content2Img ={R.mipmap.icon_sms_content2,R.mipmap.icon_contacts_content2,
            R.mipmap.icon_http_smalll,R.mipmap.icon_text_small};
    private EditText content2;

    //创建二维码按钮
    private Button createBtn;

    //显示二维码
    private ImageView qrImgView;
    //二维码保存的路径(由方法返回)
    private String qrImgSrc;
    private int qrWight;

    //分享按钮
    private Button fenxiangBtn;

    @Override
    protected int contentView() {
        return R.layout.activity_create_qrcode;
    }
//67fe3F
    @Override
    protected void initDate() {
        Intent intent = getIntent();
        type = (GenerateStyle) intent.getSerializableExtra("TYPE");
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        if (size.x>size.y){
            qrWight=(int)(size.y*0.7);
        }else{
            qrWight=(int)(size.x*0.7);
        }

    }

    @Override
    protected void initView() {
        navigationBackBtn = (ImageView)findViewById(R.id.ll_ll_img_back);
        navigationTitleView = (TextView)findViewById(R.id.create_title);

        headImgView = (ImageView)findViewById(R.id.ll_ll_img_head);
        headTitleView = (TextView)findViewById(R.id.ll_ll_tv_title);

        content1ImgView = (ImageView)findViewById(R.id.ll_ll_img_content1);
        content1 = (EditText)findViewById(R.id.ll_ll_et_content1);

        content2Group = (LinearLayout)findViewById(R.id.ll_ll_content2_group);
        content2ImgView = (ImageView)findViewById(R.id.ll_ll_img_content2);
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
        navigationTitleView.setText(navigationTitles[i]);
        headImgView.setImageResource(headImgs[i]);
        headTitleView.setText(headTitles[i]);
        content1ImgView.setImageResource(content1Img[i]);
        content2ImgView.setImageResource(content2Img[i]);
        if (i==2||i==3){
            content2Group.setVisibility(View.GONE);
        }

    }

    @Override
    protected void bindEvent() {
        navigationBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        generateQRCode.setSize(qrWight,qrWight);
        Bitmap qrBitmap =generateQRCode.getQRCodeBitmap();
        qrImgSrc=BitmapUtil.saveBitmap(qrBitmap,qrPrefix+System.currentTimeMillis()+".jpg");
        qrImgView.setImageBitmap(qrBitmap);
    }

    @Override
    protected void execute() {

    }
}
