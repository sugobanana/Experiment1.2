package com.example.xiaoxiong.test;

import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


    public class MainActivity extends AppCompatActivity  {
        final int PICK_CONTACT = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //获取按钮组件
            Button bn = (Button) findViewById(R.id.bn);
//绑定监听事件
            bn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0){
                    Intent intent = new Intent();
//                intent--Action是表示抽象要完成的动作
                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent--Type：类型
                    intent.setType("vnd.android.cursor.item/phone");
                    startActivityForResult(intent,PICK_CONTACT);
                }
            });
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            switch(requestCode){
                case(PICK_CONTACT):
                    if(resultCode==Activity.RESULT_OK){
//                    获取返回数据
                        Uri contactData = data.getData();
                        CursorLoader cursorLoader = new CursorLoader(this,contactData,null,null,null,null);
//                    查询联系人的名字
                        Cursor cursor = cursorLoader.loadInBackground();

                        if(cursor.moveToFirst()){
                            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            //获取联系人的名字
                            String name =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                            String phoneNumber = "No phone Number";
//                        根据联系人查询联系人的详细信息
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
                            if(phones.moveToFirst()){
//                            提取电话号码
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
//                        关闭游标
                            phones.close();
                            EditText show = (EditText)findViewById(R.id.show);
//                        显示联系人的名称
                            show.setText(name);
                            EditText phone=(EditText)findViewById(R.id.phone);
//                        显示联系人的号码
                            phone.setText(phoneNumber);
                        }
//                    关闭游标
                        cursor.close();
                    }
                    break;
            }
        }
    }