package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.activity.FunctionActivity;
import cn.com.unilever.www.unileverapp.config.MyConfig;

/**
 * @class 答题
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/6/13 10:14
 */
public class EMATok extends Fragment implements View.OnClickListener {
    private int i = 0;
    private View view;
    private WebView webView;
    private Button button;
    private Context context;
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
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ematok, null, false);
        return view;
    }

    private void initview() {
        webView = (WebView) view.findViewById(R.id.wv_emat_ok);
        WebSettings webSettings = webView.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);
        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);
        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());
        //加载网络资源
        webView.loadUrl("file:///android_asset/EMATok.html");
        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        webView.addJavascriptInterface(new AndroidAndJSInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String[] urls = url.split("=");
                if (urls[1].equals("1")) {
                    MyConfig.ExcellentNumber += 1;
                } else if (urls[1].equals("2")) {
                    MyConfig.FineNumber += 1;
                } else if (urls[1].equals("3")) {
                    MyConfig.DadNumber += 1;
                }
                if (MyConfig.sourceStrArray.size() == i) {
                    EMATAccomplish accomplish = new EMATAccomplish();
                    ((FunctionActivity) getActivity()).changFragment(accomplish);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        button = (Button) view.findViewById(R.id.btn_emat_ok);
        timer.schedule(task, 0, 10);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (MyConfig.problem != null && MyConfig.sourceStrArray.size() > i) {
            //todo:网络异常,无数据
            String s = MyConfig.problem.get(MyConfig.sourceStrArray.get(i));
            webView.loadUrl("javascript:javaCallJs(" + "'" + s + "'" + ")");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initview();
        button.performClick();
    }

    private class AndroidAndJSInterface {
        @JavascriptInterface
        public void next() {
            if (MyConfig.sourceStrArray.size() > i) {
                i++;
            }
        }
    }
}