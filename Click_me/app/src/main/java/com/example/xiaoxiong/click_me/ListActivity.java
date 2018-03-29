package com.example.xiaoxiong.click_me;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    TextView textView;
    Button but1;
    Button but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        textView = (TextView) findViewById(R.id.text);
        but1 = (Button) findViewById(R.id.but1);
        but2 = (Button) findViewById(R.id.but2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ListActivity.this, ChangeActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void toNext(View view) {
        Intent intent = new Intent(ListActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }
}
