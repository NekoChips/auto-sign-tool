package com.nekochips.sign.Mapper;

import com.nekochips.sign.bean.CheckinLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author NekoChips
 * @description CheckinMapper
 * @date 2020/5/3
 */
@Mapper
public interface CheckinLogMapper {

    /**
     * 保存签到日志
     * @param checkinLog 签到日志
     * @return 插入数目
     */
    int insert(CheckinLog checkinLog);

    /**
     * 查询签到日志信息
     * @param serverName 服务名称
     * @return 日志列表
     */
    List<CheckinLog> findByServer(String serverName);
}
