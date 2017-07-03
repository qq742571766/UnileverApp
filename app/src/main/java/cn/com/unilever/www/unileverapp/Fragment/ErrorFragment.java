package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.config.MyConfig;
import cn.com.unilever.www.unileverapp.utils.CameraAlbumUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.com.unilever.www.unileverapp.config.MyConfig.name;
import static cn.com.unilever.www.unileverapp.config.MyConfig.sourceStrArray;

/**
 * @class 异常填写
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 14:24
 */
public class ErrorFragment extends Fragment {
    private final static String url = "file:///android_asset/ErrorFragmentCall.html";
    private View view;
    private CameraAlbumUtil util;
    private ImageView imageView;
    private Context context;
    private WebView webview;
    private String[] saveError;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        util = new CameraAlbumUtil(getActivity());
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_error, null);
        initWidget();
        //获取数据
        return view;
    }

    private void initWidget() {
        webview = (WebView) view.findViewById(R.id.wv_error);
        imageView = (ImageView) view.findViewById(R.id.iv_problem_pictures);
        WebSettings webSettings = webview.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);
        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);
        //设置客户端-不跳转到默认浏览器中
        webview.setWebViewClient(new WebViewClient());
        //加载网络资源
        webview.loadUrl(url);
        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        //设置支持js调用java
        webview.addJavascriptInterface(new AndroidAndJSInterface(), "Android");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                File outputImage = new File(context.getExternalCacheDir(), "headPic.JPEG");
            }
        });
        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
                        webview.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            private String[] saveError;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String[] sourceStrArray = url.split("\\?");
                String[] one = sourceStrArray[1].split("&");
                String saveError = "{";
                for (int i = 0; i < one.length; i++) {
                    String[] save = one[i].split("=");
                    saveError += save[0] + ":" + save[1];
                    if (i < one.length - 1) {
                        saveError += ",";
                    }
                }
                saveError += "}";
                Log.d("AAA", saveError);
                OkHttpUtils
                        .get()
                        .url("http://192.168.10.21:8080/HiperMATICMES/ErrorController.sp")
                        .addParams("method", saveError)
                        .build()
                        .connTimeOut(300000)
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.d("AAA", id + "..." + e + "..." + call);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.d("AAA", id + "..." + response);
                            }
                        });
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void chooseDagilog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("选择头像")
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

        @JavascriptInterface
        public void show() {
            if (MyConfig.bitmap != null) {
                imageView.setImageBitmap(MyConfig.bitmap);
            }
        }

        @JavascriptInterface
        public void upload() {
            // TODO: 2017/6/13 上报接口(图片)
        }
    }
}