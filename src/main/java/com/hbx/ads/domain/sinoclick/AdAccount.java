package com.hbx.ads.domain.sinoclick;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("ad_account")
public class AdAccount {


    @TableField("id")
    private String id;

    @TableField("account_status")
    private String accountStatus;

    @TableField("transfer_and_rest_status")
    private String transferAndRestStatus;

    @TableField("ad_account_name")
    private String adAccountName;

    @TableField("balance")
    private double balance;

    @TableField("spend_amount")
    private double spendAmount;

    @TableField("ad_account_id")
    private String adAccountId;

    @TableField("recharge_status")
    private String rechargeStatus;

    @TableField("pause_action_status")
    private String pauseActionStatus;

    @TableField("balance_warning_status")
    private String balanceWarningStatus;

    @TableField("disable_warning_status")
    private String disableWarningStatus;

    @TableField("ad_account_system")
    private String adAccountSystem;

    @TableField("ad_account_system_alias")
    private String adAccountSystemAlias;

    @TableField("ad_account_system_status")
    private String adAccountSystemStatus;

    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    @TableField("ad_account_system_bd_name")
    private String adAccountSystemBdName;

    @TableField("is_sell")
    public String isSell;

    @TableField("receiver")
    public String receiver;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getTransferAndRestStatus() {
        return transferAndRestStatus;
    }

    public void setTransferAndRestStatus(String transferAndRestStatus) {
        this.transferAndRestStatus = transferAndRestStatus;
    }

    public String getAdAccountName() {
        return adAccountName;
    }

    public void setAdAccountName(String adAccountName) {
        this.adAccountName = adAccountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAdAccountId() {
        return adAccountId;
    }

    public void setAdAccountId(String adAccountId) {
        this.adAccountId = adAccountId;
    }

    public String getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(String rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    public String getPauseActionStatus() {
        return pauseActionStatus;
    }

    public void setPauseActionStatus(String pauseActionStatus) {
        this.pauseActionStatus = pauseActionStatus;
    }

    public String getBalanceWarningStatus() {
        return balanceWarningStatus;
    }

    public void setBalanceWarningStatus(String balanceWarningStatus) {
        this.balanceWarningStatus = balanceWarningStatus;
    }

    public String getDisableWarningStatus() {
        return disableWarningStatus;
    }

    public void setDisableWarningStatus(String disableWarningStatus) {
        this.disableWarningStatus = disableWarningStatus;
    }

    public double getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(double spendAmount) {
        this.spendAmount = spendAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdAccountSystem() {
        return adAccountSystem;
    }

    public void setAdAccountSystem(String adAccountSystem) {
        this.adAccountSystem = adAccountSystem;
    }

    public String getAdAccountSystemStatus() {
        return adAccountSystemStatus;
    }

    public void setAdAccountSystemStatus(String adAccountSystemStatus) {
        this.adAccountSystemStatus = adAccountSystemStatus;
    }

    public String getAdAccountSystemAlias() {
        return adAccountSystemAlias;
    }

    public void setAdAccountSystemAlias(String adAccountSystemAlias) {
        this.adAccountSystemAlias = adAccountSystemAlias;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAdAccountSystemBdName() {
        return adAccountSystemBdName;
    }

    public void setAdAccountSystemBdName(String adAccountSystemBdName) {
        this.adAccountSystemBdName = adAccountSystemBdName;
    }

    public String getIsSell() {
        return isSell;
    }

    public void setIsSell(String isSell) {
        this.isSell = isSell;
    }

    @Override
    public String toString() {
        return "AdAccount{" +
                "id='" + id + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", transferAndRestStatus='" + transferAndRestStatus + '\'' +
                ", adAccountName='" + adAccountName + '\'' +
                ", balance=" + balance +
                ", spendAmount=" + spendAmount +
                ", adAccountId='" + adAccountId + '\'' +
                ", rechargeStatus='" + rechargeStatus + '\'' +
                ", pauseActionStatus='" + pauseActionStatus + '\'' +
                ", balanceWarningStatus='" + balanceWarningStatus + '\'' +
                ", disableWarningStatus='" + disableWarningStatus + '\'' +
                ", adAccountSystem='" + adAccountSystem + '\'' +
                ", adAccountSystemAlias='" + adAccountSystemAlias + '\'' +
                ", adAccountSystemStatus='" + adAccountSystemStatus + '\'' +
                ", updateTime=" + updateTime +
                ", adAccountSystemBdName='" + adAccountSystemBdName + '\'' +
                ", isSell='" + isSell + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
