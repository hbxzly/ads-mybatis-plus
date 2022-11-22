package com.hbx.ads.util;

import com.hbx.ads.domain.sinoclick.AccountCookie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CookieUtil {

    public static boolean checkCookie(AccountCookie accountCookie){

        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        Date updateTime = accountCookie.getUpdateTime();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = updateTime.toInstant().atZone(zoneId).toLocalDateTime();
        if (localDateTime.isAfter(startTime)&&localDateTime.isBefore(endTime)){
            return true;
        }else {
            return false;
        }

    }

}
