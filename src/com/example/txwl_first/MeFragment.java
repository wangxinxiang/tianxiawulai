package com.example.txwl_first;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.txwl_first.Adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class MeFragment extends Fragment {
    private static String TAG="MeFragment";
    private Resources resources;
    private View view;//填充Fragment的视图

    private TextView tv_title,tv_right;

    private RelativeLayout rl_myInfo;//我的信息模块
    private ImageView img_headimage;
    private TextView tv_user_name;
    private EditText et_search;
    private ImageButton ibtn_clear;
    private TextView tv_total,tv_violate,tv_reclaimed,tv_reclaiming;//四个统计模块的控件

    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private Fragment fragment_all,fragment_car,fragment_house,fragment_loan,fragment_other;
    private ImageView ivBottomLine;//下划线
    private int screenW;//屏幕宽度
    private int iv_x;
    private TextView tv_indicate_all,tv_indicate_car,tv_indicate_house,tv_indicate_loan,tv_indicate_other;
    private MyFragmentPagerAdapter adapter;
//    private MyListener listener = new MyListener();

    private int currIndex = 0;
    private int bottomLineWidth;//下划线的宽度
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    private int position_four;
    public final static int num = 5 ;//indicate的数量

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view=inflater.inflate(R.layout.tab_me_fragment,null);
        resources = getResources();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        initTitle();//初始化 标题栏
        initHeadViewPager();//初始化头部控件
        initListener();//绑定头部的事件
        initWidth();//bottomeLinen 并测量屏幕宽度划分5份
        initTextView();//初始化indicate的textview 并绑定点击事件
        initViewPager();//初始化view 并在其中填充fragment

        new myAsyncTask().execute();//模拟耗时操作 完成后开始测量viewpager中子控件的高度
        setHeadViewPager();//设置头部的各个值


    }

    private void initTitle() {
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        tv_right= (TextView) view.findViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("我的团队");
    }

    private void initListener() {

        rl_myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        tv_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"点击了tv_total模块",Toast.LENGTH_LONG).show();
            }
        });

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
                    ibtn_clear.setVisibility(View.GONE);
                } else {
                    ibtn_clear.setVisibility(View.VISIBLE);
                }
            }
        });

        ibtn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清空输入框
                et_search.setText("");
            }
        });

        //edittext的软键盘的搜索按钮监听
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    //隐藏软键盘
                    if(textView.length()==0||textView.equals("")){
                        Toast.makeText(getActivity(),getActivity().toString()+"输入框为空",Toast.LENGTH_LONG).show();
                        return true;
                    }
                    else {
                        ((InputMethodManager)et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        //实现跳转
//                    Intent intent=new Intent();
//                    intent.setClass(getActivity(),)
//                    startActivity(intent);
                        Toast.makeText(getActivity(),getActivity().toString()+"点击了软键盘的搜索",Toast.LENGTH_LONG).show();
                        return true;
                    }

                }
                return false;
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyGroupActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
      public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
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

    private void setHeadViewPager() {
        //设置 从网络访问得到的字段
        tv_user_name.setText("用户名");
        tv_total.setText(setRichText(tv_total, "0", "0"));
        tv_violate.setText(setRichText(tv_violate, "0", "0"));
        tv_reclaimed.setText(setRichText(tv_reclaimed, "0", "0"));
        tv_reclaiming.setText(setRichText(tv_reclaiming, "0", "0"));

    }

    private SpannableStringBuilder setRichText(View view,String number,String money) {
        //传入 控件名 笔数 金额 返回 具有换行的富文本
        SpannableStringBuilder text=new SpannableStringBuilder();
        if (number.equals("")||number.length()==0){
            number="0";
        }
        if (money.equals("")||money.length()==0){
            money="0";
        }
        int start=number.length()+4;//star=number+3个字符+换行符\n
        int end=start+money.length();
        switch (view.getId()){
            case R.id.tv_total :
                text.append("总计");
                break;
            case R.id.tv_violate :
                text.append("违约");
                break;
            case R.id.tv_reclaimed :
                text.append("已收回");
                start++;end++;
                break;
            case R.id.tv_reclaiming :
                text.append("待收回");
                start++;end++;
                break;
        }
        text.append(number);
        text.append("笔\n");
        text.append(money);
        text.append("元");
        text.setSpan(new ForegroundColorSpan(resources.getColor(R.color.orange_main)), start, end,
                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);//设置颜色
        text.setSpan(new RelativeSizeSpan((float)1.2),start,end,
                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);//设置相对字体大小
        return text;
    }

    private void initHeadViewPager() {
        rl_myInfo= (RelativeLayout) view.findViewById(R.id.rl_myInfo);
        img_headimage= (ImageView) view.findViewById(R.id.img_headimage);
        ibtn_clear= (ImageButton) view.findViewById(R.id.ibtn_clear);
        tv_user_name= (TextView) view.findViewById(R.id.tv_user_name);
        et_search= (EditText) view.findViewById(R.id.et_search);
        tv_total= (TextView) view.findViewById(R.id.tv_total);
        tv_violate= (TextView) view.findViewById(R.id.tv_violate);
        tv_reclaimed= (TextView) view.findViewById(R.id.tv_reclaimed);
        tv_reclaiming= (TextView) view.findViewById(R.id.tv_reclaiming);
    }

    private void initTextView() {
        tv_indicate_all = (TextView) view.findViewById(R.id.tv_indicate_all);
        tv_indicate_car = (TextView) view.findViewById(R.id.tv_indicate_car);
        tv_indicate_house = (TextView) view.findViewById(R.id.tv_indicate_house);
        tv_indicate_loan = (TextView) view.findViewById(R.id.tv_indicate_loan);
        tv_indicate_other = (TextView) view.findViewById(R.id.tv_indicate_other);

        tv_indicate_all.setOnClickListener(new MyOnClickListener(0));
        tv_indicate_car.setOnClickListener(new MyOnClickListener(1));
        tv_indicate_house.setOnClickListener(new MyOnClickListener(2));
        tv_indicate_loan.setOnClickListener(new MyOnClickListener(3));
        tv_indicate_other.setOnClickListener(new MyOnClickListener(4));
    }


    private void initWidth() {
//        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;
        ivBottomLine = (ImageView) view.findViewById(R.id.iv_bottom_line);
        ivBottomLine.setLayoutParams(new LinearLayout.LayoutParams(screenW / 5, 3));


        Log.d("width", "screenW=" + screenW );
        bottomLineWidth=ivBottomLine.getWidth();
        offset = (int) ((screenW / num - bottomLineWidth) / 2);
        position_one = (int)(screenW/5.0);
        position_two=position_one*2;
        position_three=position_one*3;
        position_four=position_one*4;



    }

    private void initViewPager() {
        mPager = (ViewPager)view.findViewById(R.id.viewPager);
        fragmentsList = new ArrayList<Fragment>();

        fragment_all = new ViewPagerFragment();
        fragment_car = new ViewPagerFragment();
        fragment_house = new ViewPagerFragment();
        fragment_loan = new ViewPagerFragment();
        fragment_other = new ViewPagerFragment();

        Bundle bundle_all = new Bundle();
        bundle_all.putString("key", "all");
        fragment_all.setArguments(bundle_all);

        Bundle bundle_car = new Bundle();
        bundle_car.putString("key", "car");
        fragment_car.setArguments(bundle_car);

        Bundle bundle_house = new Bundle();
        bundle_house.putString("key", "house");
        fragment_house.setArguments(bundle_house);

        Bundle bundle_loan = new Bundle();
        bundle_loan.putString("key", "loan");
        fragment_loan.setArguments(bundle_loan);

        Bundle bundle_other = new Bundle();
        bundle_other.putString("key", "other");
        fragment_other.setArguments(bundle_other);

        fragmentsList.add(fragment_all);
        fragmentsList.add(fragment_car);
        fragmentsList.add(fragment_house);
        fragmentsList.add(fragment_loan);
        fragmentsList.add(fragment_other);

        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
        //嵌套在父fragment中的子fragment 得到fragment管理器 必须用getChildFragmentManager
//        mPager.setOffscreenPageLimit(3);//设定加载数量
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);
    }

    public class MyOnClickListener implements View.OnClickListener {
        //5个indicate的点击事件处理
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        //viewpage的事件监听

        @Override
        public void onPageSelected(int index) {
            //监听选中状态
            //实现 各个title的字体颜色变化 和 ivBottomLine的滑动位置变化
            Animation animation = null;
            resetViewPagerHeight(index);
            switch (index) {
                case 0:
                    if (currIndex == 1) {
                        //代码生成滑动动画
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        //改变tv_indicate_car的颜色值，使其没有选中的效果
                        tv_indicate_car.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==2){
                        animation=new TranslateAnimation(position_two,0,0,0);
                        tv_indicate_house.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==3){
                        animation=new TranslateAnimation(position_three,0,0,0);
                        tv_indicate_loan.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==4){
                        animation=new TranslateAnimation(position_four,0,0,0);
                        tv_indicate_other.setTextColor(resources.getColor(R.color.gray_tab));
                    }
                    //改变tv_indicate_all的颜色值，使其有选中的效果
                    tv_indicate_all.setTextColor(resources.getColor(R.color.orange_text));
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_one, 0, 0);
                        tv_indicate_all.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==2){
                        animation=new TranslateAnimation(position_two,position_one,0,0);
                        tv_indicate_house.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==3){
                        animation=new TranslateAnimation(position_three,position_one,0,0);
                        tv_indicate_loan.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==4){
                        animation=new TranslateAnimation(position_four,position_one,0,0);
                        tv_indicate_other.setTextColor(resources.getColor(R.color.gray_tab));
                    }
                    tv_indicate_car.setTextColor(resources.getColor(R.color.orange_text));
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_two, 0, 0);
                        tv_indicate_all.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==1){
                        animation=new TranslateAnimation(position_one,position_two,0,0);
                        tv_indicate_car.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==3){
                        animation=new TranslateAnimation(position_three,position_two,0,0);
                        tv_indicate_loan.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==4){
                        animation=new TranslateAnimation(position_four,position_two,0,0);
                        tv_indicate_other.setTextColor(resources.getColor(R.color.gray_tab));
                    }
                    tv_indicate_house.setTextColor(resources.getColor(R.color.orange_text));
                    break;
                case 3:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_three, 0, 0);
                        tv_indicate_all.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==1){
                        animation=new TranslateAnimation(position_one,position_three,0,0);
                        tv_indicate_car.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==2){
                        animation=new TranslateAnimation(position_two,position_three,0,0);
                        tv_indicate_house.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==4){
                        animation=new TranslateAnimation(position_four,position_three,0,0);
                        tv_indicate_other.setTextColor(resources.getColor(R.color.gray_tab));
                    }
                    tv_indicate_loan.setTextColor(resources.getColor(R.color.orange_text));
                    break;
                case 4:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_four, 0, 0);
                        tv_indicate_all.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==1){
                        animation=new TranslateAnimation(position_one,position_four,0,0);
                        tv_indicate_car.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==2){
                        animation=new TranslateAnimation(position_two,position_four,0,0);
                        tv_indicate_house.setTextColor(resources.getColor(R.color.gray_tab));
                    }else if(currIndex==3){
                        animation=new TranslateAnimation(position_three,position_four,0,0);
                        tv_indicate_loan.setTextColor(resources.getColor(R.color.gray_tab));
                    }
                    tv_indicate_other.setTextColor(resources.getColor(R.color.orange_text));
                    break;
            }
            //记录当前的页面位置
            currIndex = index;
            //动画播放完后，保持结束时的状态
            animation.setFillAfter(true);
            //动画持续时间
            animation.setDuration(180);
            //底栏滑动白线开始动画
            ivBottomLine.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            iv_x=(arg0*screenW+arg2)/5;
//            ivBottomLine.setX(iv_x);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 重新设置viewPager高度
     *
     * @param position
     */
    public void resetViewPagerHeight(int position) {
        //根据position测量当前viewpage里面内容高度 重设viewpage的高度
        View child = mPager.getChildAt(position);
        if (child != null) {
            child.measure(0, 0);
            int h = child.getMeasuredHeight();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPager.getLayoutParams();
            params.height = h ;
            mPager.setLayoutParams(params);
        }
    }


    public class myAsyncTask extends AsyncTask<Void, Void, Void> {
        //模拟后台耗时操作 操作完成 调用viewpager测量高度方法显示内容
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            resetViewPagerHeight(0);
        }

    }



}