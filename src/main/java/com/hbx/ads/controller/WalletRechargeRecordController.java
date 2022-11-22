package com.hbx.ads.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbx.ads.common.SinoClickConstants;
import com.hbx.ads.domain.pojo.WalletRechargeRecordVo;
import com.hbx.ads.domain.sinoclick.AccountCookie;
import com.hbx.ads.domain.sinoclick.WalletRechargeRecord;
import com.hbx.ads.mapper.AccountCookieMapper;
import com.hbx.ads.mapper.WalletRechargeRecordMapper;
import com.hbx.ads.util.EUDataGridResult;
import com.hbx.ads.util.Sinoclick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/walletRechargeRecord")
public class WalletRechargeRecordController {

    @Autowired
    WalletRechargeRecordMapper walletRechargeRecordMapper;

    @Autowired
    AccountCookieMapper accountCookieMapper;

    @RequestMapping("/find")
    @ResponseBody
    public EUDataGridResult getRechargeRecordList(int page, int rows, String changeDesc, String companyName){

        QueryWrapper<WalletRechargeRecord> rechargeRecordQueryWrapper = new QueryWrapper<>();
        if(changeDesc != "" && changeDesc !=null){
            rechargeRecordQueryWrapper.like("change_desc",changeDesc);
        }
        if(companyName != "" && companyName !=null){
            rechargeRecordQueryWrapper.like("company_name",companyName);
        }
        rechargeRecordQueryWrapper.orderByDesc("create_time");
        IPage<WalletRechargeRecord> rechargeRecordPage = new Page<>(page ,rows);
        Page<WalletRechargeRecord> selectPage = (Page<WalletRechargeRecord>) walletRechargeRecordMapper.selectPage(rechargeRecordPage,rechargeRecordQueryWrapper);
        EUDataGridResult result = new EUDataGridResult();

        result.setRows(selectPage.getRecords());
        result.setTotal(rechargeRecordPage.getTotal());

        return result;

    }

    @RequestMapping("/update")
    @ResponseBody
    public void updateRechargeRecordList(){

        LocalDate  date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateFormat = date.format(formatter);


        String GET_RECHARGE_RECORD_LIST_POST_RD = "{\"dataLevel\": \"1\",\"pageSize\": \"50\",\"pageNum\": \"1\",\"endDate\": \""+dateFormat+"\",\"startDate\": \"2022-01-01\"}";


        QueryWrapper<AccountCookie> accountCookieQueryWrapper = new QueryWrapper<>();
        List<AccountCookie> accountCookies = accountCookieMapper.selectList(accountCookieQueryWrapper);
        for (AccountCookie accountCookie:accountCookies){
            try{
                String s = Sinoclick.postRequest(SinoClickConstants.requestUrl.GET_RECHARGE_RECORD_LIST_POST, accountCookie.getCookie(), GET_RECHARGE_RECORD_LIST_POST_RD);
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
                    QueryWrapper<WalletRechargeRecord> rechargeRecordQueryWrapper = new QueryWrapper<>();
                    rechargeRecordQueryWrapper.eq("create_time", walletRechargeRecord.getCreateTime());
                    rechargeRecordQueryWrapper.eq("change_desc", walletRechargeRecord.getChangeDesc());
                    if (walletRechargeRecordMapper.selectOne(rechargeRecordQueryWrapper)==null){
                        walletRechargeRecordMapper.insert(walletRechargeRecord);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(accountCookie.getAccount()+"有问题");
            }
        }


    }


}
