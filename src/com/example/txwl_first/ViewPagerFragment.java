package com.example.txwl_first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.txwl_first.Adapter.Me_ListViewAdapter;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.bean.GetMyInfoBean;
import com.example.txwl_first.bean.GetMyInfoItemBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;


//viewpage中填充的 专门放置listview的fragment
public class ViewPagerFragment extends Fragment {
    private String TAG = "ViewPagerFragment--";
    private String mArgument;
    public static final String ARGUMENT = "key";
    private View view;
    private ListView lv;
    private GetMyInfoBean bean;
    private ArrayList<GetMyInfoItemBean> list;
    private Me_ListViewAdapter adapter;

    private final static  String QUERY_LISTVIEW_CAR_ITEM="query_listview_car_item";
    private final static  String QUERY_LISTVIEW_HOUSE_ITEM="query_listview_house_item";
    private final static  String QUERY_LISTVIEW_CREDIT_ITEM="query_listview_credit_item";
    private final static  String QUERY_LISTVIEW_OTHER_ITEM="query_listview_other_item";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewpager_listview_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView) view.findViewById(R.id.lv_viewpager);
        Bundle bundle = getArguments();
        if (bundle != null)
            mArgument = bundle.getString(ARGUMENT);
//        if (PreferenceUtils.getIsLogin()) {
//            //如果登录开始联网 获取个人信息数据
//            //只传id 获取所有数据
//            getHttpMyInfo(PreferenceUtils.getUserId(), "", mArgument);
//        }
        list = new ArrayList<GetMyInfoItemBean>();
        adapter = new Me_ListViewAdapter(getActivity(), list);
        lv.setAdapter(adapter);
        //在绑定adapter后调用手动测量工具 设计listview高度
        Utility_ForListView.setListViewHeightBasedOnChildren(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LoanDetailActivity.class);
                intent.putExtra("registid", list.get(i).getRegistid());
                if ("1".equals(list.get(i).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_CAR_ITEM);
                }
                if ("2".equals(list.get(i).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_HOUSE_ITEM);
                }
                if ("3".equals(list.get(i).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_CREDIT_ITEM);
                }
                if ("4".equals(list.get(i).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_OTHER_ITEM);
                }
                intent.putExtra("headImage", list.get(i).getOwneridimg());
                startActivity(intent);

            }
        });
    }


    public void initListView(GetMyInfoBean bean) {
        for (int i = 0; i < bean.getRegistinfolist().length; i++) {
            if ("0".equals(mArgument)){
                // 0标识为 是全部界面 需要显示所有数据
                list.add(bean.getRegistinfolist()[i]);//添加list里面的item项目 到arraylist
            }else if (mArgument.equals(bean.getRegistinfolist()[i].getRegisttype())){
                list.add(bean.getRegistinfolist()[i]);//添加list里面的item项目 到arraylistLog
            }
//            list.add(bean.getRegistinfolist()[i]);//添加list里面的item项目 到arraylist
        }
        adapter.notifyDataSetChanged();
        //更新adapter后 需要重新测量展示listview
        Utility_ForListView.setListViewHeightBasedOnChildren(lv);
        lv.setFocusable(false);//设置焦点为false 防止父级scrollview 初始化不是顶点位置显示
    }
}
