package com.nekochips.sign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author NekoChips
 * @description 项目启动类
 * @date 2020/5/2
 */
@SpringBootApplication
public class AutoCheckInToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoCheckInToolApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
