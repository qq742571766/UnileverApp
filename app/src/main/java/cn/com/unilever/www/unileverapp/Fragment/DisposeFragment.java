package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.unilever.www.unileverapp.R;
import cn.com.unilever.www.unileverapp.activity.FunctionActivity;
import cn.com.unilever.www.unileverapp.adapter.HistoryAdapter;
import cn.com.unilever.www.unileverapp.info.ErrorInfo;

/**
 * @class 异常列表
 * @name 林郝
 * @anthor QQ:742571766
 * @time 2017/5/22 13:29
 */
public class DisposeFragment extends Fragment {
    private View view;
    private HistoryAdapter adapter;
    private List<ErrorInfo> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dispose, null);
        //适配器
        adapter = new HistoryAdapter();
        //测试数据
        initadapter();
        //将数据添加至适配器
        adapter.adds(list);
        //初始化
        initWidget();
        return view;
    }

    //数据
    private void initadapter() {
        list = new ArrayList<>();
        ErrorInfo info = new ErrorInfo();
        info.title = "a";
        info.date = "a";
        info.category = "a";
        info.author_name = "a";
        info.url = "a";
        info.thumbnail_pic_s = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        list.add(info);
        ErrorInfo info2 = new ErrorInfo();
        info2.title = "b";
        info2.date = "b";
        info2.category = "b";
        info2.author_name = "b";
        info2.url = "b";
        info2.thumbnail_pic_s = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        list.add(info2);
        ErrorInfo info3 = new ErrorInfo();
        info3.title = "c";
        info3.date = "c";
        info3.category = "c";
        info3.author_name = "c";
        info3.url = "c";
        info3.thumbnail_pic_s = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        list.add(info3);

    }

    private void initWidget() {
        //控件
        RecyclerView rv_history = (RecyclerView) view.findViewById(R.id.rv_history);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_history.setLayoutManager(manager);
        //添加适配器
        rv_history.setAdapter(adapter);
        //点击事件
        adapter.setOnButtonClickListener(new HistoryAdapter.OnButtonClickListener() {
            @Override
            public void OnButtonClick(ErrorInfo info) {
                DisposeFM fm = new DisposeFM();
                Bundle bundle = new Bundle();
                bundle.putParcelable("info", info);
                fm.setArguments(bundle);
                ((FunctionActivity) getActivity()).changFragment(fm);
            }
        });
    }
}