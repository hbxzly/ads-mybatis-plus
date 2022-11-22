package com.hbx.ads.domain.sinoclick;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("account_cookie")
public class AccountCookie {

    @TableId(type = IdType.AUTO)
    @TableField("id")
    private int id;
    @TableField("account")
    private String account;
    @TableField("cookie")
    private String cookie;
    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AccountCookie{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", cookie='" + cookie + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
