package com.hbx.ads.common;

public class SinoClickConstants {


    public static final class requestUrl{
        /**钱包余额,账户总数*/
        public static final String GET_USER_ASSETS_POST = "https://rapi.sinoclick.com/user/getUserAssets";

        /**违规状况*/
        public static final String CLIENT_FINAL_LEVEL_POST = "https://rapi.sinoclick.com/client/getClientFinalLevel";

        /**账户详情*/
        public static final String OVERVIEW_POST = "https://rapi.sinoclick.com/adaccount/overview";

        /**账户列表*/
        public static final String GET_LIST_POST = "https://rapi.sinoclick.com/adaccount/getList";

        /**账户申请列表*/
        public static final String GET_FACEBOOK_PROCESS_LIST_POST = "https://rapi.sinoclick.com/openaccount/facebook/getFacebookProcessList";

        /**获取后台信息*/
        public static final String GET_UDESK_CLIENT_INFO_POST = "https://rapi.sinoclick.com/user/getUdeskClientInfo";

        /**获取账户清零到钱包的记录*/
        public static final String GET_RECHARGE_RECORD_LIST_POST = "https://rapi.sinoclick.com/merchants/trade/getRechargeRecordList";

        /**获取账户消耗金额*/
        public static final String GET_AD_ACCOUNT_INSIGHT_LIST_POST = "https://rapi.sinoclick.com/merchants/trade/getAdaccountInsightsList";

        /**获取充值记录*/
        public static  final String GET_TRADE_ORDER_LIST_POST = "https://rapi.sinoclick.com/merchants/trade/getTradeOrderList";

        /**获取充值细节*/
        public static  final String QUERY_TRADE_ORDER = "https://rapi.sinoclick.com/merchants/payment/queryTradeOrder";
    }
    public static final class requestData{

        /**获取账户列表*/
        public static final String GET_LIST_POST_RD="{\"channelId\": \"1\",\"keywords\": \"\",\"pageSize\": \"50\",\"pageNum\": \"1\"}";

        /**通用*/
        public static final String COMMON_RD="{\"domain\": \"sinoclick.com\"}";

        /**获取账户清零到钱包的记录*/
        public static final String GET_RECHARGE_RECORD_LIST_POST_RD = "{\"dataLevel\": \"1\",\"pageSize\": \"50\",\"pageNum\": \"1\",\"endDate\": \"2022-09-26\",\"startDate\": \"2022-01-01\"}";

        /**获取账户消耗金额*/
        public static final String GET_AD_ACCOUNT_INSIGHT_LIST_POST_RD = "{\"pageSize\": \"50\",\"pageNum\": \"1\",\"endDate\": \"2022-09-26\",\"startDate\": \"2022-09-20\"}";

        /**违规状况*/
        public static final String CLIENT_FINAL_LEVEL_RD = "{\"channelId\": \"1\"}";

        /**钱包余额,账户总数*/
        public static final String WALLET_BALANCE_RD = "{\"refresh\": \"true\"}";

        /**账户详情*/
        public static final String OVERVIEW_RD = "{\"refresh\": \"true\"}";

    }



}
