package com.hbx.ads.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/systemView")
public class SystemViewController {


    @RequestMapping("/list")
    public String goAccountSystemList(){
        return "accountSystem";
    }

    @RequestMapping("/adAccountList")
    public String goAdAccountList(){
        return "adAccount";
    }

    @RequestMapping("/add")
    public String goAddAccountSystem(){
        return "addAccountSystem";
    }

    @RequestMapping("/edit")
    public String goEditAccountSystem(){
        return "editAccountSystem";
    }

    @RequestMapping("/accountCookieList")
    public String goAccountCookie(){
        return "accountCookie";
    }

    @RequestMapping("/rechargeRecordList")
    public String goRechargeRecord(){
        return "walletRechargeRecord";
    }

    @RequestMapping("/adAccountRecharge")
    public String goAdAccountRecharge(){
        return "adAccountRecharge";
    }

    @RequestMapping("/adAccountRechargeRecord")
    public String goAdAccountRechargeRecord(){
        return "adAccountRechargeRecord";
    }

    @RequestMapping("/test")
    public String goAccountSystemList_Test(){
        return "test";
    }



}
