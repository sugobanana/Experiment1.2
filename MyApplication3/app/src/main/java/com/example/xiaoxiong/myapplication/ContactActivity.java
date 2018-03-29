package com.example.xiaoxiong.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactList = new ArrayList<String>();
    Button but_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        ListView contactView = (ListView) findViewById(R.id.contact);

        but_back = (Button) findViewById(R.id.but_back);
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //建立适配器
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
        contactView.setAdapter(adapter);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_CONTACTS}, 1);
            } else {
                readContacts();
        }
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            //无条件查询
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToFirst()) {
                    //获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人电话
                    String tel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //姓名和电话加入到数组中
                    contactList.add(displayName + "\n" + tel);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    //获取权限
    public void  onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
