package com.example.frag.registerandlogin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.frag.MainActivity;
import com.example.frag.R;
import com.example.frag.Util.ToastUtil;
import com.example.frag.database.UserDb;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox cb_remember;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> launcher;
    private Button btn_login;
    private Button reg_but;

    private String mverifyCode;
    private SharedPreferences preferences;
    private Button login_button;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        tv_password = findViewById(R.id.login_phone_number_password);
        et_password = findViewById(R.id.login_phone_number_password_edit);
        btn_forget = findViewById(R.id.login_forget_password);
        cb_remember = findViewById(R.id.login_remember_password);
        reg_but = findViewById(R.id.reg_button);
        login_button = findViewById(R.id.login_login_button);

        //给rg_login设置监听
        RadioGroup rg_login = findViewById(R.id.login_radio_group);
        rg_login.setOnCheckedChangeListener(this);
        //给et_phone添加文本变更监听
        et_phone = findViewById(R.id.login_phone_number_edit);
        et_phone.setHint("请输入手机号");
        et_password.setHint(getString(R.string.input_password));


        btn_forget.setOnClickListener(this);
        rb_password = findViewById(R.id.login_password);
        rb_verifycode = findViewById(R.id.login_verification_code);

        btn_login = findViewById(R.id.login_login_button);
        btn_login.setOnClickListener(this);

        //单击注册按钮转跳注册页面
        reg_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginMainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //单击登录按钮
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的手机号和密码
                String username=et_phone.getText().toString();
                String password =et_password.getText().toString();
                if (username.equals("") || password.equals("")){
                    ToastUtil.show(LoginMainActivity.this,"用户名或密码不能为空");
                    return;
                }

                UserDb userDb = new UserDb(LoginMainActivity.this,"user.db",null,1);
                boolean b = userDb.queryFromusers(username, password);
                if (b&&username!=""&&password!=""){
                    ToastUtil.show(LoginMainActivity.this,"登录成功");
                    startActivity(new Intent(LoginMainActivity.this, MainActivity.class));
                } else{
                    ToastUtil.show(LoginMainActivity.this,"用户名或密码错误");
                }
            }
        });

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();
    }

    private void reload() {
        boolean remember = preferences.getBoolean("remember", false);
        if (remember) {
            String phone = preferences.getString("phone", null);
            if (phone != null) {
                et_phone.setText(phone);
            }
            String password = preferences.getString("password", null);
            if (password != null) {
                et_password.setText(password);
            }
            cb_remember.setChecked(true);
        }
    }

    //界面切换显示
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //选择了密码登录
            case R.id.login_password:

                tv_password.setText(getString(R.string.login_password));
                et_password.setHint(getString(R.string.input_password));
                btn_forget.setText(getString(R.string.forget_password));
                ToastUtil.show(this,"密码登录");
                cb_remember.setVisibility(View.VISIBLE);//显示

                break;
            //选择了验证码登录
            case R.id.login_verification_code:
                tv_password.setText(getString(R.string.verifycode));
                et_password.setHint(getString(R.string.input_verifycode));
                btn_forget.setText(getString(R.string.get_verifycode));
                cb_remember.setVisibility(View.GONE);//隐藏
                ToastUtil.show(this,"验证码登录");
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}




