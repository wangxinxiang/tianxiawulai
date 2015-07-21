package com.example.txwl_first.Util;

/**
 * Created by licheng on 17/7/15.
 */
public class DataVeri {

    public static Boolean isNaN(String array){
        if(array.matches("^[0-9]*$"))
            return true;
        else return false;
    }

    public static Boolean isAge(String array){
        if("".equals(array)){
            TXWLApplication.getInstance().showTextToast("年龄不能为空");
            return false;
        }else {
            if(Integer.valueOf(array)>=16&&Integer.valueOf(array)<=110){
                return true;
            }else
                return false;
        }
    }


    public static Boolean isMobileNum(String array){
        if("".equals(array)){
            TXWLApplication.getInstance().showTextToast("手机号不能为空");
            return false;
        }else {
            if(array.matches("^((13[0-9])|(17[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$")){
                return true;
            }else
                return false;
        }
    }

    public static Boolean isIDNum(String array){
        if("".equals(array)){
            return false;
        }else {
            if(array.matches("/^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$/")){
                return true;
            }else {
                return false;
            }
        }
    }
}
