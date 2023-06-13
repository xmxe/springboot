
|                           项目名称                           |           介绍            |                             备注                             |
| :----------------------------------------------------------: | :-----------------------: | :----------------------------------------------------------: |
| [springboot-cas-server](https://github.com/xmxe/springboot/tree/master/springboot-cas/springboot-cas-server) |      搭建cas server       | 将自定义的cas_server项目中的pom.xml及src目录替换到官方cas-overlay-template,部署到服务器可完成自定义登录页面，连接数据库校验等功能 |
| [springboot-casclient](https://github.com/xmxe/springboot/tree/master/springboot-cas) |         cas客户端         |                                                              |
| [springboot-security-casclient](https://github.com/xmxe/springboot/tree/master/springboot-cas) | cas+spring security子系统 | spring security不在作登陆校验，校验在cas server端完成，spring security在登陆成功后根据用户名做权限校验 |

## 部署

> 将springboot-cas-server项目中的src目录下放到服务器中的cas-overlay-template目录下启动即可完成自定义登录页
> 想要实现不同子系统不同登录页主要在service目录下配置name-id.json文件,在name-id.json中配置theme

## spring boot+cas单点登陆

- [cas服务器搭建](https://blog.csdn.net/lhc0512/article/details/82466246)
- [手把手教你用代码实现SSO单点登录](https://mp.weixin.qq.com/s/a_WGc4yiYdr8eqqZfRSJHw)
- [搭建CAS-server服务器](https://blog.csdn.net/oumuv/article/details/83377945)
- [cas server官方模板下载](https://github.com/apereo/cas-overlay-template)
- [松哥手把手教你入门Spring Boot + CAS单点登录](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488872&idx=1&sn=3ac483e2e4b58b9940e1aa5458baddd8&chksm=e9c34708deb4ce1eab17c6b9a43d8058558708890a7cfaa053b7effd7f593dd112290d4fed34&scene=158#rd)
- [Spring Boot + Vue + CAS前后端分离实现单点登录方案](https://mp.weixin.qq.com/s/EgyzAQePnCO64ST2W4gtYw)
- [cas查询数据库验证用户](https://blog.csdn.net/zzy730913/article/details/80825800)
- [Spring Boot实现单点登录的第三种方案！](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488913&idx=1&sn=605b35708ddf3b0e6e32a170cd1aea57&chksm=e9c347f1deb4cee795228ba6eb56c928b826e2ff1356f182b6dce2a14c2c0cb209d0a3936b98&scene=158#rd)
- [cas自定义登录页](https://blog.csdn.net/yelllowcong/article/details/79236506)
- [公司产品太多了，怎么实现一次登录产品互通？](https://mp.weixin.qq.com/s/7K5wMb2PICJkBEID20Mtew)