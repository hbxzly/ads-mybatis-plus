package com.hbx.ads.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AdOperation {


    private String  adOperationAccountId;
    private String  adOperationAccountName;
    private String  adOperationAccountPage;
    private String  adOperationAccountPageId;
    private String  adOperationAccountAgency;
    private String  adOperationAccountType;
    private String  adOperationStatus;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date adOperationCreateTime;
    private Date adOperationEndTime;
    private String  adOperationCreateLocation;
    private String  adOperationClient;
    private String  adOperationAllot;
    private String  adOperationOperator;
    private String  adOperationAccountCard;
    private double adOperationAccountRechargeAmount;
    private double adOperationAccountUsedAmount;
    private double adOperationAccountBalance;
    private String  adOperationNote;

    public String getAdOperationAccountType() {
        return adOperationAccountType;
    }

    public void setAdOperationAccountType(String adOperationAccountType) {
        this.adOperationAccountType = adOperationAccountType;
    }

    public Date getAdOperationEndTime() {
        return adOperationEndTime;
    }

    public void setAdOperationEndTime(Date adOperationEndTime) {
        this.adOperationEndTime = adOperationEndTime;
    }

    public String getAdOperationAccountCard() {
        return adOperationAccountCard;
    }

    public void setAdOperationAccountCard(String adOperationAccountCard) {
        this.adOperationAccountCard = adOperationAccountCard;
    }

    public String getAdOperationAccountPageId() {
        return adOperationAccountPageId;
    }

    public void setAdOperationAccountPageId(String adOperationAccountPageId) {
        this.adOperationAccountPageId = adOperationAccountPageId;
    }

    public double getAdOperationAccountRechargeAmount() {
        return adOperationAccountRechargeAmount;
    }

    public void setAdOperationAccountRechargeAmount(double adOperationAccountRechargeAmount) {
        this.adOperationAccountRechargeAmount = adOperationAccountRechargeAmount;
    }

    public double getAdOperationAccountUsedAmount() {
        return adOperationAccountUsedAmount;
    }

    public void setAdOperationAccountUsedAmount(double adOperationAccountUsedAmount) {
        this.adOperationAccountUsedAmount = adOperationAccountUsedAmount;
    }

    public double getAdOperationAccountBalance() {
        return adOperationAccountBalance;
    }

    public void setAdOperationAccountBalance(double adOperationAccountBalance) {
        this.adOperationAccountBalance = adOperationAccountBalance;
    }

    public String getAdOperationOperator() {
        return adOperationOperator;
    }

    public void setAdOperationOperator(String adOperationOperator) {
        this.adOperationOperator = adOperationOperator;
    }


    public String getAdOperationAccountId() {
        return adOperationAccountId;
    }

    public void setAdOperationAccountId(String adOperationAccountId) {
        this.adOperationAccountId = adOperationAccountId;
    }

    public String getAdOperationAccountName() {
        return adOperationAccountName;
    }

    public void setAdOperationAccountName(String adOperationAccountName) {
        this.adOperationAccountName = adOperationAccountName;
    }

    public String getAdOperationAccountPage() {
        return adOperationAccountPage;
    }

    public void setAdOperationAccountPage(String adOperationAccountPage) {
        this.adOperationAccountPage = adOperationAccountPage;
    }

    public String getAdOperationAccountAgency() {
        return adOperationAccountAgency;
    }

    public void setAdOperationAccountAgency(String adOperationAccountAgency) {
        this.adOperationAccountAgency = adOperationAccountAgency;
    }

    public String getAdOperationStatus() {
        return adOperationStatus;
    }

    public void setAdOperationStatus(String adOperationStatus) {
        this.adOperationStatus = adOperationStatus;
    }

    public Date getAdOperationCreateTime() {
        return adOperationCreateTime;
    }

    public void setAdOperationCreateTime(Date adOperationCreateTime) {
        this.adOperationCreateTime = adOperationCreateTime;
    }

    public String getAdOperationCreateLocation() {
        return adOperationCreateLocation;
    }

    public void setAdOperationCreateLocation(String adOperationCreateLocation) {
        this.adOperationCreateLocation = adOperationCreateLocation;
    }

    public String getAdOperationClient() {
        return adOperationClient;
    }

    public void setAdOperationClient(String adOperationClient) {
        this.adOperationClient = adOperationClient;
    }

    public String getAdOperationAllot() {
        return adOperationAllot;
    }

    public void setAdOperationAllot(String adOperationAllot) {
        this.adOperationAllot = adOperationAllot;
    }

    public String getAdOperationNote() {
        return adOperationNote;
    }

    public void setAdOperationNote(String adOperationNote) {
        this.adOperationNote = adOperationNote;
    }


    @Override
    public String toString() {
        return "AdOperation{" +
                "adOperationAccountId='" + adOperationAccountId + '\'' +
                ", adOperationAccountName='" + adOperationAccountName + '\'' +
                ", adOperationAccountPage='" + adOperationAccountPage + '\'' +
                ", adOperationAccountAgency='" + adOperationAccountAgency + '\'' +
                ", adOperationStatus='" + adOperationStatus + '\'' +
                ", adOperationCreateTime=" + adOperationCreateTime +
                ", adOperationCreateLocation='" + adOperationCreateLocation + '\'' +
                ", adOperationClient='" + adOperationClient + '\'' +
                ", adOperationAllot='" + adOperationAllot + '\'' +
                ", adOperationOperator='" + adOperationOperator + '\'' +
                ", adOperationNote='" + adOperationNote + '\'' +
                '}';
    }
}
