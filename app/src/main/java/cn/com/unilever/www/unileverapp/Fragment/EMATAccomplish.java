package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.config.MyConfig;

/**
 * @class EMAT提交
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/7/10 15:18
 */
public class EMATAccomplish extends Fragment implements View.OnClickListener {
    public View view;
    public String pass = "no";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_accomplish, null, false);
        initview();
        return view;
    }

    private void initview() {
        Button btn_no = (Button) view.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(this);
        Button btn_yes = (Button) view.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(this);
        TextView textView6 = (TextView) view.findViewById(R.id.textView6);
        textView6.setText(MyConfig.name);
        TextView textView8 = (TextView) view.findViewById(R.id.textView8);
        textView8.setText(MyConfig.sourceStrArray.size()+"");
        TextView textView10 = (TextView) view.findViewById(R.id.textView10);
        textView10.setText(MyConfig.ExcellentNumber+"");
        TextView textView12 = (TextView) view.findViewById(R.id.textView12);
        textView12.setText(MyConfig.FineNumber+"");
        TextView textView14 = (TextView) view.findViewById(R.id.textView14);
        textView14.setText(MyConfig.DadNumber+"");
        Log.d("AAA",MyConfig.sourceStrArray.toString()+MyConfig.problem.toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no:
                pass = "no";
                break;
            case R.id.btn_yes:
                pass = "yes";
                break;
        }
    }
}