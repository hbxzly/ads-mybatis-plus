package com.hbx.ads.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hbx.ads.common.SinoClickConstants;
import com.hbx.ads.domain.pojo.AdAccountInsightVo;
import com.hbx.ads.domain.sinoclick.AccountCookie;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccount;
import com.hbx.ads.mapper.AccountCookieMapper;
import com.hbx.ads.mapper.AccountSystemMapper;
import com.hbx.ads.mapper.AdAccountMapper;
import com.hbx.ads.service.AccountCookieService;
import com.hbx.ads.service.AccountSystemService;
import com.hbx.ads.util.SeleniumUtil;
import com.hbx.ads.util.Sinoclick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TaskJobDemo {


    @Autowired
    AccountCookieMapper accountCookieMapper;

    @Autowired
    AccountSystemMapper accountSystemMapper;

    @Autowired
    AccountSystemService accountSystemService;

    @Autowired
    AccountCookieService accountCookieService;

    @Autowired
    AdAccountMapper adAccountMapper;

    //每隔5秒隔行一次  cron里面写七子表达式，注解默认单线程阻塞运行
    @Scheduled(cron = "0 29 3 ? * *")
    public void  task1() throws InterruptedException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        System.out.println(date + ":定时任务TaskJobTest");
        List<AccountSystem> accountSystemList = accountSystemMapper.selectList(new QueryWrapper<>());
        for (AccountSystem accountSystem:accountSystemList){
            if (accountSystem.getIsAble().equals("1")){
                String cookie = SeleniumUtil.getCookie(accountSystem);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = df.format(System.currentTimeMillis());
                UpdateWrapper<AccountCookie> accountCookieUpdateWrapper = new UpdateWrapper<>();
                accountCookieUpdateWrapper.eq("account",accountSystem.getAccount())
                        .set("cookie",cookie)
                        .set("update_time",currentTime);
                accountCookieMapper.update(null,accountCookieUpdateWrapper);
                try {
                    String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_LIST_POST, cookie, SinoClickConstants.requestData.GET_LIST_POST_RD);
                    Map<String,String> map = JSON.parseObject(s, new TypeReference<HashMap<String, String>>() {});
                    Map<String,String> mapResult = JSON.parseObject(map.get("result"), new TypeReference<HashMap<String, String>>() {});
                    List<AdAccount> adAccountList = JSONObject.parseArray(mapResult.get("result"), AdAccount.class);
                    for (AdAccount adAccount : adAccountList) {
                        adAccount.setAdAccountId(adAccount.getAdAccountId().replace("act_", ""));
                        adAccount.setAdAccountSystem(accountSystem.getClientName());
                        adAccount.setAdAccountSystemAlias(accountSystem.getClientAlias());
                        adAccount.setAdAccountSystemStatus(accountSystem.getClientStatus());
                        adAccount.setAdAccountSystemBdName(accountSystem.getBdName());
                        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        String Time=df1.format(new Date());// new Date()为获取当前系统时间
                        Date date1 = df1.parse(Time);
                        adAccount.setUpdateTime(date1);
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
                }
            }
            accountSystemService.updateAccountSystemBySelenium(accountSystem);

        }

    }
    @Scheduled(cron = "0 29 6 ? * *")
    public void  task2() throws InterruptedException {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.plusDays(-8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDateFormat = endDate.format(formatter);
        String startDateFormat = startDate.format(formatter);

        String GET_AD_ACCOUNT_INSIGHT_LIST_POST_RD = "{\"pageSize\": \"50\",\"pageNum\": \"1\",\"endDate\": \""+endDateFormat+"\",\"startDate\": \""+startDateFormat+"\"}";

        QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<AccountSystem>();

        List<AccountSystem> accountSystems = accountSystemMapper.selectList(accountSystemQueryWrapper);

        for (AccountSystem accountSystem:accountSystems){

            String accountCookie = accountCookieService.getAccountCookie(accountSystem.getAccount());

            try {
                String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_AD_ACCOUNT_INSIGHT_LIST_POST, accountCookie, GET_AD_ACCOUNT_INSIGHT_LIST_POST_RD) ;
                Map<String, String> map = JSON.parseObject(s, new TypeReference<HashMap<String, String>>() {});
                Map<String, String> mapResult = JSON.parseObject(map.get("result"), new TypeReference<HashMap<String, String>>() {});
                List<AdAccountInsightVo> adAccountInsightVoList = JSONObject.parseArray(mapResult.get("result"), AdAccountInsightVo.class);
                BigDecimal bigDecimal = new BigDecimal(0.00);
                for (AdAccountInsightVo adAccountInsightVo : adAccountInsightVoList) {
                    String spendAmountStr = adAccountInsightVo.getSpendAmount().replace("$", "");
                    double spendAmountDouble = Double.parseDouble(spendAmountStr);
                    UpdateWrapper<AdAccount> adAccountUpdateWrapper = new UpdateWrapper<>();
                    adAccountUpdateWrapper.eq("id", adAccountInsightVo.getAdAccountId());
                    adAccountUpdateWrapper.set("spend_amount", spendAmountDouble);
                    adAccountMapper.update(null, adAccountUpdateWrapper);
                    BigDecimal spendAmount = new BigDecimal(Double.toString(spendAmountDouble));
                    bigDecimal = bigDecimal.add(spendAmount);
                }
                UpdateWrapper<AccountSystem> accountSystemUpdateWrapper = new UpdateWrapper<>();
                accountSystemUpdateWrapper.eq("account", accountSystem.getAccount());
                accountSystemUpdateWrapper.set("spend_amount", bigDecimal.doubleValue());
                accountSystemMapper.update(null, accountSystemUpdateWrapper);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
