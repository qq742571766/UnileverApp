package cn.com.unilever.www.unileverapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import cn.com.unilever.www.unileverapp.R;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @class 登录界面
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 11:32
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean islogin = true;
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox cbMemory;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String response = (String) msg.obj;
                try {
                    //json解析
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = jsonObject.getBoolean("result");
                    JSONObject user = jsonObject.getJSONObject("user");
                    user.getString("username");
                    user.getString("userKey");
                    user.getString("name");
                    //通过sp储存登录信息
                    SharedPreferences sp = getSharedPreferences("logininformation", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", user.getString("username"));
                    editor.putString("userKey", user.getString("userKey"));
                    editor.putString("name", user.getString("name"));
                    editor.apply();
                    if (result) {
                        //跳转
                        Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                        MainActivity.this.finish();
                    } else {
                        Snackbar.make(etUsername, "登录失败,请检查登录信息", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

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
        x.view().inject(this);
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
            if (islogin) {
                if (isChecked) {
                    //通过sp储存用户
                    SharedPreferences sp = getSharedPreferences("islogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isChecked", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    Log.d("TAG", username + password);
                    editor.apply();
                }
            }
        }
    }

    public void Login(String username, String password) {
        OkHttpUtils
                .post()
                .url("http://192.168.10.20:8080/HiperMES/login.sp?method=appLogin&loginName=" + username + "&password=" + password)
                .build()
                .connTimeOut(10000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("TAG", "登录错误:" + e);
                        Snackbar.make(btnLogin, "登录失败请检查网络" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        progressDialog = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                        progressDialog.setMessage("正在登陆中");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        Log.d("TAG", "登录成功");
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("TAG", "登录响应完成");
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                });
    }
}