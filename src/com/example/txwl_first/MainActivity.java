package com.example.txwl_first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.txwl_first.Util.Constant;
import com.example.txwl_first.Util.TXWLApplication;

import java.util.Timer;
import java.util.TimerTask;


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

    /**
     * 是否退出标志位
     */
    private static Boolean isQuit = false;
    /**
     * 计时器
     */
    private final Timer timer = new Timer();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main_activity);
        TXWLApplication.getInstance().addActivity(this);
        initView();
        initListener();
        setSelect(0);//设置默认启动界面 为第一个 查询界面

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Constant.LOGIN_CHANGE:
                if (meFragment!=null){
                    Log.d(TAG + "onActivityResult", "onSuccess login");
                    FragmentTransaction ft=fragmentManager.beginTransaction();
                    ft.remove(meFragment);
                    meFragment=null;
                    ft.commit();
                    setSelect(3);
                    RadioButton button = (RadioButton) radioGroup.findViewById(R.id.rbtn_me);
                    button.setChecked(true);
                }
//                TXWLApplication.getInstance().showTextToast("登录成功");
                break;
            case Constant.LOGIN_FAILE:
                Log.d(TAG+"onActivityResult","canceled login");
//                setSelect(3);
//                RadioButton button = (RadioButton) radioGroup.findViewById(R.id.rbtn_me);
//                button.setChecked(true);true
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    // 双击推出程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isQuit == false) {
                isQuit = true;
                TXWLApplication.getInstance().showTextToast("再按一次退出");
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                TXWLApplication.getInstance().exit();//遍历activity管理器 挨个finish
            }
        }
        return true;
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
