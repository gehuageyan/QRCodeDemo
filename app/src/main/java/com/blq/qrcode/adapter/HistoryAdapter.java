package com.blq.qrcode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blq.qrcode.Model.HistoryModel;
import com.blq.qrcode.R;

import java.util.List;

/**
 * 类描述
 *
 * @author SSNB
 *         date 2016/5/7 11:39
 */
public class HistoryAdapter extends BaseAdapter {

    private static final int[] headImgs={R.mipmap.icon_sms,R.mipmap.icon_contacts,
            R.mipmap.icon_http,R.mipmap.icon_text};
    private String[] title={"短信","通讯录","网址","文本"};

    private static final String TAG = HistoryAdapter.class.getSimpleName();
    private Context context;
    private List<HistoryModel> dataList;
    public HistoryAdapter(Context context, List<HistoryModel> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_history_item,null);
            viewHolder.head = (ImageView)convertView.findViewById(R.id.ll_img_head);
            viewHolder.title = (TextView)convertView.findViewById(R.id.ll_ll_tv_title);
            viewHolder.content = (TextView)convertView.findViewById(R.id.ll_ll_tv_content);
            viewHolder.time = (TextView)convertView.findViewById(R.id.ll_ll_tv_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HistoryModel model = (HistoryModel) getItem(position);
        int i = 0;
        String contnet = "";
        switch(model.getType()){
            case "qr_sms":
                i=0;
                contnet = "短信联系人:"+model.getContent1()+"\n";
                contnet = contnet+"短信内容:"+model.getContent2();
                break;
            case "qr_contacts":
                i=1;
                contnet = "联系人姓名:"+model.getContent1()+"\n";
                contnet = contnet+"联系人电话:"+model.getContent2();
                break;
            case "qr_url":
                i=2;
                contnet = "网站链接:"+model.getContent1();
                break;
            case "qr_text":
                i=3;
                contnet = "文本内容:"+model.getContent1();
                break;

        }

        viewHolder.head.setImageResource(headImgs[i]);
        viewHolder.title.setText(title[i]);
        viewHolder.content.setText(contnet);
        viewHolder.time.setText(model.getTime());

        return convertView;
    }


    public void updateView(List<HistoryModel> listData){
        this.dataList = listData;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        ImageView head;
        TextView title;
        TextView content;
        TextView time;
    }
}
