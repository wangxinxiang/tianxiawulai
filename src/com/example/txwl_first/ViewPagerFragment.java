package com.example.txwl_first;

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
public class ViewPagerFragment extends Fragment{
    private String TAG="ViewPagerFragment--";
    private String mArgument;
    public static final String ARGUMENT = "key";
    private View view;
    private ListView lv;
    private GetMyInfoBean bean;
    private ArrayList<GetMyInfoItemBean> list;
    private Me_ListViewAdapter adapter;

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
        TAG+=mArgument;
        if (PreferenceUtils.getIsLogin()){
            //如果登录开始联网 获取个人信息数据
            //只传id 获取所有数据
            getHttpMyInfo(PreferenceUtils.getUserId(),"",mArgument);
        }
        list=new ArrayList<GetMyInfoItemBean>();
        adapter=new Me_ListViewAdapter(getActivity(),list);
        lv.setAdapter(adapter);
        //在绑定adapter后调用手动测量工具 设计listview高度
        Utility_ForListView.setListViewHeightBasedOnChildren(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "点击了item模块", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getHttpMyInfo(int id,String key,String registtype) {
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(10000);
        //查询所有黑名单接口 目前没有参数上传
        final RequestParams params = new RequestParams();
        params.put("userid", id);//搜索内容
        params.put("key", key);//搜索内容
        params.put("registtype", registtype);//搜索内容

        client.post(Url.GetMyInfo_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "开始联网");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "联网成功");
                Log.d(TAG + "  onSuccess-->", new String(responseBody));//打印网络访问结果

                try {
                    bean = new GsonBuilder().create().fromJson(new String(responseBody), GetMyInfoBean.class);
                    if ((bean != null) && ("success".equals(bean.getStatus()))&&bean.getRegistinfolist().length!=0) {
                        //网络请求成功
//                        int size=bean.getRegistinfolist().length;
//                        for (int i = 0; i < size; i++) {
//                            list.add(bean.getRegistinfolist()[i]);//添加list里面的item项目 到arraylist
//                        }
//                        adapter.notifyDataSetChanged();
                        TXWLApplication.getInstance().showTextToast("没数据");
                    }else if(bean.getRegistinfolist().length==0){
//                         GetMyInfoItemBean test=new GetMyInfoItemBean();
//
//                        test.setName("test");
//                        test.setMobile("test");
//                        test.setRegistname("test");
//                        test.setAccount("test");
//                        test.setRegisttype("1");
//                        test.setAnnualrate("test");
//                        test.setDate("test");
//                        list.add(test);
                        TXWLApplication.getInstance().showTextToast("没数据");

                    }
                    else {
                        Toast.makeText(getActivity(), "网络错误，请检查网络", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG, "联网失败");
            }
        });
    }
}
