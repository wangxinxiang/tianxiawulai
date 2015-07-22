package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/7/21.
 * registid 登记者ID
 name  姓名
 sex   性别
 age   年龄
 mobile 手机号码
 companyname 公司名称
 personid   身份证号
 carid      车牌号
 province   省份
 city       城市
 address    详细地址
 account    借款金额
 loanday   借款日期
 repayday  还款日期
 annualrate 年利率
 description 详细说明
 appearanceimg  房子(车子)外观图片
 appearancedesc 房子(车子)备注
 goodsidimg     发动机(房产证)号码图片
 goodsiddesc    发动机(房产证)备注
 owneridimg     所有人证件照片
 owneriddesc    所有人证件备注
 ownerheadimg   所有人头像图片
 ownerheaddesc  所有人头像备注
 contractimg    合同图片
 contractdesc   合同备注
 registtype     登记类型:1 车贷 ,2 房贷,3 信用贷 4 其他
 userid         登记人用户id（如果是登录过的用户则需要填）
 registdate     登记时间
 status         登记者状态
 realname   经办人
 */
public class QueryDetailResultBean {

    @SerializedName("registid")
    private String registid;
    @SerializedName("name")
    private String name;
    @SerializedName("sex")
    private String sex;
    @SerializedName("age")
    private String age;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("companyname")
    private String companyname;
    @SerializedName("personid")
    private String personid;
    @SerializedName("carid")
    private String carid;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("account")
    private String account;
    @SerializedName("repayday")
    private String repayday;
    @SerializedName("loanday")
    private String loanday;
    @SerializedName("annualrate")
    private String annualrate;
    @SerializedName("description")
    private String description;
    @SerializedName("appearanceimg")
    private String appearanceimg;
    @SerializedName("appearancedesc")
    private String appearancedesc;
    @SerializedName("goodsidimg")
    private String goodsidimg;
    @SerializedName("goodsiddesc")
    private String goodsiddesc;
    @SerializedName("owneridimg")
    private String owneridimg;
    @SerializedName("owneriddesc")
    private String owneriddesc;
    @SerializedName("ownerheadimg")
    private String ownerheadimg;
    @SerializedName("ownerheaddesc")
    private String ownerheaddesc;
    @SerializedName("contractimg")
    private String contractimg;
    @SerializedName("contractdesc")
    private String contractdesc;
    @SerializedName("registtype")
    private String registtype;
    @SerializedName("userid")
    private String userid;
    @SerializedName("registdate")
    private String registdate;
    @SerializedName("status")
    private String status;


    public String getRegistid() {
        return registid;
    }

    public void setRegistid(String registid) {
        this.registid = registid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRepayday() {
        return repayday;
    }

    public void setRepayday(String repayday) {
        this.repayday = repayday;
    }

    public String getLoanday() {
        return loanday;
    }

    public void setLoanday(String loanday) {
        this.loanday = loanday;
    }

    public String getAnnualrate() {
        return annualrate;
    }

    public void setAnnualrate(String annualrate) {
        this.annualrate = annualrate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppearanceimg() {
        return appearanceimg;
    }

    public void setAppearanceimg(String appearanceimg) {
        this.appearanceimg = appearanceimg;
    }

    public String getAppearancedesc() {
        return appearancedesc;
    }

    public void setAppearancedesc(String appearancedesc) {
        this.appearancedesc = appearancedesc;
    }

    public String getGoodsidimg() {
        return goodsidimg;
    }

    public void setGoodsidimg(String goodsidimg) {
        this.goodsidimg = goodsidimg;
    }

    public String getGoodsiddesc() {
        return goodsiddesc;
    }

    public void setGoodsiddesc(String goodsiddesc) {
        this.goodsiddesc = goodsiddesc;
    }

    public String getOwneridimg() {
        return owneridimg;
    }

    public void setOwneridimg(String owneridimg) {
        this.owneridimg = owneridimg;
    }

    public String getOwneriddesc() {
        return owneriddesc;
    }

    public void setOwneriddesc(String owneriddesc) {
        this.owneriddesc = owneriddesc;
    }

    public String getOwnerheadimg() {
        return ownerheadimg;
    }

    public void setOwnerheadimg(String ownerheadimg) {
        this.ownerheadimg = ownerheadimg;
    }

    public String getOwnerheaddesc() {
        return ownerheaddesc;
    }

    public void setOwnerheaddesc(String ownerheaddesc) {
        this.ownerheaddesc = ownerheaddesc;
    }

    public String getContractimg() {
        return contractimg;
    }

    public void setContractimg(String contractimg) {
        this.contractimg = contractimg;
    }

    public String getContractdesc() {
        return contractdesc;
    }

    public void setContractdesc(String contractdesc) {
        this.contractdesc = contractdesc;
    }

    public String getRegisttype() {
        return registtype;
    }

    public void setRegisttype(String registtype) {
        this.registtype = registtype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRegistdate() {
        return registdate;
    }

    public void setRegistdate(String registdate) {
        this.registdate = registdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
