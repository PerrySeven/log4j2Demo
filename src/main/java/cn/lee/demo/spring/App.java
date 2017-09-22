package cn.lee.demo.spring;

import org.apache.logging.log4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author li wei  <liwei_yy1@migu.cn>
 * @Description: TODO
 * Package Name:cn.lee.demo.spring.log
 * Date: 2017/9/21 10:34
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company    MiGu  Co., Ltd.
 **/
@SpringBootApplication
public class App {
    protected final static Logger logger = LogManager.getLogger(App.class);
    private static final Level TRADE = Level.getLevel("TRADE");
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        logger.info("Application is started!");
        logger.info("logger hello info *************");
        logger.debug("logger hello debug &&&&&&&&&&&&&&");
        logger.error("logger hello error $$$$$$$$$$$$$$$");
        logger.fatal("logger hello fatal $$$$$$$$$$$$$$$");
        logger.log(TRADE,"logger hello TRADE $$$$$$$$$$$$$$$");
        logger.error(MarkerManager.getMarker("TRACE_MARKER"),"logger hello MQ_Marker $$$$$$$$$$$$$$$");
//        testLog();
    }
    private static void testLog(){
        int n=1000;
        for (int i=0;i<n;i++){
            logger.error("log_"+i+" this is log msg"+i);
            logger.debug("log_"+i+" this is log msg"+i);
        }
    }

}
