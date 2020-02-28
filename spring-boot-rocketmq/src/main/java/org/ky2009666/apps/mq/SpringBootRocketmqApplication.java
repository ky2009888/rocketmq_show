package org.ky2009666.apps.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring boot的核心启动类
 *
 * @author Lenovo
 */
@SpringBootApplication
public class SpringBootRocketmqApplication {
    /**
     * 核心启动方法
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRocketmqApplication.class, args);
    }

}
