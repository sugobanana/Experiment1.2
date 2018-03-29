package com.example.xiaoxiong.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    TextView text1;
    EditText editText2;
    EditText editText5;
    EditText editText6;
    Button button;
    private DatabaseManager dbManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        text1 = (TextView) findViewById(R.id.text1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        button = (Button) findViewById(R.id.button);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editText2.getText().toString();//获取输入内容
                String pwd = editText5.getText().toString();
                String repwd = editText6.getText().toString();
                if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(repwd)) {//判断是否为空
                    // if (dbManager.searchUserDate_re(user)){
                    // Toast.makeText(SecondActivity.this, "该账号已存在", Toast.LENGTH_SHORT).show();
                    //}else if(!pwd.equals(repwd)){//判断密码是否一致
                    //  Toast.makeText(SecondActivity.this, "输入不一致", Toast.LENGTH_SHORT).show();
                    //  } else {
                    //   Toast.makeText(SecondActivity.this, "信息输入有空", Toast.LENGTH_SHORT).show();
                    // }
                    // } else if(dbManager.insertUserData(user,pwd)) {
                    dbManager.insertUserData(user,pwd);
                    Toast.makeText(SecondActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(SecondActivity.this,MainActivity.class);
                finish();
            }
        });
    }


}
