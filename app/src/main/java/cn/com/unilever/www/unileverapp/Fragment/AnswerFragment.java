package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cn.com.unilever.www.unileverapp.R;

/**
 * @class 答题
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/17 14:25
 */
public class AnswerFragment extends Fragment {
    private View view;
    private Context context;
    private WebView webview;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer, null, false);
        return view;
    }
}