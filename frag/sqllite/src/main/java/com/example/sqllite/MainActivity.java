package com.example.sqllite;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox cb_married;
    private DbContect mHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //获取按钮ID
        Button create_db;
        Button delete_db;
        DbContect helper = new DbContect(this);
        create_db=findViewById(R.id.create_db);
        delete_db=findViewById(R.id.delete_db);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        cb_married = findViewById(R.id.cb_married);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener (this);
        findViewById(R.id.btn_query).setOnClickListener(this);


        //创建数据库按钮单击事件
        create_db.setOnClickListener(v -> {
            //创建数据库
            helper.getWritableDatabase();
        });

        //删除数据库按钮单击事件
        delete_db.setOnClickListener(v -> {

            helper.getWritableDatabase().execSQL("drop table if exists user");

        });

        //查询数据库，显示在listview上


    }
    @Override
    protected void onStart() {
        super.onStart();
        //获得数据库帮助器的实例
        mHelper = DbContect.getInstance(this);
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

    @Override
    public void onClick(View v) {
        String name=et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();
        Toast toast_save = Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT);
        Toast toast_delete = Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT);
        Toast toast_update = Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT);
        Toast toast_query = Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT);
        User user= null;
        switch (v.getId()){
            case R.id.btn_save:
                //以下声明一个用户信息对象，并填写它的各字段值
                user=new User(name,
                        Integer.parseInt(age),
                        Long.parseLong(height),
                        Float.parseFloat(weight),
                        cb_married.isChecked());
                if(mHelper.insert(user)>0){
                   // ToastUtil.show(this, "添加成功");

                    toast_save.show();
                }
                /*mHelper.insert(user);*/
                break;
            case R.id.btn_delete:
                if(mHelper.deleteByName(name)>0){
                   // ToastUtil.show(this, "删除成功");

                    toast_delete.show();
                }
                break;
            case R.id.btn_update:
                user=new User(name,
                        Integer.parseInt(age),
                        Long.parseLong(height),
                        Float.parseFloat(weight),
                        cb_married.isChecked());
                if(mHelper.update(user)>0){
                    //ToastUtil.show(this, "修改成功");
                    toast_update.show();
                }
                break;
  /*          case R.id.btn_query:
                List<User> list = mHelper.queryAll();
                for (User u : list) {
                    Log.d("ning",u.toString());
                }
                break;*/
            case R.id.btn_query:
                List<User> list = mHelper.queryBYName(name);
                for (User u : list) {

                    toast_query.show();
                }
                break;

        }
    }

}