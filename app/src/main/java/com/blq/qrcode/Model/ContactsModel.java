package com.blq.qrcode.Model;

import android.net.Uri;

import java.util.List;

/**
 * 类描述
 *
 * @author SSNB
 *         date 2016/5/2 20:55
 */
public class ContactsModel {
    private static final String TAG = ContactsModel.class.getSimpleName();
    /**
     * 名称
     */
    private String Name;
    /**
     * 电话号码（可能有多个）
     */
    private List<String> phones;

    /**
     * 电子邮箱（可能有多个）
     */
    private List<String> email;

    /**
     * 头像的uri
     */
    private Uri imgUri;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public String toString() {
        return "ContactsModel{" +
                "Name='" + Name + '\'' +
                ", phones=" + phones.toString() +
                ", email=" + email.toString() +
                ", imgUri=" + imgUri.toString() +
                '}';
    }
}
