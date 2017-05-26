package cn.com.unilever.www.unileverapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.unilever.www.unileverapp.R;

/**
 * @class 答题
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/22 16:57
 */
class ResponseFragment extends Fragment{

    private String url;

    @Override
    public void onAttach(Context context) {
        url = getArguments().getString("url");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_response, null);
        TextView textView = (TextView) view.findViewById(R.id.aaa);
        textView.setText(url);
        return view;
    }
}
