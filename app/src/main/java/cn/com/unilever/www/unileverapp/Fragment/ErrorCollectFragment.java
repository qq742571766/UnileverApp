package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.activity.FunctionActivity;
import cn.com.unilever.www.unileverapp.view.ClearArcView;

/**
 * @class 异常管理
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/22 13:08
 */
public class ErrorCollectFragment extends Fragment implements View.OnClickListener {
    private View view;
    private DisposeFragment disposeFragment;
    private HistoryFragment historyFragment;
    private RankFragment rankFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collect, null, false);
        Toolbar toolbar = (Toolbar) ((FunctionActivity) getActivity()).findViewById(R.id.mToolbar);
        toolbar.setTitle("异常管理");
        init();
        initWidget();
        return view;
    }

    //获取数据
    private void init() {
        // TODO: 2017/5/22 获取数据 
    }

    private void initWidget() {
        ClearArcView cav_collect = (ClearArcView) view.findViewById(R.id.cav_collect);
        cav_collect.setAngle(50);
        Button btn_dispose = (Button) view.findViewById(R.id.btn_dispose);
        Button btn_error = (Button) view.findViewById(R.id.btn_error);
        Button btn_history = (Button) view.findViewById(R.id.btn_history);
        btn_dispose.setOnClickListener(this);
        btn_error.setOnClickListener(this);
        btn_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dispose:
                if (disposeFragment == null) {
                    disposeFragment = new DisposeFragment();
                }
                ((FunctionActivity) getActivity()).changFragment(disposeFragment);
                break;
            case R.id.btn_error:
                // TODO: 2017/7/14 主管上报
                if (rankFragment == null) {
                    rankFragment = new RankFragment();
                }
                ((FunctionActivity) getActivity()).changFragment(rankFragment);
                break;
            case R.id.btn_history:
                if (historyFragment == null) {
                    historyFragment = new HistoryFragment();
                }
                ((FunctionActivity) getActivity()).changFragment(historyFragment);
                break;
        }
    }
}