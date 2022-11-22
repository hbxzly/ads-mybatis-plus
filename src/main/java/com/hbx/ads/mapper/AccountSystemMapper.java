package com.hbx.ads.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbx.ads.domain.sinoclick.AccountSystem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountSystemMapper extends BaseMapper<AccountSystem> {

    @Select("SELECT DISTINCT bd_name AS bdName FROM account_system")
    List<String> searchBdName();



}
