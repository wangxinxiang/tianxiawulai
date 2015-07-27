package com.example.txwl_first;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.txwl_first.Adapter.BlackListAdapter;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.BlackListBean;
import com.example.txwl_first.bean.BlackListItemBean;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class MenuFragment extends Fragment {
    private static String TAG="MenuFragment";
    private View view;
    private Resources resources;
    private TextView tv_add_deadbeater;
    private EditText et_search;
    private ImageButton ibtn_search;
    private ListView lv;

    private BlackListAdapter adapter;
    private BlackListBean blackListBean;
    private ArrayList<BlackListItemBean> list;
    private ArrayList<BlackListItemBean> byAllList;
    private ArrayList<BlackListItemBean> byWayList;

    private PullToRefreshListView mPullRefreshListView;
    private ListView actualListView;
    private int pi=1;
    private int ps=20;
    private boolean httpFirst=true;
    private boolean pullUp=false;
    private boolean pullDown=false;

    private final static  String QUERY_LISTVIEW_CAR_ITEM="query_listview_car_item";
    private final static  String QUERY_LISTVIEW_HOUSE_ITEM="query_listview_house_item";
    private final static  String QUERY_LISTVIEW_CREDIT_ITEM="query_listview_credit_item";
    private final static  String QUERY_LISTVIEW_OTHER_ITEM="query_listview_other_item";

    public static final int REQUSET = 1; //请求码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view=inflater.inflate(R.layout.tab_menu_fragment,null);
        resources = getResources();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();//初始化视图和控件
        initListener();//初始化 监听器
        getHttpByQueryAllBlackPerson();//联网获取所有黑名单
        Log.d(TAG, "onActivityCreated");
    }

    private void initListener() {
        et_search.addTextChangedListener(new TextWatcher() {
            //搜索框的监听 处理
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("et_search", "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("et_search", "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("et_search", "afterTextChanged");
                //当有输入时 显示清空按钮 实现点击清空输入
                if (editable.length() == 0 || et_search.equals("")) {
                    //输入为空 显示全部数据
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                    if (byAllList.size()==0){
                        getHttpByQueryAllBlackPerson();
                    }else {
                        list.clear();
                        list.addAll(byAllList);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    //输入不为空
                    getHttpByWayBlackPerson(editable.toString());
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                }
            }
        });

        //edittext的软键盘的搜索按钮监听
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //隐藏软键盘
                    if (textView.length() == 0 || textView.equals("")) {
                        Toast.makeText(getActivity(), getActivity().toString() + "输入框为空", Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        ((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        //实现跳转
//                    Intent intent=new Intent();
//                    intent.setClass(getActivity(),)
//                    startActivity(intent);
//                        Toast.makeText(getActivity(), getActivity().toString() + "点击了软键盘的搜索", Toast.LENGTH_LONG).show();
                        getHttpByWayBlackPerson(et_search.getText().toString().trim());
                        return true;
                    }

                }
                return false;
            }
        });

        ibtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "点击了搜索按钮", Toast.LENGTH_SHORT).show();
                String way=et_search.getText().toString().trim();
                if (way.equals("")){
                    //点击搜索按钮时候 输入框为空
                    TXWLApplication.getInstance().showTextToast("搜索关键字为空！");
                }else {
                    //输入框不为空
                     getHttpByWayBlackPerson(way);
                     mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                }


            }
        });

        //点击添加老赖 实现跳转
        tv_add_deadbeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(PreferenceUtils.getIsLogin()){
                    intent = new Intent(getActivity(), AddOldBeaterActivity.class);
                }else {
                    TXWLApplication.getInstance().showTextToast("登录用户才能添加老赖");
                    intent =new Intent(getActivity(),LoginActivity.class);
                }
                startActivityForResult(intent,REQUSET);
            }
        });
    }

    private void getHttpByWayBlackPerson(String way) {
        //通过关键字查找黑名单
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(10000);
        //查询所有黑名单接口 目前没有参数上传
        final RequestParams params = new RequestParams();
        params.put("way", way);//搜索内容

        client.post(Url.QueryBlackPersonFromWay_URL, params, new AsyncHttpResponseHandler() {

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
                    blackListBean = new GsonBuilder().create().fromJson(new String(responseBody), BlackListBean.class);
                    if ((blackListBean != null) && ("success".equals(blackListBean.getStatus()))) {
                        //网络请求成功
                        int size= blackListBean.getBlackListItemBeans().length;
                        if (size>0){
                            //返回有数据 更新显示
                            byWayList.clear();
                            for (int i = 0; i < size; i++) {
                                byWayList.add(blackListBean.getBlackListItemBeans()[i]);//添加list里面的item项目 到arraylist
                            }
                            list.clear();
                            list.addAll(byWayList);
                            adapter.notifyDataSetChanged();
                        }else {
                            //没有数据 提示没有搜索到结果
                            TXWLApplication.getInstance().showTextToast("查找不到符合条件的信息");
                            list.clear();
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                       TXWLApplication.getInstance().showTextToast(blackListBean.getMsg());
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


    private void getHttpByQueryAllBlackPerson() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(10000);
        //查询所有黑名单接口 目前没有参数上传
        RequestParams params = new RequestParams();
//        params.put("opcode", "");//应该有一个一次网络请求 的数量
        params.put("pageindex", pi);
        params.put("pagesize", ps);

        client.post(Url.QueryAllBlackPerson_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG,"开始联网");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "联网成功");
                Log.d(TAG + "  onSuccess-->", new String(responseBody));//打印网络访问结果

                try {
                    blackListBean=new GsonBuilder().create().fromJson(new String(responseBody),BlackListBean.class);
                    if ((blackListBean!=null)&&("success".equals(blackListBean.getStatus()))){
                        //网络请求成功
                        int size=blackListBean.getBlackListItemBeans().length;
                        if (httpFirst){
                            //第一次联网
                            httpFirst=false;
                            byAllList.clear();
                            for (int i = 0  ; i < size ; i++) {
                                byAllList.add(blackListBean.getBlackListItemBeans()[i]);//添加list里面的item项目 到arraylist
                            }
                            list.clear();
                            list.addAll(byAllList);
                            Log.d("conunt",blackListBean.getRecordcount());
                            adapter.notifyDataSetChanged();
                        }else {
                            if (pullDown){
                                //下拉 数据清空 放入新的网络请求数据
                                byAllList.clear();
                                pullDown=false;
                                for (int i = 0; i < size; i++) {
                                    byAllList.add(blackListBean.getBlackListItemBeans()[i]);//添加list里面的item项目 到arraylist
                                }
                                list.clear();
                                list.addAll(byAllList);
                                mPullRefreshListView.onRefreshComplete();
                                adapter.notifyDataSetChanged();
                            }if(pullUp){
                                //上拉 加载更多 在原有数据上添加 新的网络请求数据
                                pullUp=false;
                                if (size==0){
                                    TXWLApplication.getInstance().showTextToast("已经加载全部内容");
                                }
                                for (int i = 0; i < size; i++) {
                                    byAllList.add(blackListBean.getBlackListItemBeans()[i]);//添加list里面的item项目 到arraylist
                                }
                                list.clear();
                                list.addAll(byAllList);
                                mPullRefreshListView.onRefreshComplete();
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }else {
                        Toast.makeText(getActivity(),"网络错误，请检查网络",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    TXWLApplication.getInstance().showErrorConnected(e);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG,"联网失败");
            }
        });

    }

    private void initView() {
        tv_add_deadbeater = (TextView) view.findViewById(R.id.btn_text_add_deadbeater);
        et_search= (EditText) view.findViewById(R.id.et_search);
        ibtn_search= (ImageButton) view.findViewById(R.id.ibtn_search);

        mPullRefreshListView= (PullToRefreshListView)view.findViewById(R.id.lv_blacklist);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        actualListView=mPullRefreshListView.getRefreshableView();

        list=new ArrayList<BlackListItemBean>();
        byAllList = new ArrayList<BlackListItemBean>();
        byWayList=new ArrayList<BlackListItemBean>();
        adapter=new BlackListAdapter(getActivity(),list,resources);
        actualListView.setAdapter(adapter);
        ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载数据...");// 刷新时
        endLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示

        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //下拉刷新
                Log.d("onPullDownToRefresh", "下拉");
                pi = 1;
                pullDown = true;
                getHttpByQueryAllBlackPerson();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //上拉刷新
                Log.d("onPullUpToRefresh","上拉");
                pi++;
                pullUp = true;
                getHttpByQueryAllBlackPerson();

            }
        });

        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                int position=i-1;
                Intent intent = new Intent(getActivity(), LoanDetailActivity.class);
                if("1".equals(list.get(position).getRegisttype())){
                    intent.putExtra("fromButton", QUERY_LISTVIEW_CAR_ITEM);
                }
                if ("2".equals(list.get(position).getRegisttype())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_HOUSE_ITEM);
                }
                if ("3".equals(list.get(position).getRegisttype())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_CREDIT_ITEM);
                }
                if ("4".equals(list.get(position).getRegisttype())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_OTHER_ITEM);
                }
                intent.putExtra("registid", list.get(position).getRegistid());
                intent.putExtra("headImage", list.get(position).getOwneridimg());
                intent.putExtra("title_name", list.get(position).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "oPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


}
