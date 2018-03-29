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

public class ChangeActivity extends AppCompatActivity {

    TextView back;
    EditText edit_oldpwd;
    EditText edit_newpwd;
    EditText edit_repwd;
    Button chang_btn;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        dbHelper = new DatabaseHelper(this,"UserDate.db",null,1);

        back = (TextView)findViewById(R.id.back);
        edit_oldpwd = (EditText) findViewById(R.id.edit_oldpwd);
        edit_newpwd = (EditText) findViewById(R.id.edit_newpwd);
        edit_repwd = (EditText) findViewById(R.id.edit_repwd);
        chang_btn = (Button) findViewById(R.id.qr);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        chang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpwd = edit_oldpwd.getText().toString().trim();
                String newpwd = edit_newpwd.getText().toString().trim();
                String repwd = edit_repwd.getText().toString().trim();
                if(oldpwd.equals("")||newpwd.equals("")||repwd.equals("")) {
                    Toast.makeText(ChangeActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else if(CheckRightorNot(oldpwd)) {
                    if (!newpwd.equals(oldpwd)) {
                        if (newpwd.equals(repwd)) {
                            if (update(oldpwd, newpwd)) {
                                Toast.makeText(ChangeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangeActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(ChangeActivity.this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChangeActivity.this, "新密码不能与原始密码相同", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //更新数据
    public boolean update(String oldpwd,String newpwd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newpwd);
        db.update("users", contentValues,"password = ?",new String[] {oldpwd } );
        db.close();
        return true;
    }

    //判断是否与数据库中相同
    public boolean CheckRightorNot(String oldpwd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "select * from users where password = ?";
        Cursor cursor = db.rawQuery(Query, new String[] {oldpwd});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
