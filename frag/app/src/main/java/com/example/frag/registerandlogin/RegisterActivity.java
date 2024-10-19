package com.example.frag.registerandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frag.R;
import com.example.frag.database.UserDb;
import com.example.frag.entity.User;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    Button reg_but_qd;
    Button reg_return;

    EditText et_reg_phone;
    EditText et_reg_pw;
    private UserDb mHelper;
    private static String tag="tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register);


        Toast toast_save = Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT);
        reg_but_qd = findViewById(R.id.reg_but_qd);
        reg_return = findViewById(R.id.reg_return);
        et_reg_phone = findViewById(R.id.et_reg_phone);
        et_reg_pw = findViewById(R.id.et_reg_pw);






        //返回按钮返回登录界面
        reg_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this,LoginMainActivity.class);
                startActivity(intent);
            }
        });

        //注册按钮
        reg_but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=et_reg_phone.getText().toString();
                String password =et_reg_pw.getText().toString();
                User user= null;
                toast_save.show();
                user=new User(username, password);
                if(mHelper.insert(user)>0){
                    toast_save.show();
                }

            }

        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        //获得数据库帮助器的实例
        mHelper = UserDb.getInstance(this);
        //打开数据库帮助器的读写链接
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭数据库连接
        mHelper.closeLink();
    }

}