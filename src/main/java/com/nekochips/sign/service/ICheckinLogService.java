package com.nekochips.sign.service;

import com.nekochips.sign.bean.CheckinLog;

import java.util.List;

/**
 * @author NekoChips
 * @description 签到日志逻辑接口
 * @date 2020/5/3
 */
public interface ICheckinLogService {

    /**
     * 保存签到日主
     * @param checkinLog 签到日志记录
     */
    void saveLog(CheckinLog checkinLog);

    /**
     * 查询服务签到日志记录
     * @param serverName 服务名称
     * @return 签到日志列表
     */
    List<CheckinLog> listByServer(String serverName);
}
