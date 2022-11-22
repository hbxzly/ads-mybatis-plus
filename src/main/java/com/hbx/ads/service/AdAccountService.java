package com.hbx.ads.service;

import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccount;
import com.hbx.ads.util.EUDataGridResult;

public interface AdAccountService {

    AdAccount selectOneAdAccount(String account);

    void insertOneAdAccount(AdAccount adAccount);

    void updateAdAccount(AccountSystem accountSystem);

    EUDataGridResult queryAdAccountByAdAccounts(int page, int rows, String[] adAccounts);

    EUDataGridResult queryAdAccountByAdAccountsForRecharge(int page, int rows, String[] adAccounts);
}
