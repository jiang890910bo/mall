package com.jiangbo.mall.util;

/**
 * @author Jiangbo Cheng on 2017/11/4.
 */

public class SessionKeyUtil {

    public static  final int ONE_MINUTE = 60;

    public static  final int FIVE_MINUTE = 60 * 5;

    public static  final int TEN_MINUTE = 60 * 10;

    public static  final int THIRTY_MINUTE = 60 * 30;

    public static final String COOKIE_NAME = "ms_token";

    /**
     * 存储在session用户的key
     */
    public static final String USER_KEY = "user";

    /**
     * 首页产品列表数据
     */
    public static final String INDEX_PRODUCTlIST_DATA = "index_productList_data";

    /**
     * 首页最热产品数据
     */
    public static final String INDEX_HOT_PRODUCT_DATA = "index_hot_product_data";
}
