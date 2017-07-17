package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.unilever.www.unileverapp.R;

/**
 * @class 异常历史
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/22 13:29
 */
public class HistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.mToolbar);
        toolbar.setTitle("历史异常");
        initWidget();
        return view;
    }

    private void initWidget() {
//        WebView webview = (WebView) view.findViewById(R.id.wv);
    }
}