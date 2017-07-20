package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.activity.FunctionActivity;
import cn.com.unilever.www.unileverapp.config.MyConfig;
import okhttp3.Call;

/**
 * @class EMAT主界面
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 14:25
 */
public class AnswerFragment extends Fragment implements View.OnClickListener {
    private View view;
    private WebView webView;
    private Button button;
    private String s;
    private Timer timer = new Timer();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                try {
                    webView.loadUrl("file:///android_asset/H50B7ECBA/www/EMATCall.html");
                    JSONArray jsonArray = new JSONArray((String) msg.obj);
                    s = "{" +
                            "\"" + "a0" + "\"" + ":" + jsonArray.length() + ",";
                    MyConfig.problem = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        s += "\"" + "a" + (i + 1) + "\"" + ":" + "\"" + jsonObject.getString("questionContent") + "\"";
                        MyConfig.problem.add(jsonObject.getString("questionContent"));
                        if (i < jsonArray.length() - 1) {
                            s += ",";
                        }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    view.findViewById(R.id.EMATtext).setVisibility(View.GONE);
                    s += "}";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            switch (msg.what) {
                case 1:
                    button.performClick();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer, null, false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.mToolbar);
        toolbar.setTitle("问题发起");
        return view;
    }

    private void initdata() {
        OkHttpUtils
                .post()
                .url("http://192.168.10.24:8080/HiperMES/ematAndroid.sp?method=toAndroid")
                .build()
                .connTimeOut(30000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                });
    }

    private void initview() {
        webView = (WebView) view.findViewById(R.id.wv_emat);
        button = (Button) view.findViewById(R.id.aaaa);
        button.setVisibility(View.GONE);
        WebSettings webSettings = webView.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);
        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);
        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());
        //加载网络资源
        webView.loadUrl("http://192.168.10.24:8080/HiperMES/login.sp?method=login");
        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //选择问题序号
                String[] urls = url.split("&Fruit=");
                Log.d("AAA", urls.length + "");
                //file:///android_asset/EMATCall.html?id=eeddd&name=sssss,1,2,3
                String[] message = urls[0].split("&");
                ////file:///android_asset/EMATCall.html?id=eeddd,name=sssss
                String[] ids = message[0].split("=");
                ////file:///android_asset/EMATCall.html?id,eeddd
                String[] names = message[1].split("=");
                //name,sssss
                if (ids.length == 2 && ids[1] != null) {
                    try {
                        MyConfig.id = URLDecoder.decode(ids[1], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (names.length == 2 && names[1] != null) {
                        try {
                            MyConfig.name = URLDecoder.decode(names[1], "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (urls.length <= 1) {
                            Snackbar.make(webView, "请选择题目", Snackbar.LENGTH_SHORT).show();
                        } else {
                            for (int i = 1; i < urls.length; i++) {
                                if (i <= MyConfig.problem.size()) {
                                    MyConfig.sourceStrArray.add(Integer.valueOf(urls[i]) - 1);
                                }
                            }
                            Log.d("AAA", MyConfig.problem.toString() + "..." + MyConfig.sourceStrArray.toString());
                            EMATok emaTok = new EMATok();
                            ((FunctionActivity) getActivity()).changFragment(emaTok);
                        }
                    } else {
                        Snackbar.make(webView, "输入姓名", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(webView, "输入工号", Snackbar.LENGTH_SHORT).show();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }
        });
        timer.schedule(task, 0, 100);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (s != null) {
            webView.loadUrl("javascript:javaCallJs(" + "'" + s + "'" + ")");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MyConfig.sourceStrArray = new ArrayList<>();
        initview();
        initdata();
        button.performClick();
    }
}