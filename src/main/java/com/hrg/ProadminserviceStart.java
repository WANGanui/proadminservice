package com.hrg;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 82705 on 2017/5/27.
 */
public class ProadminserviceStart {
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(ProadminserviceStart.class);

    /**
     * 当前环境类型 开发环境
     */
    private static final String DEV = "dev";

    /**
     * 当前环境类型 正式环境
     */
    private static final String RELEASE = "release";

    /**
     * 当前环境类型 测试环境
     */
    private static final String TEST = "test";

    public static void main(String[] args) throws Exception {
        try {

            logger.info("开始启动HRG项目管理系统");
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[] { "spring-context.xml","spring-redis.xml", "spring-database-write.xml","spring-database-read.xml",
                            "dubbo/dubbo-config.xml","dubbo/dubbo-service-bean.xml"});
            context.start();
            logger.info("HRG项目管理系统启动完成");
            while(true){
                if(System.in.read()==13){
                    logger.info("开始重启系统");
                    context.refresh();
                    logger.info("重启完成");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("错误："+e.getMessage());
        }
    }
}
