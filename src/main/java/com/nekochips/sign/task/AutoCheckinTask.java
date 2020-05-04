package com.nekochips.sign.task;

import com.nekochips.sign.checkin.service.AbstractCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author NekoChips
 * @description 自动签单定时任务
 * @date 2020/5/2
 */
@Component
@EnableScheduling
public class AutoCheckinTask {

    @Autowired
    private AbstractCheckinService signService;

    @Scheduled(cron = "${auto.servers.hacpai.cron}")
    public void hacpaiAutoSign() {
        signService.autoSign("hacpai");
    }
}
