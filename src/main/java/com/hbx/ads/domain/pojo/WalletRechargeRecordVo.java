package com.hbx.ads.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class WalletRechargeRecordVo {


    private String changeType;

    private String actionType;

    private ChangeAmount changeAmount;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date createTime;

    private String changeDesc;

    private String nickName;

    private String mobile;

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

    public ChangeAmount getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(ChangeAmount changeAmount) {
        this.changeAmount = changeAmount;
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


    public static class ChangeAmount{

       private double usdAmount;
       private String usdFormate;

        public double getUsdAmount() {
            return usdAmount;
        }

        public void setUsdAmount(double usdAmount) {
            this.usdAmount = usdAmount;
        }

        public String getUsdFormate() {
            return usdFormate;
        }

        public void setUsdFormate(String usdFormate) {
            this.usdFormate = usdFormate;
        }

        @Override
        public String toString() {
            return "ChangeAmount{" +
                    "usdAmount=" + usdAmount +
                    ", usdFormate='" + usdFormate + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RechargeRecordVo{" +
                "changeType='" + changeType + '\'' +
                ", actionType='" + actionType + '\'' +
                ", changeAmount=" + changeAmount +
                ", createTime=" + createTime +
                ", changeDesc='" + changeDesc + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
