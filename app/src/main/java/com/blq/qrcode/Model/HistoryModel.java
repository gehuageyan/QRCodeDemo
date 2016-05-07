package com.blq.qrcode.Model;

/**
 * 类描述
 *
 * @author SSNB
 *         date 2016/5/7 11:17
 */
public class HistoryModel {
    private static final String TAG = HistoryModel.class.getSimpleName();
    private String type;
    private String content1;
    private String content2;
    private String time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
