package com.example.xiaoxiong.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button mReturnButton;
    EditText etname,etpwd;
    Button btLogin,btReg;
    DataBaseHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etname = (EditText) findViewById(R.id.editText1);
        etpwd = (EditText) findViewById(R.id.editText2);
        btLogin = (Button) findViewById(R.id.btLogin);
        btReg = (Button) findViewById(R.id.btReg);
        mReturnButton = (Button)findViewById(R.id.btReg);
        //ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo
        //image.setImageResource(R.drawable.tp);

        btReg.setOnClickListener(new OnClickListener() {///注册按钮
            @Override
            public void onClick(View v) {
                if (isUserNameAndPwdValid()){
                    if (isUserNameAndPwdValid()) {
                        String name = etname.getText().toString().trim();
                        String pwd = etpwd.getText().toString().trim();
                        dbHelper = new DataBaseHelper(getBaseContext());
                        db = dbHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(dbHelper.Name, name);
                        cv.put(dbHelper.Pwd, pwd);
                        db.insert(dbHelper.TableName, null, cv);
                        Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        btLogin.setOnClickListener(new OnClickListener() {///登录按钮

            @Override
            public void onClick(View v) {
                String name = etname.getText().toString().trim();
                String pwd = etpwd.getText().toString().trim();
                if (name.equals("li")&&pwd.equals("1234")){

                    //dbHelper = new DataBaseHelper(getBaseContext());
                    //db = dbHelper.getReadableDatabase();
                    //Cursor c = db.query(dbHelper.TableName, new String[]{dbHelper.Name, dbHelper.Pwd}, dbHelper.Name + "=?  and " + dbHelper.Pwd + "=?", new String[]{name, pwd}, null, null, null);
                    //if (c.getCount() > 0) {
                        Intent intent = new Intent(MainActivity.this,User.class) ;    //切换Login Activity至User Activity
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "登陆失败！请输入正确的用户名或密码", Toast.LENGTH_SHORT).show();

                    }

                }
          //  }
        });

    }


    public boolean isUserNameAndPwdValid() {
        if (etname.getText().toString().trim().equals("")) {
            Toast.makeText(this, "用户名为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (etpwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
