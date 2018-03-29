package com.example.xiaoxiong.click_me;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactList = new ArrayList<>();
    Button but_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ListView contactView = (ListView) findViewById(R.id.contact);

        contactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = contactList.get(position);
                String[] content = a.split("\n");

                call(content[1]);
            }
        });

        but_back = (Button) findViewById(R.id.but_back);

        //建立适配器
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
        contactView.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS}, 1);
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
                while (cursor.moveToNext()) {
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

    //拨打电话
    private void call(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (SecurityException e){
            Log.e("error", e.getMessage());
        }
        finish();
    }

    //获取权限
    @Override
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
    public void ret(View view) {
        Intent intent5 = new Intent(ContactsActivity.this,ListActivity.class) ;
        startActivity(intent5);
        finish();
    }
}
