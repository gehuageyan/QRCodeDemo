package com.blq.qrcode.function;

/**
 * Created by SSNB on 2016/5/4.
 * 二维码的生成类型
 */
public enum GenerateStyle {
    CONTACTS("qr_contacts"),
    SMS("qr_sms"),
    URL("qr_url"),
    TEXT("qr_text");
    private String title;
    GenerateStyle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
