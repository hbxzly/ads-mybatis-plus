package com.hbx.ads.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.ads.common.SinoClickConstants;
import com.hbx.ads.domain.pojo.AdAccountRechargeRecordVo;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import com.hbx.ads.domain.sinoclick.AdAccountRechargeRecord;
import com.hbx.ads.mapper.AccountCookieMapper;
import com.hbx.ads.mapper.AccountSystemMapper;
import com.hbx.ads.mapper.AdAccountRechargeRecordMapper;
import com.hbx.ads.service.AccountCookieService;
import com.hbx.ads.service.AdAccountRechargeRecordService;
import com.hbx.ads.util.Sinoclick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdAccountRechargeRecordServiceImpl implements AdAccountRechargeRecordService {

    @Autowired
    AdAccountRechargeRecordMapper adAccountRechargeRecordMapper;

    @Autowired
    AccountSystemMapper accountSystemMapper;

    @Autowired
    AccountCookieService accountCookieService;

    @Override
    public void insertAdAccountRechargeRecord(AccountSystem accountSystem) throws InterruptedException, ParseException {

        String accountCookie = accountCookieService.getAccountCookie(accountSystem.getAccount());
        LocalDate startDate = LocalDate.of(2022,1,1);
        LocalDate endDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDateFormat = endDate.format(formatter);
        String startDateFormat = startDate.format(formatter);
        String requestData = null;
        try {


            requestData = "{\"pageSize\": \"50\",\"pageNum\": \"1\",\"endDate\": \""+endDateFormat+"\",\"startDate\": \""+startDateFormat+"\"}";
            String tradeOrderStr = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_TRADE_ORDER_LIST_POST, accountCookie, requestData);
            Map<String, String> tradeOrderMap = JSON.parseObject(tradeOrderStr, new TypeReference<HashMap<String, String>>() {});
            Map<String, String> tradeOrderMapResult = JSON.parseObject(tradeOrderMap.get("result"), new TypeReference<HashMap<String, String>>() {});
            int total = Integer.parseInt(tradeOrderMapResult.get("total"));
            int page = total/50+(total%50 != 0 ? 1:0);
            for (int i = 1; i <page+1 ; i++) {
                requestData = "{\"pageSize\": \"50\",\"pageNum\": \""+i+"\",\"endDate\": \""+endDateFormat+"\",\"startDate\": \""+startDateFormat+"\"}";
                String pageTradeOrderStr = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_TRADE_ORDER_LIST_POST, accountCookie, requestData);
                Map<String, String> pageTradeOrderMap = JSON.parseObject(pageTradeOrderStr, new TypeReference<HashMap<String, String>>() {});
                Map<String, String> pageTradeOrderMapResult = JSON.parseObject(pageTradeOrderMap.get("result"), new TypeReference<HashMap<String, String>>() {});
                List<AdAccountRechargeRecordVo> adAccountRechargeRecordVoList = JSONArray.parseArray(pageTradeOrderMapResult.get("result"), AdAccountRechargeRecordVo.class);
                for (AdAccountRechargeRecordVo adAccountRechargeRecordVo:adAccountRechargeRecordVoList){
                    requestData = "{\"tradeId\": \""+adAccountRechargeRecordVo.getTradeId()+"\"}";
                    String queryTradeOrderStr = Sinoclick.postRequest(SinoClickConstants.requestUrl.QUERY_TRADE_ORDER, accountCookie, requestData);
                    Map<String, String> queryTradeOrderMap = JSON.parseObject(queryTradeOrderStr, new TypeReference<HashMap<String, String>>() {});
                    Map<String, String> queryTradeOrderMapResult = JSON.parseObject(queryTradeOrderMap.get("result"), new TypeReference<HashMap<String, String>>() {});
                    Map<String, String> walletAmountMap = JSON.parseObject(queryTradeOrderMapResult.get("walletAmount"), new TypeReference<HashMap<String, String>>() {});
                    Map<String, String> tradeSubAmountMap = JSON.parseObject(queryTradeOrderMapResult.get("tradeSubAmount"), new TypeReference<HashMap<String, String>>() {});
                    AdAccountRechargeRecord adAccountRechargeRecord = new AdAccountRechargeRecord();
                    adAccountRechargeRecord.setTradeId(adAccountRechargeRecordVo.getTradeId());
                    adAccountRechargeRecord.setTid(adAccountRechargeRecordVo.getTid());
                    adAccountRechargeRecord.setPayMethod(adAccountRechargeRecordVo.getPayMethod());
                    adAccountRechargeRecord.setCreateTime(adAccountRechargeRecordVo.getCreateTime());
                    if (adAccountRechargeRecordVo.getTradeStatusName().equals("支付成功")){
                        adAccountRechargeRecord.setPayTime(adAccountRechargeRecordVo.getPayTime());
                    }
                    adAccountRechargeRecord.setTradeStatusName(adAccountRechargeRecordVo.getTradeStatusName());
                    adAccountRechargeRecord.setUsdAmount(adAccountRechargeRecordVo.getTradeAmount().getUsdAmount());
                    adAccountRechargeRecord.setCnyAmount(adAccountRechargeRecordVo.getTradeAmount().getCnyAmount());
                    adAccountRechargeRecord.setExchangeRate(queryTradeOrderMapResult.get("exchangeRate"));
                    adAccountRechargeRecord.setWalletUsdAmount(Double.parseDouble(walletAmountMap.get("usdAmount")));
                    adAccountRechargeRecord.setWalletCnyAmount(Double.parseDouble(walletAmountMap.get("cnyAmount")));
                    adAccountRechargeRecord.setTradeSubUsdAmount(Double.parseDouble(tradeSubAmountMap.get("usdAmount")));
                    adAccountRechargeRecord.setTradeSubCnyAmount(Double.parseDouble(tradeSubAmountMap.get("cnyAmount")));
                    adAccountRechargeRecord.setTradeDetailTypeDesc(adAccountRechargeRecordVo.getTradeDetailList().get(0).getTradeDetailTypeDesc());
                    adAccountRechargeRecord.setChannelId(adAccountRechargeRecordVo.getTradeDetailList().get(0).getChannelId());
                    adAccountRechargeRecord.setChannelAccountId(adAccountRechargeRecordVo.getTradeDetailList().get(0).getChannelAccountId());
                    adAccountRechargeRecord.setAccountSystem(accountSystem.getClientName());
                    if (accountSystem.getNote().equals("卖出")){
                        adAccountRechargeRecord.setAccountSystemIsSell("1");
                    }
                    adAccountRechargeRecordMapper.insert(adAccountRechargeRecord);
                    }
                }
            }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<AdAccountRechargeRecord> getAllAdAccountRechargeRecord() {
        QueryWrapper<AdAccountRechargeRecord> adAccountRechargeRecordQueryWrapper = new QueryWrapper<>();
        return adAccountRechargeRecordMapper.selectList(adAccountRechargeRecordQueryWrapper.orderByDesc("pay_time"));
    }

    @Override
    public Page<AdAccountRechargeRecord> getPageAdAccountRechargeRecord(int page, int rows , AdAccountRechargeRecord adAccountRechargeRecord) {

        QueryWrapper<AdAccountRechargeRecord> adAccountRechargeRecordQueryWrapper =new QueryWrapper<>();
        adAccountRechargeRecordQueryWrapper.orderByDesc("pay_time");

        IPage<AdAccountRechargeRecord> adAccountRechargeRecordPage = new Page<>(page,rows);
        Page<AdAccountRechargeRecord> selectPage = (Page<AdAccountRechargeRecord>) adAccountRechargeRecordMapper.selectPage(adAccountRechargeRecordPage, adAccountRechargeRecordQueryWrapper);
        return selectPage;
    }

    @Override
    public void updateAdAccountRechargeRecord(AdAccountRechargeRecord adAccountRechargeRecord) {

        UpdateWrapper<AdAccountRechargeRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("tid",adAccountRechargeRecord.getTid())
                .set("pay_card",adAccountRechargeRecord.getPayCard())
                .set("receiver",adAccountRechargeRecord.getReceiver())
                .set("note",adAccountRechargeRecord.getNote());
        adAccountRechargeRecordMapper.update(null,updateWrapper);

    }


}
