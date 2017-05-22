package cn.com.unilever.www.unileverapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.utils.CameraAlbumUtil;

/**
 * @class 登录界面
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 11:32
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox cbMemory;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("islogin", Context.MODE_PRIVATE);
        boolean islogin = sp.getBoolean("isChecked", false);
        if (islogin) {
            String username = sp.getString("username", null);
            String password = sp.getString("password", null);
            if (username != null && password != null) {
                Login(username, password);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextInputLayout til_username = (TextInputLayout) findViewById(R.id.til_username);
        til_username.setCounterEnabled(true);
        til_username.setCounterMaxLength(20);
        TextInputLayout til_password = (TextInputLayout) findViewById(R.id.til_password);
        til_password.setCounterEnabled(true);
        til_password.setCounterMaxLength(20);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        cbMemory = (CheckBox) findViewById(R.id.cb_memory);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            boolean isChecked = cbMemory.isChecked();
            Login(username, password);
            boolean islogin = true;
            if (islogin) {
                if (isChecked) {
                    //通过sp储存用户
                    SharedPreferences sp = getSharedPreferences("islogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isChecked", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                }
            }
        }
    }

    private void Login(String username, String password) {
        // TODO: 2017/5/17 登录接口
//        Snackbar.make(this.get, "登录成功" + "用户名" + username + "密码" + password, Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FunctionActivity.class);
        startActivity(intent);
        this.finish();
    }
}