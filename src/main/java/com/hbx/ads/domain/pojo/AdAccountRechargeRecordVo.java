package com.hbx.ads.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class AdAccountRechargeRecordVo {

    private String tradeId;
    private String tid;
    private String payMethod;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date payTime;
    private String tradeStatusName;
    private TradeAmount tradeAmount;
    private List<TradeDetailList> tradeDetailList;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getTradeStatusName() {
        return tradeStatusName;
    }

    public void setTradeStatusName(String tradeStatusName) {
        this.tradeStatusName = tradeStatusName;
    }

    public TradeAmount getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(TradeAmount tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public List<TradeDetailList> getTradeDetailList() {
        return tradeDetailList;
    }

    public void setTradeDetailList(List<TradeDetailList> tradeDetailList) {
        this.tradeDetailList = tradeDetailList;
    }

    @Override
    public String toString() {
        return "AdAccountRechargeRecordVo{" +
                "tradeId='" + tradeId + '\'' +
                ", tid='" + tid + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", tradeStatusName='" + tradeStatusName + '\'' +
                ", tradeAmount=" + tradeAmount +
                ", tradeDetailList=" + tradeDetailList +
                '}';
    }

    public class TradeAmount{

        private double usdAmount;
        private double cnyAmount;

        public double getUsdAmount() {
            return usdAmount;
        }

        public void setUsdAmount(double usdAmount) {
            this.usdAmount = usdAmount;
        }

        public double getCnyAmount() {
            return cnyAmount;
        }

        public void setCnyAmount(double cnyAmount) {
            this.cnyAmount = cnyAmount;
        }

        @Override
        public String toString() {
            return "TradeAmount{" +
                    "usdAmount=" + usdAmount +
                    ", cnyAmount=" + cnyAmount +
                    '}';
        }
    }

    public class TradeDetailList{

        private String tradeDetailTypeDesc;
        private String channelId;
        private String channelAccountId;

        public String getTradeDetailTypeDesc() {
            return tradeDetailTypeDesc;
        }

        public void setTradeDetailTypeDesc(String tradeDetailTypeDesc) {
            this.tradeDetailTypeDesc = tradeDetailTypeDesc;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelAccountId() {
            return channelAccountId;
        }

        public void setChannelAccountId(String channelAccountId) {
            this.channelAccountId = channelAccountId;
        }

        @Override
        public String toString() {
            return "TradeDetailList{" +
                    "tradeDetailTypeDesc='" + tradeDetailTypeDesc + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", channelAccountId='" + channelAccountId + '\'' +
                    '}';
        }
    }
}
