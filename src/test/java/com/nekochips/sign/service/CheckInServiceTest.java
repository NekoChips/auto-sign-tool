package com.nekochips.sign.service;

import com.nekochips.sign.AutoCheckInToolApplication;
import com.nekochips.sign.checkin.props.AutoCheckinProperties;
import com.nekochips.sign.checkin.service.AbstractCheckinService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author NekoChips
 * @description SignService 测试类
 * @date 2020/5/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoCheckInToolApplication.class)
public class CheckInServiceTest {

    @Autowired
    private AbstractCheckinService signService;

    @Autowired
    private AutoCheckinProperties autoCheckinProperties;
    
    @Test
    public void testSign() {
        signService.autoSign("hacpai");
    }

    @Test
    public void testParse() throws IOException {
        AutoCheckinProperties.Server server = autoCheckinProperties.getServers().get("hacpai");
        String token = signService.getToken(server);
        Document document = Jsoup.connect(server.getUrl())
                .userAgent(server.getUa())
                .cookie(server.getCookieName(), token)
                .execute().parse();
        String text = document.select("h2.sub-head > span.ft-gray").text();
        String[] texts = text.split("，");
        Arrays.stream(texts).forEach(System.out::println);
        System.out.println(texts[1].substring(3,4));
    }
}
