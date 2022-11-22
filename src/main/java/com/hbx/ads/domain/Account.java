package com.hbx.ads.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Account {


    private Integer number;
    private String accountId;
    private String accountName;
    private String accountPage;
    private String accountPageId;
    private String accountPageStatus;
    private String accountDomain;
    private String accountStatus;
    private String accountLicense;
    private String accountCreateLocation;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date accountAllotTime;
    private String accountAgency;
    private String accountType;
    private double rechargeAmount;
    private double usedAmount;
    private double accountBalance;
    private String accountClient;
    private String accountAllot;
    private String accountNote;


    public String getAccountPageId() {
        return accountPageId;
    }

    public void setAccountPageId(String accountPageId) {
        this.accountPageId = accountPageId;
    }

    public String getAccountPageStatus() {
        return accountPageStatus;
    }

    public void setAccountPageStatus(String accountPageStatus) {
        this.accountPageStatus = accountPageStatus;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPage() {
        return accountPage;
    }

    public void setAccountPage(String accountPage) {
        this.accountPage = accountPage;
    }

    public String getAccountDomain() {
        return accountDomain;
    }

    public void setAccountDomain(String accountDomain) {
        this.accountDomain = accountDomain;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountLicense() {
        return accountLicense;
    }

    public void setAccountLicense(String accountLicense) {
        this.accountLicense = accountLicense;
    }

    public String getAccountCreateLocation() {
        return accountCreateLocation;
    }

    public void setAccountCreateLocation(String accountCreateLocation) {
        this.accountCreateLocation = accountCreateLocation;
    }

    public Date getAccountAllotTime() {
        return accountAllotTime;
    }

    public void setAccountAllotTime(Date accountAllotTime) {
        this.accountAllotTime = accountAllotTime;
    }

    public String getAccountAgency() {
        return accountAgency;
    }

    public void setAccountAgency(String accountAgency) {
        this.accountAgency = accountAgency;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountClient() {
        return accountClient;
    }

    public void setAccountClient(String accountClient) {
        this.accountClient = accountClient;
    }

    public String getAccountAllot() {
        return accountAllot;
    }

    public void setAccountAllot(String accountAllot) {
        this.accountAllot = accountAllot;
    }

    public String getAccountNote() {
        return accountNote;
    }

    public void setAccountNote(String accountNote) {
        this.accountNote = accountNote;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountPage='" + accountPage + '\'' +
                ", accountPageId='" + accountPageId + '\'' +
                ", accountPageStatus='" + accountPageStatus + '\'' +
                ", accountDomain='" + accountDomain + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", accountLicense='" + accountLicense + '\'' +
                ", accountCreateLocation='" + accountCreateLocation + '\'' +
                ", accountAllotTime=" + accountAllotTime +
                ", accountAgency='" + accountAgency + '\'' +
                ", accountClient='" + accountClient + '\'' +
                ", accountAllot='" + accountAllot + '\'' +
                ", accountNote='" + accountNote + '\'' +
                '}';
    }
}
