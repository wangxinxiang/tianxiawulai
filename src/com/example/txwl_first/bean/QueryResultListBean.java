package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/7/20.
 *userid 用户ID
 name 姓名
 registid 登记者ID
 mobile 手机号码
 province 地址
 companyname公司名称
 accout借款金额
 repaydate 应该还款日期
 owneridimg 用户头像
 description 详细描述
 status2 用户状态
 date 用户还款信息
 registtype 登记类型:1 车贷 ,2 房贷,3 信用贷 4 其他
 loandate 借款日期
 registcompany 登记人所属公司
 contactname 悬赏人姓名
 contactmobile 悬赏人手机号
 rewardmoney 悬赏金额
 */
public class QueryResultListBean {

    @SerializedName("userid")
    private String userid;
    @SerializedName("name")
    private String name;
    @SerializedName("registid")
    private String registid;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("province")
    private String province;
    @SerializedName("companyname")
    private String companyname;
    @SerializedName("account")
    private String account;
    @SerializedName("repaydate")
    private String repaydate;
    @SerializedName("owneridimg")
    private String owneridimg;
    @SerializedName("description")
    private String description;
    @SerializedName("status2")
    private String status2;
    @SerializedName("date")
    private String date;
    @SerializedName("registtype")
    private String registtype;
    @SerializedName("loandate")
    private String loandate;
    @SerializedName("registcompany")
    private String registcompany;
    @SerializedName("contactname")
    private String contactname;
    @SerializedName("contactmobile")
    private String contactmobile;
    @SerializedName("rewardmoney")
    private String rewardmoney;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistid() {
        return registid;
    }

    public void setRegistid(String registid) {
        this.registid = registid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(String repaydate) {
        this.repaydate = repaydate;
    }

    public String getOwneridimg() {
        return owneridimg;
    }

    public void setOwneridimg(String owneridimg) {
        this.owneridimg = owneridimg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegisttype() {
        return registtype;
    }

    public void setRegisttype(String registtype) {
        this.registtype = registtype;
    }

    public String getLoandate() {
        return loandate;
    }

    public void setLoandate(String loandate) {
        this.loandate = loandate;
    }

    public String getRegistcompany() {
        return registcompany;
    }

    public void setRegistcompany(String registcompany) {
        this.registcompany = registcompany;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactmobile() {
        return contactmobile;
    }

    public void setContactmobile(String contactmobile) {
        this.contactmobile = contactmobile;
    }

    public String getRewardmoney() {
        return rewardmoney;
    }

    public void setRewardmoney(String rewardmoney) {
        this.rewardmoney = rewardmoney;
    }
}
