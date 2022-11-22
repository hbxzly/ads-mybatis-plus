package com.hbx.ads.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hbx.ads.common.SinoClickConstants;
import com.hbx.ads.domain.pojo.AdAccountRechargeVo;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccount;
import com.hbx.ads.mapper.AdAccountMapper;
import com.hbx.ads.service.AccountCookieService;
import com.hbx.ads.service.AccountSystemService;
import com.hbx.ads.service.AdAccountService;
import com.hbx.ads.util.EUDataGridResult;
import com.hbx.ads.util.Sinoclick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdAccountServiceImpl implements AdAccountService {

    @Autowired
    AdAccountMapper adAccountMapper;

    @Autowired
    AccountSystemService accountSystemService;

    @Autowired
    AccountCookieService accountCookieService;

    @Override
    public AdAccount selectOneAdAccount(String account) {

        QueryWrapper<AdAccount> adAccountQueryWrapper = new QueryWrapper<>();
        adAccountQueryWrapper.eq("account",account);
        return adAccountMapper.selectOne(adAccountQueryWrapper);
    }

    @Override
    public void insertOneAdAccount(AdAccount adAccount) {
        adAccountMapper.insert(adAccount);
    }

    @Override
    public void updateAdAccount(AccountSystem accountSystem) {

        AccountSystem accountSystemT = accountSystemService.selectOneAccountSystem(accountSystem);
        String accountCookie = accountCookieService.getAccountCookie(accountSystemT.getAccount());

        try {
            String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_LIST_POST, accountCookie, SinoClickConstants.requestData.GET_LIST_POST_RD);
            Map<String,String> map = JSON.parseObject(s, new TypeReference<HashMap<String, String>>() {});
            Map<String,String> mapResult = JSON.parseObject(map.get("result"), new TypeReference<HashMap<String, String>>() {});
            List<AdAccount> adAccountList = JSONObject.parseArray(mapResult.get("result"), AdAccount.class);
            for (AdAccount adAccount : adAccountList) {
                adAccount.setAdAccountId(adAccount.getAdAccountId().replace("act_", ""));
                adAccount.setAdAccountSystem(accountSystem.getClientName());
                adAccount.setAdAccountSystemAlias(accountSystem.getClientAlias());
                adAccount.setAdAccountSystemStatus(accountSystem.getClientStatus());
                adAccount.setAdAccountSystemBdName(accountSystem.getBdName());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String Time=df.format(new Date());// new Date()为获取当前系统时间
                Date date = df.parse(Time);
                adAccount.setUpdateTime(date);
                QueryWrapper<AdAccount> adAccountQueryWrapper = new QueryWrapper<>();
                adAccountQueryWrapper.eq("id",adAccount.getId());
                if (adAccountMapper.selectOne(adAccountQueryWrapper)!=null){
                    adAccountMapper.updateById(adAccount);
                }else {
                    adAccountMapper.insert(adAccount);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("异常后台："+accountCookie);
        }

    }


    @Override
    public EUDataGridResult queryAdAccountByAdAccounts(int page, int rows, String[] adAccounts) {

        PageHelper.startPage(page,rows);
        List<AdAccount> adAccountList = adAccountMapper.customQueryAdAccountByAdAccounts(page, rows, adAccounts);
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(adAccountList);
        System.out.println(adAccountList);
        //创建页面对象并初始化
        PageInfo<AdAccount> pageInfo = new PageInfo<>(adAccountList);
        //初始化对象，总条数
        result.setTotal(pageInfo.getTotal());

        return result;
    }

    @Override
    public EUDataGridResult queryAdAccountByAdAccountsForRecharge(int page, int rows, String[] adAccounts) {

        PageHelper.startPage(page,rows);
        List<AdAccount> adAccountList = adAccountMapper.customQueryAdAccountByAdAccounts(page, rows, adAccounts);
        List<AdAccountRechargeVo> adAccountRechargeVoList = new ArrayList<>();
        for (AdAccount adAccount :adAccountList) {
            AdAccountRechargeVo adAccountRechargeVo = new AdAccountRechargeVo();
            adAccountRechargeVo.setId(adAccount.getId());
            adAccountRechargeVo.setAdAccountSystem(adAccount.getAdAccountSystem());
            adAccountRechargeVo.setRechargeAmount("");
            adAccountRechargeVoList.add(adAccountRechargeVo);
        }
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(adAccountRechargeVoList);
        //创建页面对象并初始化
        PageInfo<AdAccountRechargeVo> pageInfo = new PageInfo<>(adAccountRechargeVoList);

        //初始化对象，总条数
        result.setTotal(pageInfo.getTotal());

        return result;
    }
}
