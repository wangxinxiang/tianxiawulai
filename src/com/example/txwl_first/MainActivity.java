package com.example.txwl_first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.txwl_first.Util.TXWLApplication;


/*
   主activity 管理4个主要fragment
   实现方式 使用transaction的 add hide show 三种方式避免每次tab切换 新实例化fragment
 */
public class MainActivity extends FragmentActivity {
    private static String TAG="FragmentActivity";
    public final static int num = 4 ;

    private Fragment meFragment,searchFragment,editFragment,menuFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private RadioGroup radioGroup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.main_activity);

        initView();
        initListener();
        setSelect(0);//设置默认启动界面 为第一个 查询界面


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                if (meFragment!=null){
                    Log.d(TAG + "onActivityResult", "onSuccess login");
                    FragmentTransaction ft=fragmentManager.beginTransaction();
                    ft.remove(meFragment);
                    meFragment=null;
                    ft.commit();
                    setSelect(3);
                }
//                TXWLApplication.getInstance().showTextToast("登录成功");
                break;
            case RESULT_CANCELED:
                Log.d(TAG+"onActivityResult","canceled login");
                break;

        }

    }

    private void setSelect(int position) {
        fragmentManager = getSupportFragmentManager();//得到管理器 引用V4包时用 getSupport 否则直接get得到管理器
        transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);//传入事物管理 隐藏所有非空的fragment
        switch (position)
        {
            //根据选中位置 实例化或者show 该fragment
            case 0:
                if (searchFragment==null){
                    searchFragment=new SearchFragment();
                    transaction.add(R.id.content,searchFragment);
                }else{
                    transaction.show(searchFragment);
                }
                break;

            case 1:
                if (editFragment==null){
                    editFragment=new EditFragment();
                    transaction.add(R.id.content,editFragment);
                }else{
                    transaction.show(editFragment);
                }
                break;

            case 2:
                if (menuFragment==null){
                    menuFragment=new MenuFragment();
                    transaction.add(R.id.content,menuFragment);
                }else{
                    transaction.show(menuFragment);
                }
                break;

            case 3:
                if (meFragment==null){
                    meFragment=new MeFragment();
                    transaction.add(R.id.content,meFragment);
                    Log.d("meFragment","add");
                }else{
                    transaction.show(meFragment);
                    Log.d("meFragment", "show");
                }
                break;

            default:
                break;
        }
        transaction.commit();//提交事务
    }

    private void hideFragment(FragmentTransaction transaction)
    {
        if (meFragment != null)
        {
            transaction.hide(meFragment);
        }
        if (menuFragment != null)
        {
            transaction.hide(menuFragment);
        }
        if (editFragment != null)
        {
            transaction.hide(editFragment);
        }
        if (searchFragment != null)
        {
            transaction.hide(searchFragment);
        }
    }

    private void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_search:
                       setSelect(0);
                        break;
                    case R.id.rbtn_edit:
                        setSelect(1);
                        break;
                    case R.id.rbtn_menu:
                        setSelect(2);
                        break;
                    case R.id.rbtn_me:
                        setSelect(3);
                        break;
                }
            }
        });
    }

    private void initView() {
        radioGroup = (RadioGroup)findViewById(R.id.main_radiogroup);
        ((RadioButton)radioGroup.findViewById(R.id.rbtn_search)).setChecked(true);//设置默认选中search按钮
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
