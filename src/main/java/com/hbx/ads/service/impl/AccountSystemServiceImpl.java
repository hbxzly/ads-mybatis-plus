package com.hbx.ads.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hbx.ads.common.SinoClickConstants;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.mapper.AccountSystemMapper;
import com.hbx.ads.service.AccountCookieService;
import com.hbx.ads.service.AccountSystemService;
import com.hbx.ads.util.Sinoclick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountSystemServiceImpl implements AccountSystemService {


    @Autowired
    AccountCookieService accountCookieService;

    @Autowired
    AccountSystemMapper accountSystemMapper;


    @Override
    public void updateAccountSystemBySelenium(AccountSystem accountSystem) {

        String accountCookie = accountCookieService.getAccountCookie(accountSystem.getAccount());

        try {

            String finalLevelIdString = Sinoclick.postRequest(SinoClickConstants.requestUrl.CLIENT_FINAL_LEVEL_POST, accountCookie, SinoClickConstants.requestData.CLIENT_FINAL_LEVEL_RD);
            Map<String, String> finalLevelMap = JSON.parseObject(finalLevelIdString, new TypeReference<HashMap<String, String>>() {});
            Map<String, String> result = JSON.parseObject(finalLevelMap.get("result"), new TypeReference<HashMap<String, String>>() {});
            String userAssetsString = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_USER_ASSETS_POST, accountCookie, SinoClickConstants.requestData.WALLET_BALANCE_RD);
            Map<String, String> userAssetsMap = JSON.parseObject(userAssetsString, new TypeReference<HashMap<String, String>>() {});
            Map<String, String> userAssetsResultMap = JSON.parseObject(userAssetsMap.get("result"), new TypeReference<HashMap<String, String>>() {});
            Map<String, String> walletMap = JSON.parseObject(userAssetsResultMap.get("wallet"), new TypeReference<HashMap<String, String>>() {});
            String overviewString = Sinoclick.postRequest(SinoClickConstants.requestUrl.OVERVIEW_POST, accountCookie, SinoClickConstants.requestData.OVERVIEW_RD);
            Map<String, String> overviewMap = JSON.parseObject(overviewString, new TypeReference<HashMap<String, String>>() {});
            Map<String, String> overviewResultMap = JSON.parseObject(overviewMap.get("result"), new TypeReference<HashMap<String, String>>() {});
            Map<String, String> facebookMap = JSON.parseObject(overviewResultMap.get("facebook"), new TypeReference<HashMap<String, String>>() {});
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = df.format(System.currentTimeMillis());
            UpdateWrapper<AccountSystem> accountSystemUpdateWrapper = new UpdateWrapper<>();
            accountSystemUpdateWrapper.eq("account", accountSystem.getAccount())
                    .set("client_status", result.get("finalLevelId"))
                    .set("client_balance", walletMap.get("balance"))
                    .set("total_count", facebookMap.get("totalCount"))
                    .set("active_count", facebookMap.get("activeCount"))
                    .set("disable_count", facebookMap.get("disableCount"))
                    .set("last_sync_time", currentTime);
            accountSystemMapper.update(null, accountSystemUpdateWrapper);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAccountSystem(AccountSystem accountSystem) {
        UpdateWrapper<AccountSystem> accountSystemUpdateWrapper = new UpdateWrapper<>();
        accountSystemUpdateWrapper.eq("account", accountSystem.getAccount())
                .set("password", accountSystem.getPassword())
                .set("client_alias", accountSystem.getClientAlias())
                .set("agency", accountSystem.getAgency())
                .set("is_able", accountSystem.getIsAble())
                .set("note", accountSystem.getNote());
        accountSystemMapper.update(null, accountSystemUpdateWrapper);
    }

    @Override
    public void updateAccountSystemBasicInfo(AccountSystem accountSystem) {
        String accountCookie = accountCookieService.getAccountCookie(accountSystem.getAccount());

        try {
            String clientInfoString = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_UDESK_CLIENT_INFO_POST, accountCookie, SinoClickConstants.requestData.COMMON_RD);
            Map<String, String> clientInfoMap = JSON.parseObject(clientInfoString, new TypeReference<HashMap<String, String>>() {});
            AccountSystem accountSystemTemp = JSONObject.parseObject(clientInfoMap.get("result"), AccountSystem.class);
            UpdateWrapper<AccountSystem> accountSystemUpdateWrapper = new UpdateWrapper<>();
            accountSystemUpdateWrapper.eq("account", accountSystem.getAccount())
                    .set("client_name", accountSystemTemp.getClientName())
                    .set("email", accountSystemTemp.getEmail())
                    .set("user_id", accountSystemTemp.getUserId())
                    .set("bd_name", accountSystemTemp.getBdName());
            accountSystemMapper.update(null, accountSystemUpdateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccountSystem selectOneAccountSystem(AccountSystem accountSystem) {
        QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
        accountSystemQueryWrapper.eq("account",accountSystem.getAccount())
                .or().eq("client_name",accountSystem.getClientName())
                .or().eq("client_alias",accountSystem.getClientAlias());
        return accountSystemMapper.selectOne(accountSystemQueryWrapper);
    }

    @Override
    public List<AccountSystem> getAllAccountSystem() {
        return accountSystemMapper.selectList(new QueryWrapper<>());
    }
}
