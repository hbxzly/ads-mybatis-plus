package com.hbx.ads.domain.sinoclick;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("ad_account_recharge_record")
public class AdAccountRechargeRecord {

    /**交易ID*/
    @TableField("trade_id")
    private String tradeId;
    /**订单ID*/
    @TableField("tid")
    private String tid;
    /**支付方式*/
    @TableField("pay_method")
    private String payMethod;
    /**创建时间*/
    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date createTime;
    /**支付时间*/
    @TableField("pay_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date payTime;
    /**订单状态*/
    @TableField("trade_status_name")
    private String tradeStatusName;
    /**订单类型*/
    @TableField("trade_detail_type_desc")
    private String tradeDetailTypeDesc;
    /**美金总数*/
    @TableField("usd_amount")
    private double usdAmount;
    /**人民币总数*/
    @TableField("cny_amount")
    private double cnyAmount;
    /**钱包支付美金*/
    @TableField("wallet_usd_amount")
    private double walletUsdAmount;
    /**钱包支付人民币*/
    @TableField("wallet_cny_amount")
    private double walletCnyAmount;
    /**其它支付美金*/
    @TableField("trade_sub_usd_amount")
    private double tradeSubUsdAmount;
    /**其它支付人民币*/
    @TableField("trade_sub_cny_amount")
    private double tradeSubCnyAmount;
    /**汇率*/
    @TableField("exchange_rate")
    private String exchangeRate;
    /**账户ID*/
    @TableField("channel_account_id")
    private String channelAccountId;
    /**账户类型*/
    @TableField("channel_id")
    private String channelId;
    /**后台名称*/
    @TableField("account_system")
    private String accountSystem;

    @TableField("account_system_is_sell")
    private String accountSystemIsSell;

    @TableField("note")
    private String note;


    @TableField("receiver")
    private String receiver;

    @TableField("pay_card")
    private String payCard;

    public String getPayCard() {
        return payCard;
    }

    public void setPayCard(String payCard) {
        this.payCard = payCard;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public String getTradeStatusName() {
        return tradeStatusName;
    }

    public void setTradeStatusName(String tradeStatusName) {
        this.tradeStatusName = tradeStatusName;
    }

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

    public double getWalletUsdAmount() {
        return walletUsdAmount;
    }

    public void setWalletUsdAmount(double walletUsdAmount) {
        this.walletUsdAmount = walletUsdAmount;
    }

    public double getWalletCnyAmount() {
        return walletCnyAmount;
    }

    public void setWalletCnyAmount(double walletCnyAmount) {
        this.walletCnyAmount = walletCnyAmount;
    }

    public double getTradeSubUsdAmount() {
        return tradeSubUsdAmount;
    }

    public void setTradeSubUsdAmount(double tradeSubUsdAmount) {
        this.tradeSubUsdAmount = tradeSubUsdAmount;
    }

    public double getTradeSubCnyAmount() {
        return tradeSubCnyAmount;
    }

    public void setTradeSubCnyAmount(double tradeSubCnyAmount) {
        this.tradeSubCnyAmount = tradeSubCnyAmount;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getChannelAccountId() {
        return channelAccountId;
    }

    public void setChannelAccountId(String channelAccountId) {
        this.channelAccountId = channelAccountId;
    }

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

    public String getAccountSystem() {
        return accountSystem;
    }

    public void setAccountSystem(String accountSystem) {
        this.accountSystem = accountSystem;
    }

    public String getAccountSystemIsSell() {
        return accountSystemIsSell;
    }

    public void setAccountSystemIsSell(String accountSystemIsSell) {
        this.accountSystemIsSell = accountSystemIsSell;
    }

    @Override
    public String toString() {
        return "AdAccountRechargeRecord{" +
                "tradeId='" + tradeId + '\'' +
                ", tid='" + tid + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", tradeStatusName='" + tradeStatusName + '\'' +
                ", tradeDetailTypeDesc='" + tradeDetailTypeDesc + '\'' +
                ", usdAmount=" + usdAmount +
                ", cnyAmount=" + cnyAmount +
                ", walletUsdAmount=" + walletUsdAmount +
                ", walletCnyAmount=" + walletCnyAmount +
                ", tradeSubUsdAmount=" + tradeSubUsdAmount +
                ", tradeSubCnyAmount=" + tradeSubCnyAmount +
                ", exchangeRate='" + exchangeRate + '\'' +
                ", channelAccountId='" + channelAccountId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", accountSystem='" + accountSystem + '\'' +
                ", accountSystemIsSell='" + accountSystemIsSell + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
