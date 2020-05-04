package com.nekochips.sign.service;

import com.nekochips.sign.AutoCheckInToolApplication;
import com.nekochips.sign.bean.CheckinLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author NekoChips
 * @description
 * @date 2020/5/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoCheckInToolApplication.class)
public class CheckinLogServiceTest {

    @Autowired
    private ICheckinLogService checkinLogService;
    
    @Test
    public void test() {
        CheckinLog checkinLog = new CheckinLog();
        checkinLog.setServer("hacpai");
        checkinLog.setUsername("NekoChips");
        checkinLog.setMessage("签到成功");
        checkinLog.setCheckinDays(10);
        checkinLogService.saveLog(checkinLog);
    }
}
