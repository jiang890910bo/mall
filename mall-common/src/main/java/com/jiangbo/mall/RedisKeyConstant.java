package com.jiangbo.mall;

/**
 * @author Jiangbo Cheng on 2017/11/22.
 */

public class RedisKeyConstant {

    public static final String SYSTEM_PREFIX = "mall:";

    /**
     * 后台管理系统前缀
     */
    public static final String MANAGEMENT_SYSTEM_PREFIX = SYSTEM_PREFIX + "management:";

    /**
     * 前台网站前缀
     */
    public static final String MARKETPLACE_PREFIX = SYSTEM_PREFIX + "marketplace:";
    /**
     * 产品库存
     */
    public static final String PRODUCT_STOCK_PREFIX = MARKETPLACE_PREFIX + "product_stock:";


    /**
     * 首页正在活动中的产品列表数据
     */
    public static final String INDEX_WORKING_PRODUCTlIST_DATA = MARKETPLACE_PREFIX + "index_working_productList_data";

    /**
     * 首页待开始活动的产品列表数据
     */
    public static final String INDEX_WAITING_PRODUCTlIST_DATA = MARKETPLACE_PREFIX + "index_waiting_productList_data";

    /**
     * 首页最热产品数据
     */
    public static final String INDEX_HOT_PRODUCT_DATA = MARKETPLACE_PREFIX + "index_hot_product_data";
}
