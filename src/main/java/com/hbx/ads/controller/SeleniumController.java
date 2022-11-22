package com.hbx.ads.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbx.ads.domain.pojo.AdAccountRechargeVo;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.mapper.AccountSystemMapper;
import com.hbx.ads.service.SeleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/selenium")
public class SeleniumController {

    @Autowired
    AccountSystemMapper accountSystemMapper;

    @Autowired
    SeleniumService seleniumService;



    @RequestMapping("/openChrome")
    @ResponseBody
    public void openChrome(@RequestBody List<AccountSystem> accountSystemList){

        for (AccountSystem accountSystem:accountSystemList) {
            seleniumService.loginAccountSystem(accountSystem);
        }

    }


    @RequestMapping("/adAccountRecharge")
    @ResponseBody
    public void adAccountRecharge(@RequestBody List<AdAccountRechargeVo> adAccountRechargeVoList) throws InterruptedException {

        for (AdAccountRechargeVo adAccountRechargeVo :adAccountRechargeVoList) {
            QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
            accountSystemQueryWrapper.eq("client_name",adAccountRechargeVo.getAdAccountSystem());
            AccountSystem accountSystem = accountSystemMapper.selectOne(accountSystemQueryWrapper);
            seleniumService.adAccountRecharge(accountSystem,adAccountRechargeVo);
        }





    }

    @RequestMapping("/closeChrome")
    public void closeChrome(){
        seleniumService.closeChrome();
    }


}
