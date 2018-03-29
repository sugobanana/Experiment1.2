package com.example.xiaoxiong.click_me;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    TextView text1;
    EditText editText2;
    EditText editText5;
    EditText editText6;
    Button button;
    private DatabaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        text1 = (TextView) findViewById(R.id.text1);

        button = (Button) findViewById(R.id.button);
        dbHelper = new DatabaseHelper(this,"UserDate.db",null,1);
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
                editText2 = (EditText) findViewById(R.id.editText2);
                editText5 = (EditText) findViewById(R.id.editText5);
                editText6 = (EditText) findViewById(R.id.editText6);
                String newname = editText2.getText().toString();
                String password = editText5.getText().toString();
                String repassword = editText6.getText().toString();
                if (newname.equals("") || password.equals("") || repassword.equals("")) {
                    Toast.makeText(SecondActivity.this, "输入有误", Toast.LENGTH_SHORT).show();
                } else if (CheckIsDataAlreadyInDborNot(newname)) {
                    Toast.makeText(SecondActivity.this, "该用户已被注册", Toast.LENGTH_SHORT).show();
                } else if (password.equals(repassword)) {
                    if (register(newname, password)) {
                        Toast.makeText(SecondActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(SecondActivity.this, "两次密码不同",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    /*public void logon(View view) {
        editText2 = (EditText) findViewById(R.id.editText2);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        String newname = editText2.getText().toString();
        String password = editText5.getText().toString();
        String repassword = editText6.getText().toString();
        if(newname.equals("")||password.equals("")||repassword.equals("")) {
            Toast.makeText(this, "输入有误",Toast.LENGTH_SHORT).show();
        }else if (CheckIsDataAlreadyInDborNot(newname)) {
            Toast.makeText(this,"该用户已被注册", Toast.LENGTH_SHORT).show();
        } else if(password.equals(repassword)){
            if(register(newname, password)) {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    //插入数据
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        db.insert("users",null,values);
        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }

    //检测用户名是否存在
    public  boolean CheckIsDataAlreadyInDborNot(String value) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from users where name = ?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }


}
