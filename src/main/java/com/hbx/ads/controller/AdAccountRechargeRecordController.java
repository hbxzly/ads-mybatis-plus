package com.hbx.ads.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccountRechargeRecord;
import com.hbx.ads.service.AccountSystemService;
import com.hbx.ads.service.AdAccountRechargeRecordService;
import com.hbx.ads.util.EUDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/adAccountRechargeRecord")
public class AdAccountRechargeRecordController {


    @Autowired
    AccountSystemService accountSystemService;

    @Autowired
    AdAccountRechargeRecordService adAccountRechargeRecordService;

    @RequestMapping("/find")
    @ResponseBody
    public EUDataGridResult findAdAccountRechargeRecord(int page, int rows , AdAccountRechargeRecord adAccountRechargeRecord){

        Page<AdAccountRechargeRecord> selectPage = adAccountRechargeRecordService.getPageAdAccountRechargeRecord(page, rows, adAccountRechargeRecord);
        EUDataGridResult result = new EUDataGridResult();

        result.setRows(selectPage.getRecords());
        result.setTotal(selectPage.getTotal());

        return result;
    }

    @RequestMapping("/insertAdAccountRechargeRecord")
    public void insertAdAccountRechargeRecord() throws InterruptedException, ParseException {

        List<AccountSystem> accountSystemList = accountSystemService.getAllAccountSystem();
        for (AccountSystem accountSystem:accountSystemList){
            adAccountRechargeRecordService.insertAdAccountRechargeRecord(accountSystem);
        }
    }


    @RequestMapping("/updateAdAccountRechargeRecord")
    @ResponseBody
    public String updateAdAccountRechargeRecord(@RequestBody List<AdAccountRechargeRecord> adAccountRechargeRecordList){

        for (AdAccountRechargeRecord adAccountRechargeRecord: adAccountRechargeRecordList){
            adAccountRechargeRecordService.updateAdAccountRechargeRecord(adAccountRechargeRecord);
        }


        return "OK";
    }

}
