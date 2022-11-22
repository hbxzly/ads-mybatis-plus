package com.hbx.ads.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.ads.domain.sinoclick.AccountCookie;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.mapper.AccountCookieMapper;
import com.hbx.ads.mapper.AccountSystemMapper;
import com.hbx.ads.util.EUDataGridResult;
import com.hbx.ads.util.SeleniumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/accountCookie")
public class AccountCookieController {

    @Autowired
    AccountCookieMapper accountCookieMapper;

    @Autowired
    AccountSystemMapper accountSystemMapper;

    @RequestMapping("/find")
    @ResponseBody
    public EUDataGridResult getAccountSystemListByNameList(int page, int rows, String account){

        QueryWrapper<AccountCookie> accountCookieQueryWrapper = new QueryWrapper<>();
        Page<AccountCookie> accountCookiePage = new Page<>(page ,rows);
        if(account != "" && account !=null){
            accountCookieQueryWrapper.eq("account",account);
        }
        Page<AccountCookie> selectPage = accountCookieMapper.selectPage(accountCookiePage,accountCookieQueryWrapper);
        EUDataGridResult result = new EUDataGridResult();

        result.setRows(selectPage.getRecords());
        result.setTotal(accountCookiePage.getTotal());

        return result;
    }


    @RequestMapping("/updateAllAccountSystemCookie")
    @ResponseBody
    public String updateAllAccountSystemCookie() throws InterruptedException {
        List<AccountSystem> accountSystemList = accountSystemMapper.selectList(new QueryWrapper<>());
        for (AccountSystem accountSystem:accountSystemList){
            if (accountSystem.getIsAble().equals("1")){
                String cookie = SeleniumUtil.getCookie(accountSystem);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = df.format(System.currentTimeMillis());
                UpdateWrapper<AccountCookie> accountCookieUpdateWrapper = new UpdateWrapper<>();
                accountCookieUpdateWrapper.eq("account",accountSystem.getAccount())
                        .set("cookie",cookie)
                        .set("update_time",currentTime);
                accountCookieMapper.update(null,accountCookieUpdateWrapper);
            }
        }
        return "OK";
    }

    @RequestMapping("/updateOneAccountSystemCookie")
    @ResponseBody
    public String  updateOneAccountSystemCookie(String account) throws InterruptedException {
        QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
        accountSystemQueryWrapper.eq("account",account);
        AccountSystem accountSystem = accountSystemMapper.selectOne(accountSystemQueryWrapper);
        String cookie = SeleniumUtil.getCookie(accountSystem);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(System.currentTimeMillis());
        UpdateWrapper<AccountCookie> accountCookieUpdateWrapper = new UpdateWrapper<>();
        accountCookieUpdateWrapper.eq("account",account)
                .set("cookie",cookie)
                .set("update_time",currentTime);
        accountCookieMapper.update(null,accountCookieUpdateWrapper);
        return "OK";
    }


}
