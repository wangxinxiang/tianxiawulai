package com.example.txwl_first.Util;

/**
 * Created by licheng on 17/7/15.
 */
public class DataVeri {

    public static Boolean isNaN(String array){
        if(array.matches("^[0-9]*$"))
            return true;
        else
        {
            TXWLApplication.getInstance().showTextToast("只能输入纯数字");
            return false;
        }
    }

    public static Boolean isAge(String array){
        if("".equals(array)){
            TXWLApplication.getInstance().showTextToast("年龄不能为空");
            return false;
        }else {
            if(Integer.valueOf(array)>=16&&Integer.valueOf(array)<=110){
                return true;
            }else{
                TXWLApplication.getInstance().showTextToast("年龄输入有误");
                return false;
            }

        }
    }


    public static Boolean isMobileNum(String array){
        if("".equals(array)){
            TXWLApplication.getInstance().showTextToast("手机号不能为空");
            return false;
        }else {
            if(array.matches("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$")){
                return true;
            }else{
                TXWLApplication.getInstance().showTextToast("手机号格式错误");
                return false;
            }

        }
    }

    public static Boolean isIDNum(String array){
        if("".equals(array)){
            TXWLApplication.getInstance().showTextToast("身份证号码不能为空");
            return false;
        }else {
            if(array.matches("/^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$/")){
                return true;
            }else {
                TXWLApplication.getInstance().showTextToast("身份证号码格式错误");
                return false;
            }
        }
    }

    public static Boolean stringIsNull(String str, String name) {
        if ("".equals(str)) {
            TXWLApplication.getInstance().showTextToast(name + "不能为空");
            return true;
        } else {
            return false;
        }
    }

    public static String getLoan_type(String i) {
        switch (i) {
            case "1":
                return "车贷";
            case "2":
                return "房贷";
            case "3":
                return "信用贷";
            default:
                return "其他";
        }
    }

}
