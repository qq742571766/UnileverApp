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
 * @class 主管类别
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/7/14 10:10
 */
class ManagerclassifyFragment extends Fragment implements View.OnClickListener {
    private SMATFragment smat;
    private DACFragment dac;
    private View view;
    private ErrorFragment errorFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_managerclass, null);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.mToolbar);
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
                if (smat == null) {
                    smat = new SMATFragment();
                }
                ((FunctionActivity) getActivity()).changFragment(smat);
                break;
            case R.id.DAC:
                MyConfig.type = "DAC";
                if (dac == null) {
                    dac = new DACFragment();
                }
                ((FunctionActivity) getActivity()).changFragment(dac);
                break;
            case R.id.EMAT:
                MyConfig.type = "EMAT";
                if (errorFragment == null) {
                    errorFragment = new ErrorFragment();
                }
                ((FunctionActivity) getActivity()).changFragment(errorFragment);
                break;
            case R.id.TAG:
                MyConfig.type = "TAG";
                if (errorFragment == null) {
                    errorFragment = new ErrorFragment();
                }
                ((FunctionActivity) getActivity()).changFragment(errorFragment);
                break;
        }
    }
}
