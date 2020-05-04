![](https://img.shields.io/badge/AutoCheckin-v1.0.0-green)
# auto-sign-tool
一款基于 Spring Boot 的 自动登录/签到 小工具。欢迎大佬们来进行优化或集成更多的签到服务。

## 功能
1. 支持 [黑客派社区](https://hacpai.com/) 自动签到功能。

## 版本介绍
### v1.0.0
1. 以配置文件的方式，配置相关参数，如：
```yaml
auto:
  servers:
    ## 黑客派社区服务
    hacpai:
      # 签到地址
      url: https://hacpai.com/activity/checkin
      login-url: https://hacpai.com/api/v2/login
      logout-url: https://hacpai.com/api/v2/logout
      # 登录参数
      login-params:
        userName: test
        userPassword: test
        captcha:
      # 自定义 User-Agent, 部分服务不允许使用默认的 User-Agent
      ua: Auto-Sign/1.0.0
      # cookie 名称
      cookie-name: symphony
      # 定时任务 cron 表达式
      cron: 0 1 0 ? * *
      # response 参数字段，用于解析请求响应
      response:
        code: sc
        message: msg
        token: token
        success: 0
```
2. 提供了公共的模板抽象类 `AbstractCheckinService`，如需添加其它的自动签到功能，只需继承该类并实现抽象方法即可。具体实现可看源码。

## 鸣谢
1. 感谢 [黑客派社区](https://hacpai.com/) 社区提供的开放接口。
