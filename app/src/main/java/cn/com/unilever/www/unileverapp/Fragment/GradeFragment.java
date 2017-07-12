package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
 * @class 评分界面
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/7/11 17:32
 */
class GradeFragment extends Fragment implements View.OnClickListener {
    private int i = 0;
    private View view;
    private WebView webView;
    private Button button;
    private Context context;
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
    private Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grade, null, false);
        Toolbar toolbar = (Toolbar) ((FunctionActivity) getActivity()).findViewById(R.id.mToolbar);
        toolbar.setTitle("评分");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initview();
        button.performClick();
    }

    private void initview() {
        webView = (WebView) view.findViewById(R.id.wv_grade);
        WebSettings webSettings = webView.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);
        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);
        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());
        //加载网络资源
        webView.loadUrl("file:///android_asset/EMATGrade.html");
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
        button = (Button) view.findViewById(R.id.btn_grade);
        timer.schedule(task, 0, 10);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (MyConfig.problem != null && MyConfig.sourceStrArray.size() > i) {
            s = MyConfig.problem.get(MyConfig.sourceStrArray.get(i));
            //题
            webView.loadUrl("javascript:javaCallJs(" + "'" + s + "'" + ")");
            //答案
            SharedPreferences sp = context.getSharedPreferences("grade", Context.MODE_PRIVATE);
            String ss = sp.getString((i+1) + "", null);
            webView.loadUrl("javascript:javaCallJ(" + "'" + ss + "'" + ")");
        }
    }

    private class AndroidAndJSInterface {
        @JavascriptInterface
        public void next() {
            if (MyConfig.sourceStrArray.size() > i) {
                i++;
            }
        }
        @JavascriptInterface
        public void last() {
            if (MyConfig.sourceStrArray.size() > i) {
                i--;
            }
        }
    }
}
