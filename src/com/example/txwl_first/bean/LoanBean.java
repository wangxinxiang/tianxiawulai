package com.example.txwl_first.bean;


public class LoanBean {
    //欠款人姓名
    private String own_user_name;
    //欠款人头像
    private String own_user_head_image;
    //欠款人电话
    private String own_user_phone;
    //欠款人所在地
    private String own_user_place;
    //欠款描述
    private String debt_descrip;
    //欠款金额
    private String debt_money;
    //欠款状态
    private String debt_type;
    //悬赏人姓名
    private String reward_user_name;
    //悬赏金额
    private String reward_money;
    //悬赏人电话
    private String reward_user_phone;
    //逾期天数
    private String over_day;

    public LoanBean(String own_user_name,String own_user_phone,String own_user_place,String debt_descrip,String debt_money,String debt_type){}{
        this.own_user_name = own_user_name;
        this.own_user_phone = own_user_phone;
        this.own_user_place = own_user_place;
        this.debt_descrip = debt_descrip;
        this.debt_money = debt_money;
        this.debt_type = debt_type;
    }


    public String getDebt_descrip() {
        return debt_descrip;
    }

    public void setDebt_descrip(String debt_descrip) {
        this.debt_descrip = debt_descrip;
    }

    public String getDebt_type() {
        return debt_type;
    }

    public void setDebt_type(String debt_type) {
        this.debt_type = debt_type;
    }

    public String getOver_day() {
        return over_day;
    }

    public void setOver_day(String over_day) {
        this.over_day = over_day;
    }

    public String getReward_money() {
        return reward_money;
    }

    public void setReward_money(String reward_money) {
        this.reward_money = reward_money;
    }

    public String getReward_user_name() {
        return reward_user_name;
    }

    public void setReward_user_name(String reward_user_name) {
        this.reward_user_name = reward_user_name;
    }

    public String getReward_user_phone() {
        return reward_user_phone;
    }

    public void setReward_user_phone(String reward_user_phone) {
        this.reward_user_phone = reward_user_phone;
    }

    public String getOwn_user_name() {
        return own_user_name;
    }

    public void setOwn_user_name(String own_user_name) {
        this.own_user_name = own_user_name;
    }

    public String getOwn_user_phone() {
        return own_user_phone;
    }

    public void setOwn_user_phone(String own_user_phone) {
        this.own_user_phone = own_user_phone;
    }

    public String getOwn_user_place() {
        return own_user_place;
    }

    public void setOwn_user_place(String own_user_place) {
        this.own_user_place = own_user_place;
    }

    public String getDebt_money() {
        return debt_money;
    }

    public void setDebt_money(String debt_money) {
        this.debt_money = debt_money;
    }

    public String getOwn_user_head_image() {
        return own_user_head_image;
    }

    public void setOwn_user_head_image(String own_user_head_image) {
        this.own_user_head_image = own_user_head_image;
    }
}
