package com.blq.qrcode.function;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述
 *
 * @author SSNB
 *         date 2016/5/4 16:30
 */
public class GenerateQRCode {
    private static final String TAG = GenerateQRCode.class.getSimpleName();
    public static final String titleCut="/#/";//字符串头部标识和内容的分割标志
    public static final String contentCut="||";//字符串内容之间的分割标志
    private String content;//传入的字符串
    private GenerateStyle style;//生成二维码的类型
    private String realContent;//拼接后的字符串
    private int showWidth=200;//二维码显示的宽度
    private int showHeight=200;//二维码显示的高度

    public GenerateQRCode(String content,GenerateStyle style){
        this.content = content;
        this.style = style;
        initDate();
    }

    private void initDate() {
        realContent=style.getTitle()+titleCut+content;
    }

    /**
     * 设置图片显示的大小
     * @param showWidth
     * @param showHeight
     */
    public void setSize(int showWidth,int showHeight){
        this.showHeight = showHeight;
        this.showWidth = showWidth;
    }

    /**
     * 返回生成的二维码
     * @return 生成的二维码
     */
    public Bitmap getQRCodeBitmap(){
        Bitmap bitmap =null;
        //如果传入的content数据是空的话那个就返回
        if(content==null||content.isEmpty()){
            return null;
        }

        try {
            Map hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(realContent, BarcodeFormat.QR_CODE, showWidth, showHeight,hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK :Color.WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(pixels,showWidth,showHeight, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
