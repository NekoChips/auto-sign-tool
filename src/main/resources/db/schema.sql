drop table if exists `log_checkin`;
create table `log_checkin`
(
    `id`           int auto_increment comment 'id',
    `server`       varchar(20)  not null comment '服务名称',
    `username`     varchar(20)  not null comment '用户名称',
    `message`      varchar(512) not null comment '签到信息',
    `checkin_days` int(5)       not null comment '连续签到天数',
    `gmt_create`   date     not null default current_timestamp comment '签到时间',
    `gmt_modified` date comment '更新时间',
    primary key (`id`)
);