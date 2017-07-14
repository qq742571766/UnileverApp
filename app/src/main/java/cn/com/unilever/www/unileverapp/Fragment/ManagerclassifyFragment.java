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
import cn.com.unilever.www.unileverapp.config.MyConfig;

/**
 * @class
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/7/14 10:10
 */
class ManagerclassifyFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ErrorFragment errorFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_managerclass, null);
        Toolbar toolbar = (Toolbar) ((FunctionActivity) getActivity()).findViewById(R.id.mToolbar);
        toolbar.setTitle("异常填写");
        initview();
        return view;
    }

    private void initview() {
        Button SMAT = (Button) view.findViewById(R.id.SMAT);
        Button DAC = (Button) view.findViewById(R.id.DAC);
        Button EMAT = (Button) view.findViewById(R.id.EMAT);
        Button TAG = (Button) view.findViewById(R.id.TAG);
        SMAT.setOnClickListener(this);
        DAC.setOnClickListener(this);
        EMAT.setOnClickListener(this);
        TAG.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SMAT:
                MyConfig.type = "SMAT";
                break;
            case R.id.DAC:
                MyConfig.type = "DAC";
                break;
            case R.id.EMAT:
                MyConfig.type = "EMAT";
                break;
            case R.id.TAG:
                MyConfig.type = "TAG";
                break;
        }
        if (errorFragment == null) {
            errorFragment = new ErrorFragment();
        }
        ((FunctionActivity) getActivity()).changFragment(errorFragment);
    }
}
