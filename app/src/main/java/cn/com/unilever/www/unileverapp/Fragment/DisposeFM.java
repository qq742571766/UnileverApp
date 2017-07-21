package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.unilever.www.unileverapp.R;

/**
 * @class 异常解决
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/22 14:15
 */
public class DisposeFM extends Fragment implements View.OnClickListener {
    private View view;
    private TextView xiangqing1;
    private RelativeLayout rl;
    private TextView xiangqing2;
    private RelativeLayout rl_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_history, null);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.mToolbar);
        toolbar.setTitle("异常处理");
        initWidget();
        return view;
    }

    private void initWidget() {
        xiangqing1 = (TextView) view.findViewById(R.id.xiangqing1);
        xiangqing2 = (TextView) view.findViewById(R.id.xiangqing2);
        rl = (RelativeLayout) view.findViewById(R.id.rl);
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        rl_title.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_title:
                if (xiangqing2.getVisibility() == View.GONE && rl.getVisibility() == View.GONE) {
                    xiangqing1.setVisibility(View.GONE);
                    xiangqing2.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.VISIBLE);
                } else {
                    xiangqing1.setVisibility(View.VISIBLE);
                    xiangqing2.setVisibility(View.GONE);
                    rl.setVisibility(View.GONE);
                }
                break;
        }
    }
}
