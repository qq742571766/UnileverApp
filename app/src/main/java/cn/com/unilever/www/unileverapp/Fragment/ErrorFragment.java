package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.io.File;

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
    private View view;
    private final static String url = "file:///android_asset/ErrorFragmentCall.html";
    private CameraAlbumUtil util;
    private ImageView imageView;
    private Context context;

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
        WebView webview = (WebView) view.findViewById(R.id.wv_error);
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
//                Toast.makeText(getActivity(), outputImage.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class AndroidAndJSInterface {
        @JavascriptInterface
        public void picture() {
            chooseDagilog();
            if (MyConfig.bitmap != null) {
                imageView.setImageBitmap(MyConfig.bitmap);
            }
        }
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
}