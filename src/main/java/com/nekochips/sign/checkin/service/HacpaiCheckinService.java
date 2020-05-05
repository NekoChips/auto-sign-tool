package com.nekochips.sign.checkin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nekochips.sign.bean.CheckinLog;
import com.nekochips.sign.checkin.props.AutoCheckinProperties;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NekoChips
 * @description 自动签到服务
 * @date 2020/5/2
 */
@Slf4j
@Service("hacpaiService")
public class HacpaiCheckinService extends AbstractCheckinService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String HACPAI_USERNAME_FIELD = "userName";

    private static final String HACPAI_PASSWORD_FIELD = "userPassword";
    
    @Override
    public CheckinLog handleSign(AutoCheckinProperties.Server server, String token) {
        try {
            Document document = Jsoup.connect(server.getUrl())
                    .userAgent(server.getUa())
                    .cookie(server.getCookieName(), token)
                    .get();

            Elements target = document.select("div.module__body > div.vditor-reset");
            if (target.size() == 0) {
                // 执行签到
                String href = document.select("div.module__body > a.btn").attr("abs:href");
                Connection.Response response = Jsoup.connect(href)
                        .userAgent(server.getUa())
                        .cookie(server.getCookieName(), token)
                        .header(HttpHeaders.REFERER, server.getUrl())
                        .execute();

                // 签到成功生成签到日志
                if (response.statusCode() == HttpStatus.OK.value()) {
                    log.info("sign in success!");
                    Map<String, String> result = parseDocument(response.parse());
                    CheckinLog checkinLog = new CheckinLog();
                    checkinLog.setMessage(result.get("message"));
                    checkinLog.setCheckinDays(Integer.parseInt(result.get("days")));
                    checkinLog.setUsername(server.getLoginParams().get(HACPAI_USERNAME_FIELD));
                    return checkinLog;
                }
            }
            // 已经签到了
            log.info("already signed in today.");
        } catch (IOException e) {
            log.error("connect error, url: {}", server.getUrl(), e);
        }
        return null;
    }

    @Override
    public JSONObject login(AutoCheckinProperties.Server server) {
        Map<String, String> loginParams = new HashMap<>(server.getLoginParams());
        ResponseEntity<JSONObject> respEntity =
                restTemplate.postForEntity(server.getLoginUrl(),
                        buildRequestContent(loginParams, server.getUa()), JSONObject.class);

        if (respEntity.getStatusCode() == HttpStatus.OK) {
            log.info("invoke login service success, url: {}", server.getLoginUrl());
            JSONObject respContent = respEntity.getBody();
            assert respContent != null;
            String code = respContent.getString(server.getResponse().getCode());

            if (server.getResponse().getSuccess().equals(code)) {
                return respContent;
            }
        }
        return new JSONObject();
    }

    /**
     * 构建请求
     * @param params 请求参数
     * @param ua User-Agent
     * @return 请求
     */
    private HttpEntity<JSONObject> buildRequestContent(@Nullable Map<String, String> params, String ua) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.USER_AGENT, ua);

        JSONObject reqContent = new JSONObject();

        if (params != null) {
            params.forEach((x, y) -> {
                if (HACPAI_PASSWORD_FIELD.equals(x)) {
                    params.put(x, DigestUtils.md5DigestAsHex(y.getBytes(StandardCharsets.UTF_8)));
                }
            });
            reqContent = ((JSONObject) JSON.toJSON(params));
        }
        return new HttpEntity<>(reqContent, headers);
    }

    /**
     * 解析页面信息
     *
     * @param document 页面信息
     * @return 页面关键信息
     */
    private Map<String, String> parseDocument(Document document) {
        Map<String, String> result = new HashMap<>(4);
        String text = document.select("h2.sub-head > span.ft-gray").text();
        String[] texts = text.split("，");
        String days = texts[1].substring(3, 4);
        
        Elements elements = document.select("div.module__body > div.vditor-reset");
        StringBuilder message = new StringBuilder(elements.text());
        message
                .append(" 当前")
                .append(document.select("div.module__body > a.btn").text());
        result.put("days", days);
        result.put("message", message.toString());
        return result;
    }
}
