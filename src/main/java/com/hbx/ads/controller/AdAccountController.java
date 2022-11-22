package com.hbx.ads.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.ads.common.SinoClickConstants;
import com.hbx.ads.domain.pojo.AdAccountInsightVo;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccount;
import com.hbx.ads.mapper.AccountSystemMapper;
import com.hbx.ads.mapper.AdAccountMapper;
import com.hbx.ads.service.AccountCookieService;
import com.hbx.ads.service.AccountSystemService;
import com.hbx.ads.service.AdAccountService;
import com.hbx.ads.service.SeleniumService;
import com.hbx.ads.util.EUDataGridResult;
import com.hbx.ads.util.Sinoclick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@RequestMapping("adAccount")
public class AdAccountController {

    @Autowired
    AdAccountMapper adAccountMapper;

    @Autowired
    AdAccountService adAccountService;

    @Autowired
    AccountSystemMapper accountSystemMapper;

    @Autowired
    AccountSystemService accountSystemService;

    @Autowired
    AccountCookieService accountCookieService;

    @Autowired
    SeleniumService seleniumService;

    @RequestMapping("/find")
    @ResponseBody
    public EUDataGridResult getAdAccountList(int page , int rows , AdAccount adAccount){

        QueryWrapper<AdAccount> adAccountQueryWrapper = new QueryWrapper<>();

        if (adAccount.getAdAccountId() !=null && adAccount.getAdAccountId() !="" ){
            adAccountQueryWrapper.like("ad_account_id",adAccount.getAdAccountId()).or().like("id",adAccount.getAdAccountId());
        }
        if (adAccount.getAdAccountSystemBdName() !=null && adAccount.getAdAccountSystemBdName() !="" ){
            adAccountQueryWrapper.like("ad_account_system_bd_name",adAccount.getAdAccountSystemBdName());
        }
        if (adAccount.getAdAccountSystem() !=null && adAccount.getAdAccountSystem() !="" ){
            adAccountQueryWrapper.like("ad_account_system",adAccount.getAdAccountSystem()).or().like("ad_account_system_alias",adAccount.getAdAccountSystem());
        }
        if (adAccount.getBalance() !=0 ){
            adAccountQueryWrapper.gt("balance",adAccount.getBalance());
        }
        if (adAccount.getSpendAmount() !=0 ){
            adAccountQueryWrapper.gt("spend_amount",adAccount.getSpendAmount());
        }
        if (adAccount.getAccountStatus() !=null && adAccount.getAccountStatus() !="" ){
            adAccountQueryWrapper.eq("account_status",adAccount.getAccountStatus());
        }
        if (adAccount.getAdAccountSystemStatus() !=null && adAccount.getAdAccountSystemStatus() !="" ){
            adAccountQueryWrapper.eq("ad_account_system_status",adAccount.getAdAccountSystemStatus());
        }


        IPage<AdAccount> adAccountIPage = new Page<>(page ,rows);

        Page<AdAccount> selectPage = (Page<AdAccount>) adAccountMapper.selectPage(adAccountIPage,adAccountQueryWrapper);
        EUDataGridResult result = new EUDataGridResult();

        result.setRows(selectPage.getRecords());
        result.setTotal(adAccountIPage.getTotal());

        return result;
    }

    @RequestMapping("/findByAdAccountIdList")
    @ResponseBody
    public EUDataGridResult getAdAccountListByAdAccountIdList(int page , int rows , String[] adAccounts){

        return adAccountService.queryAdAccountByAdAccounts(page, rows, adAccounts);
    }

    @RequestMapping("/updateAdAccountList")
    @ResponseBody
    public void updateAdAccountList(){

       QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<AccountSystem>();

        List<AccountSystem> accountSystems = accountSystemMapper.selectList(accountSystemQueryWrapper);

        for (AccountSystem accountSystem:accountSystems){

            String accountCookie = accountCookieService.getAccountCookie(accountSystem.getAccount());


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
    }

    @RequestMapping("/updateAdAccount")
    @ResponseBody
    public void updateAdAccount(AccountSystem accountSystem) {
        adAccountService.updateAdAccount(accountSystem);
    }

    @RequestMapping("/getAdAccountSpend")
    @ResponseBody
    public void getAdAccountSpend(){


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

    /**
     * 生成充值表
     * @param page
     * @param rows
     * @param adAccounts
     * @return
     */
    @RequestMapping("/getRechargeList")
    @ResponseBody
    public EUDataGridResult getRechargeList(int page , int rows , String[] adAccounts){
        return adAccountService.queryAdAccountByAdAccountsForRecharge(page,rows,adAccounts);
    }

    @RequestMapping("/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response, @RequestBody AdAccount adAccount) throws IOException {



        System.out.println(adAccount);

        /*System.out.println(response);
        List<AdAccount> adAccountList = adAccountMapper.selectList(new QueryWrapper<>());
        ArrayList<Map<String,Object>> mapArrayList = new ArrayList<>();
        for (AdAccount adAccount :adAccountList) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("id",adAccount.getId());
            map.put("adAccountName",adAccount.getAdAccountName());
            map.put("accountStatus",adAccount.getAccountStatus());
            map.put("balance",adAccount.getBalance());
            mapArrayList.add(map);
        }
        System.out.println(mapArrayList.size());
        String[] keys = {"账户ID","账户名称","账户状态","账户余额" };
        String[] columnNames = {"id","adAccountName","accountStatus","balance" };
        String fileName ="测试.xls";

        ExcelUtils.exportExcel(response, keys, columnNames, fileName, mapArrayList);*/

    }

    @RequestMapping("/openAdAccountSystem")
    @ResponseBody
    public void openAdAccountSystem(String[] accountSystems){
        for (int i = 0; i < accountSystems.length; i++) {
            QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
            accountSystemQueryWrapper.eq("client_name",accountSystems[i]);
            AccountSystem accountSystem = accountSystemMapper.selectOne(accountSystemQueryWrapper);
            seleniumService.loginAccountSystem(accountSystem);
        }
    }

    @RequestMapping(value = "/findAccountSystemByClientName",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String findAccountSystemByClientName(String clientName){
        QueryWrapper<AccountSystem> accountSystemQueryWrapper = new QueryWrapper<>();
        accountSystemQueryWrapper.eq("client_name",clientName);
        AccountSystem accountSystem = accountSystemMapper.selectOne(accountSystemQueryWrapper);

        return "后台余额："+accountSystem.getClientBalance()+"----------备注："+accountSystem.getNote();
    }




}
