package com.hbx.ads.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbx.ads.domain.sinoclick.AdAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdAccountMapper extends BaseMapper<AdAccount> {

//    IPage<AdAccount> customPage(@Param("adAccounts") String adAccounts);
    List<AdAccount> customQueryAdAccountByAdAccounts(@Param("page") int page, @Param("rows") int rows, @Param("adAccounts") String[] adAccounts);



}

