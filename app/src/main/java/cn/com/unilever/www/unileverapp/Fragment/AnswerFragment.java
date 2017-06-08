package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.unilever.www.unileverapp.R;

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
    private String s;
    Timer timer = new Timer();
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    button.performClick();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer, null, false);
        return view;
    }

    private void initdata() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        s = "{" +
                "\"" + "a0" + "\"" + ":" + "7" + "," +
                "\"" + "a1" + "\"" + ":" + "\"" + "a1" + "\"" + "," +
                "\"" + "a2" + "\"" + ":" + "\"" + "a2" + "\"" + "," +
                "\"" + "a3" + "\"" + ":" + "\"" + "a3" + "\"" + "," +
                "\"" + "a4" + "\"" + ":" + "\"" + "a4" + "\"" + "," +
                "\"" + "a5" + "\"" + ":" + "\"" + "a5" + "\"" + "," +
                "\"" + "a6" + "\"" + ":" + "\"" + "a6" + "\"" + "," +
                "\"" + "a7" + "\"" + ":" + "\"" + "a7" + "\"" + "}";
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
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String[] sourceStrArray = url.split("&Fruit=");
                String[] name = sourceStrArray[0].split("=");
                Log.e("ATAG", name[1] );
                for (int i = 1; i < sourceStrArray.length; i++) {
                    Log.e("ATAG", sourceStrArray[i] );

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