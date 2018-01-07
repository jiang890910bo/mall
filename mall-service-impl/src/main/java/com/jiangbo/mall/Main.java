package com.jiangbo.mall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Jiangbo Cheng on 2017/10/21.
 */
@SpringBootApplication
@ImportResource({"classpath:spring/spring-applicationContext.xml", "classpath:dubbo/dubbo-provider.xml"})
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    public static void main (String[] args) throws IOException, InterruptedException {
        ApplicationContext ctx = new SpringApplicationBuilder()
                .sources(Main.class)
                .web(false)
                .run(args);

        logger.info("项目启动!");
        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
        closeLatch.await();
    }
}
