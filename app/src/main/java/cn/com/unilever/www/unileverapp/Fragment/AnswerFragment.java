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

import java.util.Timer;
import java.util.TimerTask;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.activity.FunctionActivity;
import cn.com.unilever.www.unileverapp.config.MyConfig;

import static cn.com.unilever.www.unileverapp.config.MyConfig.name;
import static cn.com.unilever.www.unileverapp.config.MyConfig.sourceStrArray;

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
        // TODO: 2017/6/13 获取问题内容
        s = "{" +
                "\"" + "a0" + "\"" + ":" + "7" + "," +
                "\"" + "a1" + "\"" + ":" + "\"" + "a1" + "\"" + "," +
                "\"" + "a2" + "\"" + ":" + "\"" + "a2" + "\"" + "," +
                "\"" + "a3" + "\"" + ":" + "\"" + "a3" + "\"" + "," +
                "\"" + "a4" + "\"" + ":" + "\"" + "a4" + "\"" + "," +
                "\"" + "a5" + "\"" + ":" + "\"" + "a5" + "\"" + "," +
                "\"" + "a6" + "\"" + ":" + "\"" + "a6" + "\"" + "," +
                "\"" + "a7" + "\"" + ":" + "\"" + "a7" + "\"" + "}";
        // TODO: 2017/6/15 将json转换成String数组,得到长度
        MyConfig.cahngdu = new String[0];
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
                sourceStrArray = url.split("&Fruit=");
                Log.d("AAA", "" + sourceStrArray.length);
                name = sourceStrArray[0].split("=");
                Log.d("AAA", "" + name.length);
                int num = sourceStrArray.length - 1;
                if (num != 0) {
                    if (name.length >= 2) {
                        if (emaTok == null) {
                            emaTok = new EMATok();
                        }
                        ((FunctionActivity) getActivity()).changFragment(emaTok);
                    } else {
                        Snackbar.make(webView, "请输入答题人工号", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(webView, "请选择题目", Snackbar.LENGTH_SHORT).show();
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