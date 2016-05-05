package com.blq.qrcode.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

import com.blq.qrcode.Model.SMSModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述
 *  用于获取手机信息
 *  需要获取权限 <uses-permission android:name="android.permission.READ_SMS" />
 * @author SSNB
 *         date 2016/5/2 18:03
 */
public class SMSUtil {
    private static final String TAG = SMSUtil.class.getSimpleName();
    /**
     * 所有的短信
     */
    public static final String SMS_URI_ALL = "content://sms/";
    /**
     * 收件箱短信
     */
    public static final String SMS_URI_INBOX = "content://sms/inbox";
    /**
     * 已发送短信
     */
    public static final String SMS_URI_SEND = "content://sms/sent";
    /**
     * 草稿箱短信
     */
    public static final String SMS_URI_DRAFT = "content://sms/draft";
    private Activity activity;//这里有个activity对象，不知道为啥以前好像不要，现在就要了。自己试试吧。
    private Uri uri;
    private List<SMSModel> infos;

    public SMSUtil(Activity activity, String uri) {
        infos = new ArrayList<>();
        this.activity = activity;
        this.uri = Uri.parse(uri);
    }

    /**
     * Role:获取短信的各种信息 <BR>
     * Date:2012-3-19 <BR>
     *
     * @author CODYY)peijiangping
     */
    public List<SMSModel> getSmsInfo() {
        String[] projection = new String[] { "_id", "address", "person",
                "body", "date", "type" };
        Cursor cusor = activity.managedQuery(uri, projection, null, null,
                "date desc");
        int nameColumn = cusor.getColumnIndex("person");
        int phoneNumberColumn = cusor.getColumnIndex("address");
        int smsbodyColumn = cusor.getColumnIndex("body");
        int dateColumn = cusor.getColumnIndex("date");
        int typeColumn = cusor.getColumnIndex("type");
        if (cusor != null) {
            while (cusor.moveToNext()) {
                SMSModel smsinfo = new SMSModel();
                smsinfo.setName(cusor.getString(nameColumn));
                smsinfo.setDate(cusor.getString(dateColumn));
                smsinfo.setPhoneNumber(cusor.getString(phoneNumberColumn));
                smsinfo.setSmsbody(cusor.getString(smsbodyColumn));
                smsinfo.setType(cusor.getString(typeColumn));
                infos.add(smsinfo);
            }
            cusor.close();
        }
//        MLog.e(TAG, infos.toString());
        return infos;
    }
}
