package cn.com.unilever.www.unileverapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
 * @class 待处理列表
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
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.mToolbar);
        toolbar.setTitle("异常列表");
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
        info.title = "半成品丽料架,空箱与实物混放";
        info.date = "2017/7/7";
        info.category = "异常";
        info.author_name = "admin";
        info.thumbnail_pic_s = "http://img5.imgtn.bdimg.com/it/u=3145329202,154902187&fm=26&gp=0.jpg";
        list.add(info);
        ErrorInfo info2 = new ErrorInfo();
        info2.title = "箱子高度";
        info2.date = "2017/7/8";
        info2.category = "异常";
        info2.author_name = "admin";
        info2.thumbnail_pic_s = "http://files.3158.cn/article/201508/3103/071978781300138.jpg";
        list.add(info2);
        ErrorInfo info3 = new ErrorInfo();
        info3.title = "边线6s";
        info3.date = "2017/7/10";
        info3.category = "整改";
        info3.author_name = "admin";
        info3.thumbnail_pic_s = "http://www.shyecheng.com.cn/upload/image/shuangmianbujijiaodai-1413894128_lit.jpg";
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