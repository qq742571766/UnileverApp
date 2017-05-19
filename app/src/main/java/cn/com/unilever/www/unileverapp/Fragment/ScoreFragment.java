package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import cn.com.unilever.www.unileverapp.R;

/**
 * @class 评分
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 14:28
 */
public class ScoreFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_score, null);
        initWidget();
        return view;
    }

    private void initWidget() {
        WebView webview = (WebView) view.findViewById(R.id.wv_score);
    }
}
