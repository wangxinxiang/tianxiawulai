package com.example.txwl_first.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2015/7/19.
 */
public class TXWLApplication extends Application{

    private List<Activity> activityList = new LinkedList<Activity>();
    private Stack<Activity> activityStack=new Stack<Activity>();
    private static TXWLApplication mApplication;

    public synchronized static TXWLApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }


    //显示简单的对话框
    public void showAlertDialog(Context context, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create();
        alertDialog.setMessage(message);
        alertDialog.setButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();

            }
        });
        alertDialog.show();

    }

    //得到屏幕高度
    public int getScreenHeight(Context context) {

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
    //得到屏幕宽度
    public int getScreenWidth(Context context) {

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 显示一个text的toast,且最多只显示一个toast的时间
     *
     */
    public void showTextToast(String msg) {

        Toast toast = null;

        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), msg,
                    Toast.LENGTH_SHORT);

        } else {

            toast.setText(msg);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showErrorConnected(Exception ex) {
        showTextToast("网络错误");
        Log.d("Exception -->", "" + ex);
    }

    //检测网络
    public boolean isconnected( ) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo ni : infos) {
                    if (ni.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    public void backTop() {
        for (int i = 1; i < activityList.size(); i++) {
            activityList.get(i).finish();
        }
    }

    //压入activity栈，在需要管理的activity的onCreate中添加
    public void pushStack(Activity activity){
        activityStack.push(activity);
    }

    //遍历栈，全部finish
    public void finishStack(){
        if (!activityStack.empty()){
            for (Activity activity:activityStack){
                activity.finish();
            }
        }
    }

    //在activity 中back键按下或者返回时调用 确保栈中的activity唯一
    public void popStack(Activity activity){
        if(!activityStack.empty()){
            if(activityStack.peek()==activity){
                activityStack.pop();
            }
        }
    }
}
