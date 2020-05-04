package com.nekochips.sign.checkin.service;

import com.alibaba.fastjson.JSONObject;
import com.nekochips.sign.bean.CheckinLog;
import com.nekochips.sign.checkin.props.AutoCheckinProperties;
import com.nekochips.sign.service.ICheckinLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

/**
 * @author NekoChips
 * @description 自动签到公共抽象类
 * @date 2020/5/3
 */
@Slf4j
public abstract class AbstractCheckinService {

    @Autowired
    private AutoCheckinProperties autoSignProperties;

    @Autowired
    private ICheckinLogService checkinLogService;

    /**
     * 自动签到
     *
     * @param serverName 服务名称
     */
    public void autoSign(String serverName) {
        AutoCheckinProperties.Server server = autoSignProperties.getServers().get(serverName);
        if (server == null) {
            log.info("server not exist, please check the name! server name: {}", serverName);
            return;
        }
        String token = getToken(server);
        if (token == null) {
            log.info("token is empty because of unauthenticated.");
            return;
        }
        CheckinLog checkinLog = handleSign(server, token);
        saveLog(checkinLog, serverName);
    }

    /**
     * 记录签到日志
     * @param checkinLog 签到日志
     * @param serverName 服务名称
     */
    protected void saveLog(@Nullable CheckinLog checkinLog, String serverName) {
        if (checkinLog == null) {
            log.info("no checkin log exist, server name: {}", serverName);
            return;
        }
        checkinLog.setServer(serverName);
        checkinLogService.saveLog(checkinLog);
    }

    /**
     * 获取用户 token
     *
     * @param server 服务配置信息
     * @return 用户 token
     */
    public String getToken(AutoCheckinProperties.Server server) {
        JSONObject respJsonObject = login(server);
        AutoCheckinProperties.Response respParams = server.getResponse();
        String code = respJsonObject.getString(respParams.getCode());
        String successCode = respParams.getSuccess();
        String message = respJsonObject.getString(respParams.getMessage());
        if (successCode.equals(code)) {
            return respJsonObject.getString(respParams.getToken());
        }
        log.warn("There are other verifications that need to be processed! The service may not support automatic " +
                "sign-in！code: {}, message: {}", code, message);
        return null;
    }

    
    /**
     * 登录
     *
     * @param server 服务配置信息
     * @return 登录返回 response
     */
    protected abstract JSONObject login(AutoCheckinProperties.Server server);

    /**
     * 处理自动签到
     *
     * @param server 服务配置信息
     * @param token  用户 token
     * @return 签到信息
     */
    protected abstract CheckinLog handleSign(AutoCheckinProperties.Server server, String token);

}
