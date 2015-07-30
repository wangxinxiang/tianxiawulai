package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/7/21.
 */
public class GetLaolaiListItemBean {

    @SerializedName("laolaiid")
    private String laolaiid;
    @SerializedName("casetime")
    private String casetime;
    @SerializedName("casetitle")
    private String casetitle;

    public String getLaolaiid() {
        return laolaiid;
    }

    public void setLaolaiid(String laolaiid) {
        this.laolaiid = laolaiid;
    }

    public String getCasetime() {
        return casetime;
    }

    public void setCasetime(String casetime) {
        this.casetime = casetime;
    }

    public String getCasetitle() {
        return casetitle;
    }

    public void setCasetitle(String casetitle) {
        this.casetitle = casetitle;
    }
}
