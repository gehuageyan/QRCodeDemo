package com.blq.qrcode.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blq.qrcode.Model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述
 *
 * @author SSNB
 *         date 2016/5/7 9:58
 */
public class HistoryDb {
    public static int STATUS_CREATE=0;
    public static int STATUS_SCAN=1;

    private static final String TAG = HistoryDb.class.getSimpleName();
    private Context context;
    private DBHelper dbHelper;
    public HistoryDb(Context context){
        this.context = context;
        dbHelper = new DBHelper(this.context);
    }

    /**
     * 添加历史记录
     * @param status 该条记录的状态
     *               STATUS_CREATE 表示创建历史
     *               STATUS_SCAN 表示扫描历史
     * @param style 该记录的类型（短信，通讯录，网址，文本）
     * @param content1 具体内容1
     * @param content2 具体内容2
     */
    public void addHistory(int status,String style,String content1 ,String content2,String time){
        String sql="INSERT INTO history (status, style, content1,content2,time)" +
                " VALUES (?,?,?,?,?)";
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.execSQL(sql,new Object[]{status,style,content1,content2,time});
        db.close();
    }


    /**
     * 查询生产和创建历史
     * @param status 该条记录的状态
     *               STATUS_CREATE 表示创建历史
     *               STATUS_SCAN 表示扫描历史
     * @param pager 当前查询的页数
     * @return 返回list列表
     */
    public List<HistoryModel> selectHistory(int status,int pager){
        List<HistoryModel> list = new ArrayList<>();
        if(pager<=0){
            pager=1;
        }
        String statusStr=status+"";
        pager = (pager-1)*10;
        String startNub = pager+"";
        String sql="SELECT * " +
                "FROM  `history`" +
                "WHERE status =?" +
                "ORDER BY mid DESC " +
                "LIMIT ? , 10";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,new String[]{statusStr,startNub});
        while (cursor.moveToNext()){
            HistoryModel model = new HistoryModel();
            model.setType(cursor.getString(cursor.getColumnIndex("style")));
            model.setContent1(cursor.getString(cursor.getColumnIndex("content1")));
            model.setContent2(cursor.getString(cursor.getColumnIndex("content2")));
            model.setTime(cursor.getString(cursor.getColumnIndex("time")));
            list.add(model);
        }
        db.close();
        cursor.close();
        return list;
    }
}
