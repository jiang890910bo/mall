package com.jiangbo.mall.load;

import com.jiangbo.mall.RedisKeyConstant;
import com.jiangbo.mall.RedisValidTimeConstant;
import com.jiangbo.mall.biz.ActivityProductRefBiz;
import com.jiangbo.mall.biz.RedisClientBiz;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.vo.ActivityProductRefVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/11/22.
 */
//@Component
public class LoadProductStockToRedis {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisClientBiz redisClientBiz;

    @Autowired
    ActivityProductRefBiz activityProductRefBiz;

    /**
     * 每天晚上23 触发
     */
    //@Scheduled(cron = "0 0 23 * * ?")
    private void loadStock(){
        logger.info("...job loading product stock...");
        ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
        activityProductRefVO.setStatus(DataStatusEnum.NORMAL.getNum());
        List<ActivityProductRefVO> list = activityProductRefBiz.getByCondition(activityProductRefVO);
        for(ActivityProductRefVO vo : list){
            redisClientBiz.set(RedisKeyConstant.PRODUCT_STOCK_PREFIX + vo.getId(), vo.getStock().intValue(), RedisValidTimeConstant.TWELVES_HOURS);
        }
    }


    /**
     *
     "0 0 12 * * ?"			每天中午十二点触发
     "0 15 10 ? * *"			每天早上10：15触发
     "0 15 10 * * ?"			每天早上10：15触发
     "0 15 10 * * ? *"		每天早上10：15触发
     "0 15 10 * * ? 2005" 	2005年的每天早上10：15触发
     "0 * 14 * * ?"			每天从下午2点开始到2点59分每分钟一次触发
     "0 0/5 * * * * ?"		每5分钟触发一次
     "0 0/5 14 * * ?"		每天从下午2点开始到2：55分结束每5分钟一次触发
     "0 0/5 14,18 * * ?"		每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     "0 0-5 14 * * ?"		每天14:00至14:05每分钟一次触发
     "0 10,44 14 ? 3 WED"	三月的每周三的14：10和14：44触发
     "0 15 10 ? * MON-FRI"	每个周一、周二、周三、周四、周五的10：15触发
     "0 15 10 15 * ?"		每月15号的10：15触发
     "0 15 10 L * ?"			每月的最后一天的10：15触发
     "0 15 10 ? * 6L"		每月最后一个周五的10：15
     */
}
