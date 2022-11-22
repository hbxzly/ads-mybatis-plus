package com.hbx.ads.domain.sinoclick;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("wallet_recharge_record")
public class WalletRechargeRecord {

    @TableField("change_type")
    private String changeType;
    @TableField("action_type")
    private String actionType;
    @TableField("usd_amount")
    private double usdAmount;
    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date createTime;
    @TableField("change_desc")
    private String changeDesc;
    @TableField("nick_name")
    private String nickName;
    @TableField("mobile")
    private String mobile;
    @TableField("company_name")
    private String companyName;

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public double getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(double usdAmount) {
        this.usdAmount = usdAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getChangeDesc() {
        return changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "RechargeRecord{" +
                "changeType='" + changeType + '\'' +
                ", actionType='" + actionType + '\'' +
                ", usdAmount='" + usdAmount + '\'' +
                ", createTime=" + createTime +
                ", changeDesc='" + changeDesc + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
