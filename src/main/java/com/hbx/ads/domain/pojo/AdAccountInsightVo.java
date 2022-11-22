package com.hbx.ads.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AdAccountInsightVo {

    private String channelId;

    private String channelName;

    private String adAccountId;

    private String spendAmount;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date minSpendDay;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date maxSpendDay;

    private String remark;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getAdAccountId() {
        return adAccountId;
    }

    public void setAdAccountId(String adAccountId) {
        this.adAccountId = adAccountId;
    }

    public String getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(String spendAmount) {
        this.spendAmount = spendAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getMinSpendDay() {
        return minSpendDay;
    }

    public void setMinSpendDay(Date minSpendDay) {
        this.minSpendDay = minSpendDay;
    }

    public Date getMaxSpendDay() {
        return maxSpendDay;
    }

    public void setMaxSpendDay(Date maxSpendDay) {
        this.maxSpendDay = maxSpendDay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AdAccountInsightVo{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", adAccountId='" + adAccountId + '\'' +
                ", spendAmount='" + spendAmount + '\'' +
                ", createTime=" + createTime +
                ", minSpendDay=" + minSpendDay +
                ", maxSpendDay=" + maxSpendDay +
                ", remark='" + remark + '\'' +
                '}';
    }
}
