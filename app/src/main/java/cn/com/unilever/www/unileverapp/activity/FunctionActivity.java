package cn.com.unilever.www.unileverapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.unilever.www.unileverapp.Fragment.AnswerFragment;
import cn.com.unilever.www.unileverapp.Fragment.ErrorCollectFragment;
import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.base.BaseFragmentActiviy;
import cn.com.unilever.www.unileverapp.config.MyConfig;
import cn.com.unilever.www.unileverapp.utils.CameraAlbumUtil;

/**
 * @class 功能界面
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 11:32
 */
public class FunctionActivity extends BaseFragmentActiviy {
    View headerLayout;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private DrawerLayout activity_function;
    private AnswerFragment answerfragment;
    private ErrorCollectFragment errorcollectfragment;
    private long clickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        //控件
        initView();
        //头布局
        itinToolbar();
        //进入时显示界面
        changFragment(new ErrorCollectFragment());
        //切换
        initNavigation();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        navigationView = (NavigationView) findViewById(R.id.nav);
        activity_function = (DrawerLayout) findViewById(R.id.activity_function);
    }

    private void itinToolbar() {
        mToolbar.setTitle("标题");//设置文本为空的
        setSupportActionBar(mToolbar);//调用此方法之后可以使用actionbar的所有功能
        ActionBar actionBar = getSupportActionBar();//通过上面设置的actionbar获取出来
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//Display：展示显示 HomeAsUp：左边的按钮 此条属性就是设置左边按钮能否被显示
            actionBar.setHomeAsUpIndicator(R.drawable.home_as_up);//设置HomeAsUp的图片
        }
        headerLayout = navigationView.getHeaderView(0);
        SharedPreferences sp = getSharedPreferences("logininformation", Context.MODE_PRIVATE);
        String userKey = sp.getString("userKey", null);
        String username = sp.getString("username", null);
        Log.d("TAG", userKey + "..." + username);
        TextView tv_name = (TextView) headerLayout.findViewById(R.id.tv_name);
        tv_name.setText(username);
        TextView tv_id = (TextView) headerLayout.findViewById(R.id.tv_lv);
        tv_id.setText(userKey);
    }

    private void initNavigation() {
        //头条设置的灰色，一旦进去之后自己就默认为灰色
        navigationView.setCheckedItem(R.id.error);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.error:
                        if (errorcollectfragment == null) {
                            errorcollectfragment = new ErrorCollectFragment();
                        }
                        changFragment(errorcollectfragment);
                        activity_function.closeDrawers();
                        break;
                    case R.id.answer:
                        answerfragment = new AnswerFragment();
                        changFragment(answerfragment);
                        activity_function.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    //当选项菜单具体某个item被选中的时候
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //android下面的键的名字，一直是这个名字，但是要用android点
            case android.R.id.home:
                //打开抽屉布局,因为xml是gravity属性，所以需要设置Gravity下面的属性
                activity_function.openDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }

    //跳转Fragment
    public void changFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!(fragment instanceof ErrorCollectFragment) && !(fragment instanceof AnswerFragment)) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.fl_commcontent_main, fragment);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MyConfig.bitmap = CameraAlbumUtil.onActivityResult(requestCode, resultCode, data);
        WebView webView = (WebView) findViewById(R.id.wv_error);
        webView.loadUrl("JavaScript:setImag('" + CameraAlbumUtil.outputImage.getAbsolutePath() + "')");
    }
}