package com.example.txwl_first.Util;

/**
 * Created by Administrator on 2015/7/19.
 */
public class Url {

    public static String  URL = "http://txwlapi.201494.com/";

    public static String  UPLOADURL = URL + "Default/uploadimage";      //上传图片接口

    public static String  QUERY_URL = URL + "Default/GetUserListByway";      //查询主页面

    public static String  GetLaoLaiSecondList_URL = URL + "Default/GetLaoLaiSecondList";      //老赖查询结果二级列表

    public static String  GetLaoLaiDetail_URL = URL + "Default/GetLaoLaiDetail";      //查询老赖详细信息

    public static String  QUERY_DETAIL_URL = URL + "Default/GetUserDetail";      //查询详细信息

    public static String  QueryAllBlackPerson_URL = URL + "Default/QueryAllBlackPerson";      //获取所有黑名单接口
    public static String  QueryBlackPersonFromWay_URL = URL + "Default/QueryBlackPersonFromWay";
     //根据手机号或者姓名或者身份证搜索黑名单信息

    public static String REGISTER_URL = URL + "Default/Regist";         //登记

    public static String PERSONAL_URL = URL + "Default/UserGet";         //获取个人信息

    public static String MobileValidateSend_URL = URL + "Default/MobileValidateSend";   //发送验证码

    public static String UserRegist_URL = URL + "Default/UserRegist";         //用户注册

    public static String UserLogin_URL = URL + "Default/UserLogin";         //用户登录

    public static String PERSONAL_MODIFY_URL = URL + "Default/UserUpdate";          //修改个人信息

    public static String GROUP_URL = URL + "Default/QueryTeamPerson";            //我的团队

    public static String ADD_GROUP_URL = URL + "Default/AddTeamPerson";         //添加团队成员

    public static String GetMyInfo_URL = URL + "Default/GetMyInfo";             //我

    public static String ADD_BLACK_URL = URL + "Default/UpdateBlackPerson";       //添加黑名单

    public static String REPORT_URL = URL + "Default/AddReport";            //举报

    public static String GetBankInfo_URL = URL + "Default/GetBankInfo";            //取银行卡信息
    public static String AddBankInfo_URL = URL + "Default/AddBankInfo";            //添加绑定银行卡
    public static String AddRecharge_URL = URL + "Default/AddRecharge";            //保存充值记录
    public static String RechargeOK_URL = URL + "Default/RechargeOK";            //充值成功操作

    //贝付
    public static String Partner="201508061047514248";

}
