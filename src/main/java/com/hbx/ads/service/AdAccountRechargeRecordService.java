package com.hbx.ads.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccountRechargeRecord;

import java.text.ParseException;
import java.util.List;

public interface AdAccountRechargeRecordService {


    void insertAdAccountRechargeRecord(AccountSystem accountSystem) throws InterruptedException, ParseException;

    List<AdAccountRechargeRecord> getAllAdAccountRechargeRecord();

    Page<AdAccountRechargeRecord> getPageAdAccountRechargeRecord(int page, int rows , AdAccountRechargeRecord adAccountRechargeRecord);

    void updateAdAccountRechargeRecord(AdAccountRechargeRecord adAccountRechargeRecord);



}
