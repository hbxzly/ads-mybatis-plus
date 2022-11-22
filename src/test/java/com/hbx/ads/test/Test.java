package com.hbx.ads.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hbx.ads.common.SinoClickConstants;
import com.hbx.ads.domain.pojo.AdAccountInsightVo;
import com.hbx.ads.domain.pojo.AdAccountRechargeRecordVo;
import com.hbx.ads.domain.pojo.WalletRechargeRecordVo;
import com.hbx.ads.domain.sinoclick.AccountCookie;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccountRechargeRecord;
import com.hbx.ads.domain.sinoclick.WalletRechargeRecord;
import com.hbx.ads.domain.sinoclick.AdAccount;
import com.hbx.ads.mapper.*;
import com.hbx.ads.service.AccountSystemService;
import com.hbx.ads.service.AdAccountRechargeRecordService;
import com.hbx.ads.util.SeleniumUtil;
import com.hbx.ads.util.Sinoclick;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class Test {

    @Autowired
    AccountSystemMapper accountSystemMapper;

    @Autowired
    AccountCookieMapper accountCookieMapper;

    @Autowired
    AdAccountMapper adAccountMapper;

    @Autowired
    WalletRechargeRecordMapper walletRechargeRecordMapper;

    @Autowired
    AccountSystemService accountSystemService;

    @Autowired
    AdAccountRechargeRecordMapper adAccountRechargeRecordMapper;

    @Autowired
    AdAccountRechargeRecordService adAccountRechargeRecordService;



    /**获得账户清零记录*/
    @org.junit.Test
    public void test1() throws InterruptedException {

        QueryWrapper<AccountCookie> accountCookieQueryWrapper = new QueryWrapper<>();
        List<AccountCookie> accountCookies = accountCookieMapper.selectList(accountCookieQueryWrapper);
        for (AccountCookie accountCookie:accountCookies){
            try{
                String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_RECHARGE_RECORD_LIST_POST, accountCookie.getCookie(), SinoClickConstants.requestData.GET_RECHARGE_RECORD_LIST_POST_RD);
                Map<String,String> map = JSON.parseObject(s, new TypeReference<HashMap<String, String>>() {});
                Map<String,String> mapResult = JSON.parseObject(map.get("result"), new TypeReference<HashMap<String, String>>() {});
                List<WalletRechargeRecordVo> rechargeRecordVoList = JSONObject.parseArray(mapResult.get("result"), WalletRechargeRecordVo.class);
                for(WalletRechargeRecordVo rechargeRecordVo:rechargeRecordVoList){
                    WalletRechargeRecord walletRechargeRecord = new WalletRechargeRecord();
                    walletRechargeRecord.setActionType(rechargeRecordVo.getActionType());
                    walletRechargeRecord.setChangeDesc(rechargeRecordVo.getChangeDesc());
                    walletRechargeRecord.setChangeType(rechargeRecordVo.getChangeType());
                    walletRechargeRecord.setCompanyName(rechargeRecordVo.getCompanyName());
                    walletRechargeRecord.setMobile(rechargeRecordVo.getMobile());
                    walletRechargeRecord.setNickName(rechargeRecordVo.getNickName());
                    walletRechargeRecord.setCreateTime(rechargeRecordVo.getCreateTime());
                    walletRechargeRecord.setUsdAmount(rechargeRecordVo.getChangeAmount().getUsdAmount());
                    walletRechargeRecordMapper.insert(walletRechargeRecord);
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(accountCookie.getAccount()+"有问题");
            }
        }


    }

    /**获得账户7天消耗*/
    @org.junit.Test
    public void test2() throws InterruptedException {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.plusDays(-8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDateFormat = endDate.format(formatter);
        String startDateFormat = startDate.format(formatter);

        String GET_AD_ACCOUNT_INSIGHT_LIST_POST_RD = "{\"pageSize\": \"50\",\"pageNum\": \"1\",\"endDate\": \""+endDateFormat+"\",\"startDate\": \""+startDateFormat+"\"}";

        QueryWrapper<AccountCookie> accountCookieQueryWrapper = new QueryWrapper<>();
        List<AccountCookie> accountCookies = accountCookieMapper.selectList(accountCookieQueryWrapper);
        for (AccountCookie accountCookie:accountCookies) {
            try {
                String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_AD_ACCOUNT_INSIGHT_LIST_POST, accountCookie.getCookie(), GET_AD_ACCOUNT_INSIGHT_LIST_POST_RD) ;
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
                accountSystemUpdateWrapper.eq("account", accountCookie.getAccount());
                accountSystemUpdateWrapper.set("spend_amount", bigDecimal.doubleValue());
                accountSystemMapper.update(null, accountSystemUpdateWrapper);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(accountCookie.getAccount()+"有问题");
            }

        }
    }

    /**获取账户*/
    @org.junit.Test
    public void test3() throws InterruptedException {

        QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<AccountSystem>();

        List<AccountSystem> accountSystems = accountSystemMapper.selectList(accountSystemQueryWrapper);

        for (AccountSystem accountSystem:accountSystems){

            QueryWrapper<AccountCookie> accountCookieQueryWrapper = new QueryWrapper<AccountCookie>();
            accountCookieQueryWrapper.eq("account",accountSystem.getAccount());
            AccountCookie accountCookie = accountCookieMapper.selectOne(accountCookieQueryWrapper);
            try {
                String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_LIST_POST, accountCookie.getCookie(), SinoClickConstants.requestData.GET_LIST_POST_RD);
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
                System.out.println("异常后台："+accountCookie.getAccount());
            }


        }


    }

    /*更新cookie*/
    @org.junit.Test
    public void test4() throws InterruptedException {

        AccountSystem accountSystem = new AccountSystem();
        accountSystem.setAccount("18050013533");
        accountSystem.setPassword("aa123321");
        String cookie = SeleniumUtil.getCookie(accountSystem);
        AccountCookie accountCookie = new AccountCookie();
        accountCookie.setCookie(cookie);
        QueryWrapper<AccountCookie> accountCookieQueryWrapper = new QueryWrapper<AccountCookie>();
        accountCookieQueryWrapper.eq("account",accountSystem.getAccount());
        accountCookieMapper.update(accountCookie,accountCookieQueryWrapper);
    }


    @org.junit.Test
    public void test5() throws InterruptedException {

        /*AccountSystem accountSystem = new AccountSystem();
        accountSystem.setAccount("18050013533");
        accountSystem.setPassword("aa123321");
        accountSystemService.updateAccountSystem("18050013533");*/

        BigDecimal bigDecimal = new BigDecimal(0.00);
        BigDecimal bigDecimalEnd = new BigDecimal(0.00);
        for (int i = 0; i < 5; i++) {
            bigDecimalEnd = bigDecimal.add(new BigDecimal("0.8"));
        }
        String requestData = null;
        String cookie = "Hm_lpvt_e7f568a97517120fedf5c9e0f204bdcc=1667274566;pageLeave={%22pageType_var%22:%22-%22%2C%22pageName_var%22:%22%E7%99%BB%E5%BD%95_%E9%A3%9E%E4%B9%A6%E9%80%B8%E9%80%94(SinoClick)-%E9%A3%9E%E4%B9%A6%E6%B7%B1%E8%AF%BA%E6%97%97%E4%B8%8B%EF%BC%8C%E8%B7%A8%E5%A2%83%E7%94%B5%E5%95%86%E5%87%BA%E6%B5%B7%E8%90%A5%E9%94%80SaaS%E6%9C%8D%E5%8A%A1%E5%B9%B3%E5%8F%B0%22%2C%22rmTime_var%22:%223%22%2C%22startTime_var%22:%222022-11-01%2011:49:23%22%2C%22endTime_var%22:%222022-11-01%2011:49:26%22%2C%22platform%22:%22pc%22%2C%22pageURL_var%22:%22https://passport.sinoclick.com/login%22%2C%22pageUrl%22:%22https://passport.sinoclick.com/login%22};96ca14b024c8be7c_gr_last_sent_sid_with_cs1=9202491f-eb0c-43b9-8589-38668eaa215e;96ca14b024c8be7c_gr_session_id=9202491f-eb0c-43b9-8589-38668eaa215e;96ca14b024c8be7c_gr_cs1=2071952;96ca14b024c8be7c_gr_last_sent_cs1=2071952;96ca14b024c8be7c_gr_session_id_9202491f-eb0c-43b9-8589-38668eaa215e=true;user-token=1bc3bf7fb4a445189200a5e9c3fb1bd9;gr_user_id=f98e42eb-082f-4efc-b526-aea5bda1fdde;Hm_lvt_e7f568a97517120fedf5c9e0f204bdcc=1667274564;pageView={%22pageType_var%22:%22-%22%2C%22pageName_var%22:%22%E5%95%86%E6%88%B7%E4%B8%AD%E5%BF%83-%E9%A6%96%E9%A1%B5%22%2C%22pageFromURL_var%22:%22https://passport.sinoclick.com/login%22%2C%22pageFromName_var%22:%22%E7%99%BB%E5%BD%95_%E9%A3%9E%E4%B9%A6%E9%80%B8%E9%80%94(SinoClick)-%E9%A3%9E%E4%B9%A6%E6%B7%B1%E8%AF%BA%E6%97%97%E4%B8%8B%EF%BC%8C%E8%B7%A8%E5%A2%83%E7%94%B5%E5%95%86%E5%87%BA%E6%B5%B7%E8%90%A5%E9%94%80SaaS%E6%9C%8D%E5%8A%A1%E5%B9%B3%E5%8F%B0%22%2C%22platform%22:%22pc%22%2C%22pageUrl%22:%22https://business.sinoclick.com/client/comInfo%22};";
        requestData = "{\"tradeId\": \"1294457\"}";
        String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.QUERY_TRADE_ORDER,cookie,requestData);
        Map<String, String> map = JSON.parseObject(s, new TypeReference<HashMap<String, String>>() {});
        Map<String, String> mapResult = JSON.parseObject(map.get("result"), new TypeReference<HashMap<String, String>>() {});
        Map<String, String> tradeSubAmount = JSON.parseObject(mapResult.get("tradeSubAmount"), new TypeReference<HashMap<String, String>>() {});
        System.out.println(tradeSubAmount);



    }

    /**测试充值记录*/
    @org.junit.Test
    public void test6() throws InterruptedException {

        LocalDate startDate = LocalDate.of(2022,1,1);
        LocalDate endDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDateFormat = endDate.format(formatter);
        String startDateFormat = startDate.format(formatter);
        String requestData = null;
        String cookie = "96ca14b024c8be7c_gr_session_id_0b2ce130-18d7-467a-a649-f5f3ea799516=true;Hm_lpvt_e7f568a97517120fedf5c9e0f204bdcc=1667524007;pageLeave={%22pageType_var%22:%22-%22%2C%22pageName_var%22:%22%E7%99%BB%E5%BD%95_%E9%A3%9E%E4%B9%A6%E9%80%B8%E9%80%94(SinoClick)-%E9%A3%9E%E4%B9%A6%E6%B7%B1%E8%AF%BA%E6%97%97%E4%B8%8B%EF%BC%8C%E8%B7%A8%E5%A2%83%E7%94%B5%E5%95%86%E5%87%BA%E6%B5%B7%E8%90%A5%E9%94%80SaaS%E6%9C%8D%E5%8A%A1%E5%B9%B3%E5%8F%B0%22%2C%22rmTime_var%22:%222%22%2C%22startTime_var%22:%222022-11-04%2009:06:44%22%2C%22endTime_var%22:%222022-11-04%2009:06:46%22%2C%22platform%22:%22pc%22%2C%22pageURL_var%22:%22https://passport.sinoclick.com/login%22%2C%22pageUrl%22:%22https://passport.sinoclick.com/login%22};96ca14b024c8be7c_gr_last_sent_sid_with_cs1=0b2ce130-18d7-467a-a649-f5f3ea799516;96ca14b024c8be7c_gr_session_id=0b2ce130-18d7-467a-a649-f5f3ea799516;96ca14b024c8be7c_gr_cs1=2071952;96ca14b024c8be7c_gr_last_sent_cs1=2071952;user-token=3d5501e99cc043068b775f019608b427;gr_user_id=d14b84b7-7579-418d-942f-94b2c94b4c2a;Hm_lvt_e7f568a97517120fedf5c9e0f204bdcc=1667524004;pageView={%22pageType_var%22:%22-%22%2C%22pageName_var%22:%22%E5%95%86%E6%88%B7%E4%B8%AD%E5%BF%83-%E9%A6%96%E9%A1%B5%22%2C%22pageFromURL_var%22:%22https://passport.sinoclick.com/login%22%2C%22pageFromName_var%22:%22%E7%99%BB%E5%BD%95_%E9%A3%9E%E4%B9%A6%E9%80%B8%E9%80%94(SinoClick)-%E9%A3%9E%E4%B9%A6%E6%B7%B1%E8%AF%BA%E6%97%97%E4%B8%8B%EF%BC%8C%E8%B7%A8%E5%A2%83%E7%94%B5%E5%95%86%E5%87%BA%E6%B5%B7%E8%90%A5%E9%94%80SaaS%E6%9C%8D%E5%8A%A1%E5%B9%B3%E5%8F%B0%22%2C%22platform%22:%22pc%22%2C%22pageUrl%22:%22https://business.sinoclick.com/client/comInfo%22};";
        requestData = "{\"pageSize\": \"50\",\"pageNum\": \"1\",\"endDate\": \""+endDateFormat+"\",\"startDate\": \""+startDateFormat+"\"}";
        String tradeOrderStr = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_TRADE_ORDER_LIST_POST, cookie, requestData);
        Map<String, String> tradeOrderMap = JSON.parseObject(tradeOrderStr, new TypeReference<HashMap<String, String>>() {});
        Map<String, String> tradeOrderMapResult = JSON.parseObject(tradeOrderMap.get("result"), new TypeReference<HashMap<String, String>>() {});
        int total = Integer.parseInt(tradeOrderMapResult.get("total"));
        int page = total/50+(total%50 != 0 ? 1:0);
            for (int i = 2; i <page+1 ; i++) {
                requestData = "{\"pageSize\": \"50\",\"pageNum\": \""+page+"\",\"endDate\": \""+endDateFormat+"\",\"startDate\": \""+startDateFormat+"\"}";
                String pageTradeOrderStr = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_TRADE_ORDER_LIST_POST, cookie, requestData);
                Map<String, String> pageTradeOrderMap = JSON.parseObject(pageTradeOrderStr, new TypeReference<HashMap<String, String>>() {});
                Map<String, String> pageTradeOrderMapResult = JSON.parseObject(pageTradeOrderMap.get("result"), new TypeReference<HashMap<String, String>>() {});
                List<AdAccountRechargeRecordVo> adAccountRechargeRecordVoList = JSONArray.parseArray(pageTradeOrderMapResult.get("result"), AdAccountRechargeRecordVo.class);
                for (AdAccountRechargeRecordVo adAccountRechargeRecordVo:adAccountRechargeRecordVoList){
                    AdAccountRechargeRecord adAccountRechargeRecord = new AdAccountRechargeRecord();
                    adAccountRechargeRecord.setTradeId(adAccountRechargeRecordVo.getTradeId());
                    adAccountRechargeRecord.setTid(adAccountRechargeRecordVo.getTid());
                    adAccountRechargeRecord.setPayMethod(adAccountRechargeRecordVo.getPayMethod());
                    adAccountRechargeRecord.setTradeStatusName(adAccountRechargeRecordVo.getTradeStatusName());
                    adAccountRechargeRecord.setCnyAmount(adAccountRechargeRecordVo.getTradeAmount().getCnyAmount());
                    adAccountRechargeRecord.setUsdAmount(adAccountRechargeRecordVo.getTradeAmount().getUsdAmount());
                    adAccountRechargeRecord.setTradeDetailTypeDesc(adAccountRechargeRecordVo.getTradeDetailList().get(0).getTradeDetailTypeDesc());
                    adAccountRechargeRecord.setChannelId(adAccountRechargeRecordVo.getTradeDetailList().get(0).getChannelId());
                    adAccountRechargeRecord.setChannelAccountId(adAccountRechargeRecordVo.getTradeDetailList().get(0).getChannelAccountId());
                }
            }




    }

    @org.junit.Test
    public void testSelenium() throws InterruptedException {
        //参数配置
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver;
        ChromeOptions option = new ChromeOptions();
//        option.addArguments("no-sandbox");//禁用沙盒
//        option.addArguments("--headless");//无头浏览器，不打开窗口
        //通过ChromeOptions的setExperimentalOption方法，传下面两个参数来禁止掉谷歌受自动化控制的信息栏
        option.setExperimentalOption("useAutomationExtension", false);
        option.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(option);
        driver.get("https://passport.sinoclick.com/login");
/*        String html=driver.getPageSource();
        // 这里只是打印源码，后续可以根据自己的需求来解析相关的数据
        System.out.println(html);*/

//        Thread.sleep(1000);
        WebElement account = driver.findElement(By.id("account"));
        account.sendKeys("17129480564");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("aa123321");
//        Thread.sleep(1000L);
        Thread.sleep(200);
        WebElement loginButton = driver.findElement(By.xpath("/html/body/lang/div[@id='root']/div[@class='App ']/div/div[@class='passport-wrapper login-wrapper']/div[@class='login-wrapper-right']/div[@class='login-component ']/div[@class='login-box']/form[@class='ant-form ant-form-horizontal login-form']/div[@class='ant-form-item']/div[@class='ant-row ant-form-item-row']/div[@class='ant-col ant-form-item-control']/div[@class='ant-form-item-control-input']/div[@class='ant-form-item-control-input-content']/button[@class='ant-btn ant-btn-primary ant-btn-block']"));
        loginButton.click();
        Thread.sleep(2000);
        String textContent = driver.findElement(By.xpath("/html/body/div[@id='root']/section[@class='m-design-layout layout']/section[@class='m-design-layout']/section[@class='m-design-layout con-layout']/main[@class='m-design-layout-content site-layout-background home-main-safari-hack m-design-layout-content-dark']/div[@class='main-box']/div[@class='content-box']/div[@class='panel_cart_item ']/div[@class='asserts_panel_main pt-6 pb-6']/div[@class='m-design-card main_right pl-5 pr-5']/div[@class='m-design-card-body']/div[2]/div[1]/div[1]/div[@class='asserts_account_main_right']/div[@class='channel_assert_item'][1]/div[@class='main_bottom']/div[@class='fbLevel_r']/div[@class='channel_data_detail_item_bottom channel_data_detail_item_bottom_log']/span")).getAttribute("textContent").toString();
        System.out.println(textContent);
        /*driver.get("https://business.sinoclick.com/client/myorder/recharge/ad-account?account=act_380201567366859&channel=1&user=2021543");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 20,1);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[@id='root']/section[@class='m-design-layout layout']/section[@class='m-design-layout m-design-layout-has-sider']/section[@class='m-design-layout con-layout']/main[@class='m-design-layout-content site-layout-background home-main-safari-hack m-design-layout-content-dark']/div[@class='recharge__RechartStyle-sc-17j3ul1-0 kMjQCQ']/div[@class='m-design-card card__CardStyle-sc-a60nbi-0 cuGpQt']/div[@class='m-design-card-body']/div[@class='form-container']/button[@class='m-design-btn m-design-btn-primary m-design-btn-lg']/span")));
        WebElement rechargeButton = driver.findElement(By.xpath("/html/body/div[@id='root']/section[@class='m-design-layout layout']/section[@class='m-design-layout m-design-layout-has-sider']/section[@class='m-design-layout con-layout']/main[@class='m-design-layout-content site-layout-background home-main-safari-hack m-design-layout-content-dark']/div[@class='recharge__RechartStyle-sc-17j3ul1-0 kMjQCQ']/div[@class='m-design-card card__CardStyle-sc-a60nbi-0 cuGpQt']/div[@class='m-design-card-body']/div[@class='form-container']/button[@class='m-design-btn m-design-btn-primary m-design-btn-lg']/span"));
        WebElement rechargeAmount = driver.findElement(By.id("recharge_amount"));
        rechargeAmount.sendKeys("100");
        rechargeButton.click();*/

    }


    @org.junit.Test
    public void test7() throws InterruptedException, ParseException {

        QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
        List<AccountSystem> accountSystems = accountSystemMapper.selectList(accountSystemQueryWrapper);
        for (AccountSystem accountSystem:accountSystems){
            adAccountRechargeRecordService.insertAdAccountRechargeRecord(accountSystem);
        }


    }

    @org.junit.Test
    public void test8() {
        /*List<AdAccountRechargeRecord> adAccountRechargeRecords = adAccountRechargeRecordMapper.selectList(new QueryWrapper<>());
        System.out.println(adAccountRechargeRecords);*/
        List<WalletRechargeRecord> walletRechargeRecords = walletRechargeRecordMapper.selectList(new QueryWrapper<>());
        System.out.println(walletRechargeRecords);
    }

}
