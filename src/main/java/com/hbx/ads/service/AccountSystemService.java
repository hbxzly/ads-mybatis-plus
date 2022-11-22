package com.hbx.ads.service;

import com.hbx.ads.domain.sinoclick.AccountSystem;

import java.util.List;

public interface AccountSystemService {

    void updateAccountSystemBySelenium(AccountSystem accountSystem);

    void updateAccountSystem(AccountSystem accountSystem);

    void updateAccountSystemBasicInfo(AccountSystem accountSystem);

    AccountSystem selectOneAccountSystem(AccountSystem accountSystem);

    List<AccountSystem> getAllAccountSystem();
}
