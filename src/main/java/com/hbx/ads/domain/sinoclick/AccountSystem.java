package com.hbx.ads.domain.sinoclick;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("account_system")
public class AccountSystem {


    @TableId(type = IdType.AUTO)
    @TableField("id")
    private int id;
    @TableField("client_name")
    private String clientName;
    @TableField("user_id")
    private String userId;
    @TableField("account")
    private String account;
    @TableField("email")
    private String email;
    @TableField("password")
    private String password;
    @TableField("bd_name")
    private String bdName;
    @TableField("total_count")
    private int totalCount;
    @TableField("active_count")
    private int activeCount;
    @TableField("disable_count")
    private int disableCount;
    @TableField("spend_amount")
    private double spendAmount;
    @TableField("client_status")
    private String clientStatus;
    @TableField("client_balance")
    private double clientBalance;
    @TableField("client_alias")
    private String clientAlias;
    @TableField("agency")
    private String agency;
    @TableField("last_sync_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastSyncTime;
    @TableField("note")
    private String note;
    @TableField("is_able")
    private String isAble;

    public String getIsAble() {
        return isAble;
    }

    public void setIsAble(String isAble) {
        this.isAble = isAble;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBdName() {
        return bdName;
    }

    public void setBdName(String bdName) {
        this.bdName = bdName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public double getClientBalance() {
        return clientBalance;
    }

    public void setClientBalance(double clientBalance) {
        this.clientBalance = clientBalance;
    }

    public String getClientAlias() {
        return clientAlias;
    }

    public void setClientAlias(String clientAlias) {
        this.clientAlias = clientAlias;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setDisableCount(int disableCount) {
        this.disableCount = disableCount;
    }

    public int getDisableCount() {
        return disableCount;
    }

    public double getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(double spendAmount) {
        this.spendAmount = spendAmount;
    }

    public Date getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "AccountSystem{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", userId='" + userId + '\'' +
                ", account='" + account + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bdName='" + bdName + '\'' +
                ", totalCount=" + totalCount +
                ", activeCount=" + activeCount +
                ", disableCount=" + disableCount +
                ", spendAmount=" + spendAmount +
                ", clientStatus='" + clientStatus + '\'' +
                ", clientBalance=" + clientBalance +
                ", clientAlias='" + clientAlias + '\'' +
                ", agency='" + agency + '\'' +
                ", lastSyncTime=" + lastSyncTime +
                ", note='" + note + '\'' +
                ", isAble='" + isAble + '\'' +
                '}';
    }
}
