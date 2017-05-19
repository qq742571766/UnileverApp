package cn.com.unilever.www.unileverapp.base;

import android.Manifest;
import android.app.Application;

import com.yanzhenjie.permission.AndPermission;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * @class 创建
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 11:32
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .start();
    }
}