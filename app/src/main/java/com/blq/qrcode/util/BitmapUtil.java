package com.blq.qrcode.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.blq.snblib.util.MLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类描述
 *
 * @author SSNB
 *         date 2016/5/5 14:44
 */
public class BitmapUtil {
    private static final String TAG = BitmapUtil.class.getSimpleName();
    private static final String BasePath = "mnt/sdcard/qrcode";
    public static String saveBitmap(Bitmap bm,String fileName) {
        final String filePath = BasePath+"/"+fileName;
        MLog.e(TAG, "保存图片"+filePath);
        File f = new File(BasePath);
        if (!f.exists())
            f.mkdir();
        f = new File(filePath);

        try {
            if(!f.exists()){
                f.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filePath;
    }
}
