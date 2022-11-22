package com.hbx.ads.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.ads.domain.sinoclick.AccountCookie;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccount;
import com.hbx.ads.mapper.AccountCookieMapper;
import com.hbx.ads.mapper.AccountSystemMapper;
import com.hbx.ads.mapper.AdAccountMapper;
import com.hbx.ads.service.AccountSystemService;
import com.hbx.ads.service.AdAccountService;
import com.hbx.ads.util.EUDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/accountSystem")
public class AccountSystemController {

    @Autowired
    AccountSystemMapper accountSystemMapper;

    @Autowired
    AccountCookieMapper accountCookieMapper;

    @Autowired
    AccountSystemService accountSystemService;

    @Autowired
    AdAccountMapper adAccountMapper;

    @Autowired
    AdAccountService adAccountService;





    @RequestMapping("/find")
    @ResponseBody
    public EUDataGridResult getAccountSystemList(int page, int rows, AccountSystem accountSystem){

        QueryWrapper<AccountSystem> queryWrapper = new QueryWrapper<AccountSystem>();
        if (accountSystem.getClientName() !=null && accountSystem.getClientName()!="" ) {
            queryWrapper.like("client_name", accountSystem.getClientName()).or().like("client_alias",accountSystem.getClientName());
        }
        if (accountSystem.getAccount() !=null && accountSystem.getAccount()!="") {
            queryWrapper.like("account", accountSystem.getAccount());
        }
        if (accountSystem.getBdName() !=null && accountSystem.getBdName()!="") {
            queryWrapper.like("bd_name", accountSystem.getBdName());
        }
        if (accountSystem.getAgency() !=null && accountSystem.getAgency()!="") {
            queryWrapper.like("agency", accountSystem.getAgency());
        }
        if (accountSystem.getClientStatus() !=null && accountSystem.getClientStatus()!="") {
            queryWrapper.eq("client_status", accountSystem.getClientStatus());
        }
        if (accountSystem.getClientBalance() !=0 ) {
            queryWrapper.gt("client_balance", accountSystem.getClientBalance());
        }
        if (accountSystem.getSpendAmount() !=0 ) {
            queryWrapper.gt("spend_amount", accountSystem.getSpendAmount());
        }
        if (accountSystem.getNote() !=null && accountSystem.getNote()!="") {
            queryWrapper.like("note", accountSystem.getNote());
        }
        queryWrapper.orderByDesc("id");
        Page<AccountSystem> accountSystemPage = new Page<>(page ,rows);
        Page<AccountSystem> selectPage = accountSystemMapper.selectPage(accountSystemPage, queryWrapper);
        EUDataGridResult result = new EUDataGridResult();

        result.setRows(selectPage.getRecords());
        result.setTotal(accountSystemPage.getTotal());

        return result;
    }


    @RequestMapping("/findByNameList")
    @ResponseBody
    public EUDataGridResult getAccountSystemListByNameList(int page, int rows, String[] names){


        LambdaQueryWrapper<AccountSystem> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        Page<AccountSystem> accountSystemPage = new Page<>(page ,rows);
        userLambdaQueryWrapper.in(AccountSystem::getClientName,names);
        Page<AccountSystem> selectPage = accountSystemMapper.selectPage(accountSystemPage,userLambdaQueryWrapper);
        EUDataGridResult result = new EUDataGridResult();

        result.setRows(selectPage.getRecords());
        result.setTotal(accountSystemPage.getTotal());

        return result;
    }

    @RequestMapping("/addAccountSystem")
    @ResponseBody
    public void addAccountSystem(@RequestBody AccountSystem accountSystem){

        AccountCookie accountCookie = new AccountCookie();
        accountCookie.setAccount(accountSystem.getAccount());
        accountSystemMapper.insert(accountSystem);
        accountCookieMapper.insert(accountCookie);

    }

    @RequestMapping("/updateAccountSystem")
    @ResponseBody
    public void updateAccountSystem(String[] accounts){

        for (int i = 0; i < accounts.length; i++) {
            QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
            accountSystemQueryWrapper.eq("account",accounts[i]);
            AccountSystem accountSystem = accountSystemMapper.selectOne(accountSystemQueryWrapper);
            accountSystemService.updateAccountSystemBySelenium(accountSystem);
            adAccountService.updateAdAccount(accountSystem);
        }
    }

    @RequestMapping("/updateAccountSystemBasicInfo")
    @ResponseBody
    public void updateAccountSystemBasicInfo(String[] accounts){

        for (int i = 0; i < accounts.length; i++) {
            QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
            accountSystemQueryWrapper.eq("account",accounts[i]);
            accountSystemService.updateAccountSystemBasicInfo(accountSystemMapper.selectOne(accountSystemQueryWrapper));
        }
    }


    @RequestMapping("/editAccountSystem")
    @ResponseBody
    public void editAccountSystem(@RequestBody AccountSystem accountSystem){
        if (accountSystem.getNote().equals("卖出")){
            accountSystemService.updateAccountSystem(accountSystem);
            QueryWrapper<AdAccount> adAccountQueryWrapper = new QueryWrapper<>();
            adAccountQueryWrapper.eq("ad_account_system",accountSystem.getClientName());
            List<AdAccount> adAccountList = adAccountMapper.selectList(adAccountQueryWrapper);
            for (AdAccount adAccount:adAccountList){
                UpdateWrapper<AdAccount>  adAccountUpdateWrapper = new UpdateWrapper<>();
                adAccountUpdateWrapper.eq("id",adAccount.getId())
                        .set("is_Sell","1");
                adAccountMapper.update(null,adAccountUpdateWrapper);
            }
        }else {
            accountSystemService.updateAccountSystem(accountSystem);
        }
    }

    @RequestMapping("/updateAllAccountSystem")
    @ResponseBody
    public String updateAllAccountSystem(){
        List<AccountSystem> accountSystemList = accountSystemMapper.selectList(new QueryWrapper<>());
        for (AccountSystem accountSystem :accountSystemList) {
            if (accountSystem.getIsAble().equals("1")){
                accountSystemService.updateAccountSystemBySelenium(accountSystem);
            }
        }
        return "OK";
    }

    @RequestMapping("/findAdAccountByAccountSystem")
    @ResponseBody
    public List<AdAccount> findAdAccountByAccountSystem(String accountSystem){
        QueryWrapper<AdAccount> adAccountQueryWrapper = new QueryWrapper<>();
        adAccountQueryWrapper.eq("ad_account_system",accountSystem);
        List<AdAccount> adAccountList = adAccountMapper.selectList(adAccountQueryWrapper);
        return adAccountList;
    }


    @RequestMapping("/getBdName")
    @ResponseBody
    public String findBdName(){
//        List<String> strings = accountSystemMapper.searchBdName();
        Map<String,Object> bdNameMap = new HashMap<>();
        /*for (String str : strings) {
            bdNameMap.put(str,str);
        }*/
        bdNameMap.put("11",11);
        bdNameMap.put("22",22);
        bdNameMap.put("33",33);
        bdNameMap.put("44",44);
        System.out.println(bdNameMap);

        return JSONUtils.toJSONString(bdNameMap);
    }

}
