package com.jiangbo.mall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Jiangbo Cheng on 2017/10/21.
 */

public class Hmain {

    private static final Logger logger = LoggerFactory.getLogger(Hmain.class);


    public static void main (String[] args) throws IOException{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "spring/spring-applicationContext.xml", "dubbo/dubbo-provider.xml"});

        context.start();
        System.out.println("dubbo service start, Enter any key stop...");
        System.in.read();//为保证服务一直开着，利用输入流的阻塞来模拟
    }
}
