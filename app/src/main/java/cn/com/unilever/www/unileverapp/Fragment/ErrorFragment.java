package cn.com.unilever.www.unileverapp.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.config.MyConfig;
import cn.com.unilever.www.unileverapp.utils.CameraAlbumUtil;

/**
 * @class 异常填写
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 14:24
 */
public class ErrorFragment extends Fragment {
    //D:\demo\UnileverApp\app\src\main\assets\H50B7ECBA\www\index.html
    private final static String url = "file:///android_asset/H50B7ECBA/www/index.html";
    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mFilePathCallback;
    private WebView mWebView;
    private View view;
    private CameraAlbumUtil util;
    private WebView webview;
    private boolean on_off = true;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1 && on_off) {
                webview.loadUrl(url);
                on_off = false;
            }
        }
    };
    private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_error, null);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.mToolbar);
        toolbar.setTitle("异常填写");
        initWidget();
        //获取数据
        return view;
    }

    private void initWidget() {
        webview = (WebView) view.findViewById(R.id.wv_error);
        WebSettings webSettings = webview.getSettings();
        //设置支持javaScript脚本语言
        webSettings.setJavaScriptEnabled(true);
        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);
        //设置客户端-不跳转到默认浏览器中
        webview.setWebViewClient(new WebViewClient());
        //加载网络资源
        webview.loadUrl(MyConfig.loginurl);
        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        //设置支持js调用java
        webview.addJavascriptInterface(new AndroidAndJSInterface(), "Android");
        //页面监听
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("AAA", url);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
                super.onPageFinished(view, url);
            }
        });
    }

    private void chooseDagilog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("选择相片")
                .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        util.takePhoto();
                    }
                })

                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        util.openAlbum();
                    }
                })
                .show();
    }

    private class AndroidAndJSInterface {
        @JavascriptInterface
        public void picture() {
            chooseDagilog();
        }
    }
}