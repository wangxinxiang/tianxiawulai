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
 ownerheadimg 用户头像
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
public class QueryResultLaolaiItemBean {

    @SerializedName("name")
    private String name;
    @SerializedName("age")
    private String age;
    @SerializedName("cardnum")
    private String cardnum;
    @SerializedName("area")
    private String area;
    @SerializedName("casecount")
    private String casecount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCasecount() {
        return casecount;
    }

    public void setCasecount(String casecount) {
        this.casecount = casecount;
    }
}
