package com.example.txwl_first.Util;

/**
 * Created by licheng on 17/7/15.
 */
public class DataVeri {
    public static Boolean isAge(String array){
        if(array.matches("^([0-9]|[0-9]{2}|100)$")){
            return true;
        }else
            return false;
    }


    public static Boolean isMobileNum(String array){
        if(array.matches("^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$")){
            return true;
        }else
            return false;
    }

    public static Boolean isIDNum(String array){
        if(array.matches("/^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$/")){
            return true;
        }else {
            return false;
        }
    }


    public static Boolean isMoney(String array){
        if(array.matches("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$")){
            return true;
        }else {
            return false;
        }
    }

    public static Boolean isYear(String array){
        if(array.matches("^(([1-9]\\d{0,1})|(0))(\\.\\d{1,2})?$")){
            return true;
        }else {
            return false;
        }
    }


}
