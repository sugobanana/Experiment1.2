package com.example.xiaoxiong.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    TextView textView;
    Button but1;
    Button but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);

        textView = (TextView) findViewById(R.id.text);
        but1 = (Button) findViewById(R.id.but1);
        but2 = (Button) findViewById(R.id.but2);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, ContactActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
