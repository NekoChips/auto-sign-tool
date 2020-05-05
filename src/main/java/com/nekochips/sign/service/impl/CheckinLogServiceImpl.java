package com.nekochips.sign.service.impl;

import com.nekochips.sign.bean.CheckinLog;
import com.nekochips.sign.mapper.CheckinLogMapper;
import com.nekochips.sign.service.ICheckinLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author NekoChips
 * @description ICheckinService 实现类
 * @date 2020/5/3
 */
@Slf4j
@Service
public class CheckinLogServiceImpl implements ICheckinLogService {

    @Autowired
    private CheckinLogMapper checkinMapper;

    @Override
    public void saveLog(CheckinLog checkinLog) {
        checkinMapper.insert(checkinLog);
    }

    @Override
    public List<CheckinLog> listByServer(String serverName) {
        return checkinMapper.findByServer(serverName);
    }
}
