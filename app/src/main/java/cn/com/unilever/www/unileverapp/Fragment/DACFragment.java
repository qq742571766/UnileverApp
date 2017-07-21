package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
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
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.activity.FunctionActivity;
import cn.com.unilever.www.unileverapp.config.MyConfig;
import okhttp3.Call;
/**
 * @class DAC观察
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/7/21 11:10
 */

class DACFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Timer timer = new Timer();
    private Button btn_dac;
    private WebView wv_dac;
    private String s;
    private Context context;
    private ErrorFragment errorFragment;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                s = (String) msg.obj;
            }
            if (msg.what == 1) {
                btn_dac.performClick();
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
        view = inflater.inflate(R.layout.fragment_dac, null);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.mToolbar);
        toolbar.setTitle("DAC");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyConfig.sourceStrArray = new ArrayList<>();
        initWidget();
        initdata();
        btn_dac.performClick();
    }

    private void initWidget() {
        wv_dac = (WebView) view.findViewById(R.id.wv_dac);
        btn_dac = (Button) view.findViewById(R.id.btn_dac);
        WebSettings webSettings = wv_dac.getSettings();
        //设置支持javaScript脚本语言
        webSettings.setJavaScriptEnabled(true);
        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);
        //设置客户端-不跳转到默认浏览器中
        wv_dac.setWebViewClient(new WebViewClient());
        //加载网络资源
        wv_dac.loadUrl("file:///android_asset/H50B7ECBA/www/samt_page.html");
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        //设置支持js调用java
        wv_dac.addJavascriptInterface(new DACFragment.AndroidAndJSInterface(), "Android");
        //页面监听
        wv_dac.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        timer.schedule(task, 0, 100);
        btn_dac.setOnClickListener(this);
    }

    private void initdata() {
        OkHttpUtils
                .post()
                .url(MyConfig.url + "/HiperMES/ematAndroid.sp?method=toAndroid")
                .build()
                .connTimeOut(30000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("AAA", response);
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (s != null) {
            wv_dac.loadUrl("javascript:javaCallJs(" + "'" + s + "'" + ")");
        }
    }

    private class AndroidAndJSInterface {
        @JavascriptInterface
        public void errors() {
            if (errorFragment == null) {
                errorFragment = new ErrorFragment();
            }
            ((FunctionActivity) getActivity()).changFragment(errorFragment);
        }

        @JavascriptInterface
        public void next() {
            Toast.makeText(context, "下一个1", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void skip() {
            Toast.makeText(context, "下一个2", Toast.LENGTH_SHORT).show();
        }
    }
}