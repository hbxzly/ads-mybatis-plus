package com.hbx.ads.service;

import com.hbx.ads.domain.pojo.AdAccountRechargeVo;
import com.hbx.ads.domain.sinoclick.AccountSystem;

public interface SeleniumService {

    void loginAccountSystem(AccountSystem accountSystem);

    void adAccountRecharge(AccountSystem accountSystem, AdAccountRechargeVo adAccountRechargeVo) throws InterruptedException;

    void closeChrome();
}
