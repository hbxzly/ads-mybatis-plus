package com.hbx.ads.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbx.ads.domain.sinoclick.AccountCookie;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.mapper.AccountCookieMapper;
import com.hbx.ads.service.AccountCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AccountCookieServiceImpl implements AccountCookieService {


    @Autowired
    AccountCookieMapper accountCookieMapper;

    @Override
    public String getAccountCookie(String account) {

        QueryWrapper<AccountCookie> accountCookieQueryWrapper = new QueryWrapper<>();
        accountCookieQueryWrapper.eq("account",account);
        return accountCookieMapper.selectOne(accountCookieQueryWrapper).getCookie();
    }
}
