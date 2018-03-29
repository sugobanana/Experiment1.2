package com.example.xiaoxiong.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView text1;
    EditText username_edit;
    EditText password_edit;
    Button button1;
    CheckBox rememberPass;
    private DatabaseManager dbManager = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);//获取SharedPreferences对象
        text1 = (TextView)findViewById(R.id.text1);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent1);
                finish();
            }
        });//a click event

        //获取布局ID
        username_edit = (EditText) findViewById(R.id.username);
        password_edit = (EditText) findViewById(R.id.password);
        button1 = (Button) findViewById(R.id.button1);
        rememberPass = (CheckBox) findViewById(R.id.checkBox);

        boolean isRemember = pref.getBoolean("记住密码", false);
        if(isRemember) {
            String user = pref.getString("username", "");
            String password = pref.getString("password", "");
            username_edit.setText(user);
            password_edit.setText(password);
            rememberPass.setChecked(true);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username_edit.getText().toString().trim();
                String pass = password_edit.getText().toString().trim();
                //if(dbManager.searchUserDate(user,pass)) {
                if(user.equals("li")&&pass.equals("1234")){
                    edt = pref.edit();
                    if(rememberPass.isChecked()) {
                        edt.putBoolean("记住密码", true);
                        edt.putString("user",user);
                        edt.putString("password", pass);
                    } else {
                        edt.clear();
                    }
                    edt.apply();

                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this,"登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
