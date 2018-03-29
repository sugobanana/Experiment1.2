package com.example.xiaoxiong.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by XiaoXiong on 2017/12/6.
 */

public class DatabaseManager {
    User user;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper = null;
    private SQLiteDatabase mSQLiteDatabase = null;

    public DatabaseManager(Context context) {
        mContext = context;
    }

    //打开数据库
    /*public void openDataBase() throws SQLException {
        mDatabaseHelper =  new DatabaseHelper();
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }*/

    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }
    //添加新用户
    public boolean insertUserData(String username, String password) {
        SQLiteDatabase sdb= mDatabaseHelper.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("password",password);
        sdb.insert("users",null,values);
        sdb.close();
               return true;
       /*
        String str = "insert into users(username,password) values(?,?)";
       Object obj[] = {
            user.getUsername(),user.getPassword()
        };
        sdb.execSQL(str,new String[] {username,password});*/
    }

    //查询用户
    public boolean searchUserDate_re(String username) {
        SQLiteDatabase sdb= mDatabaseHelper.getReadableDatabase();
        String sql = "select * from users where username = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username});
        if (cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean searchUserDate(String username, String password) {
        SQLiteDatabase sdb= mDatabaseHelper.getReadableDatabase();
        String sql = "select * from users where username= ? and password = ?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username, password});
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
