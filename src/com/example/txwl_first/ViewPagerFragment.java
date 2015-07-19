package com.example.txwl_first;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.txwl_first.Adapter.Me_ListViewAdapter;
import com.example.txwl_first.Util.Utility_ForListView;

import java.util.ArrayList;


//viewpage中填充的 专门放置listview的fragment
public class ViewPagerFragment extends Fragment{
    private String mArgument;
    public static final String ARGUMENT = "key";
    private View view;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.viewpager_listview_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv= (ListView) view.findViewById(R.id.lv_viewpager);
        Bundle bundle = getArguments();
        if (bundle != null)
            mArgument = bundle.getString(ARGUMENT);
        ArrayList<String> list=new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            list.add(mArgument);
        }
        lv.setAdapter(new Me_ListViewAdapter(getActivity(), list));
        //在绑定adapter后调用手动测量工具 设计listview高度
        Utility_ForListView.setListViewHeightBasedOnChildren(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "点击了item模块", Toast.LENGTH_LONG).show();
            }
        });
    }
}
