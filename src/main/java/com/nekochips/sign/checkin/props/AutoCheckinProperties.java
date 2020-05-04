package com.nekochips.sign.checkin.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author NekoChips
 * @description 自动签到属性配置
 * @date 2020/5/2
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auto")
public class AutoCheckinProperties {

    /**
     * 签到服务
     */
    private Map<String, Server> servers = new LinkedHashMap<>();

    @Data
    public static class Server {
        /**
         * 服务名称
         */
        private String name;
        
        /**
         * 登录地址
         */
        private String loginUrl;

        /**
         * 登出地址
         */
        private String logoutUrl;

        /**
         * 签到地址
         */
        private String url;

        /**
         * 登录参数，如 username: admin, password: 123456
         */
        private Map<String, String> loginParams;

        /**
         * 返回参数，用于解析
         */
        private Response response;

        /**
         * cookie 名称
         */
        private String cookieName;

        /**
         * user-agent, 如：Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129
         * Safari/537.36
         */
        private String ua;

        /**
         * 签到时间 cron 表达式
         */
        private String cron;
    }

    @Data
    public static class Response {
        
        /**
         * 返回体中的返回码字段
         */
        private String code;

        /**
         * 返回体中返回描述字段
         */
        private String message;

        /**
         * 返回体中的 token 字段
         */
        private String token;

        /**
         * 正确返回码
         */
        private String success;
    }
}
