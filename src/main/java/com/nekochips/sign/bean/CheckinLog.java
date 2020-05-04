package com.nekochips.sign.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author NekoChips
 * @description 签到日志
 * @date 2020/5/3
 */
@Data
public class CheckinLog implements Serializable {

    private Long id;

    /**
     * 服务名称
     */
    private String server;

    /**
     * 用户名
     */
    private String username;

    /**
     * 签到信息
     */
    private String message;

    /**
     * 连续签到天数
     */
    private Integer checkinDays;

    /**
     * 签到时间
     */
    private String signTime;
}
