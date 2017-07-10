package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import java.util.Timer;
import java.util.TimerTask;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.activity.FunctionActivity;
import cn.com.unilever.www.unileverapp.config.MyConfig;
import okhttp3.Call;

/**
 * @class 答题
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 14:25
 */
public class AnswerFragment extends Fragment implements View.OnClickListener {
    private View view;
    private WebView webView;
    private Button button;
    private EMATok emaTok;
    private String s;
    private Timer timer = new Timer();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                try {
                    JSONArray jsonArray = new JSONArray((String) msg.obj);
                    s = "{" +
                            "\"" + "a0" + "\"" + ":" + jsonArray.length() + ",";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        s += "\"" + "a" + (i + 1) + "\"" + ":" + "\"" + jsonObject.getString("questionContent") + "\"";
                        MyConfig.problem.add(jsonObject.getString("questionContent"));
                        if (i < jsonArray.length() - 1) {
                            s += ",";
                        }
                    }
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
        return view;
    }

    private void initdata() {
        OkHttpUtils
                .post()
                .url("http://192.168.10.20:8080/HiperMES_Unilever/ematQuestion.sp?method=toAndroid")
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
        webView.loadUrl("file:///android_asset/EMATCall.html");
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
                Log.d("AAA", url);
                //选择问题序号
                String[] urls = url.split("&Fruit=");
                //工号
                if (urls[0].split("=").length==2) {
                    String kayid = urls[0].split("=")[1];
                }else {
                    Snackbar.make(webView, "输入工号", Snackbar.LENGTH_SHORT).show();
                }
                if (urls.length <= 1) {
                    Snackbar.make(webView, "请选择题目", Snackbar.LENGTH_SHORT).show();
                } else {
                    for (int i = 1; i < urls.length; i++) {
                        MyConfig.sourceStrArray.add(Integer.valueOf(urls[i]));
                        Log.d("AAA", urls[i]+"..."+MyConfig.sourceStrArray.size());
                    }
                    EMATok emaTok = new EMATok();
                    ((FunctionActivity)getActivity()).changFragment(emaTok);
                }
                return super.shouldOverrideUrlLoading(view, url);
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
        initdata();
        initview();
        button.performClick();
    }
}
//通过
//(题)优良
//答题人(id)
//考核人(id)
//时间