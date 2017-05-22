package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cn.com.unilever.www.unileverapp.R;

/**
 * @class 问答
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 14:25
 */
public class ProblemFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem, null);
        initWidget();
        return view;
    }

    private void initWidget() {
//        WebView webview = (WebView) view.findViewById(R.id.wv_problem);
    }
}
