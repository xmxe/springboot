- **spring.factory在spring boot3.0移除的解决办法**
如果你有探索过这些Starter的原理，那你一定知道Spring Boot并没有消灭这些原本你要配置的Bean，而是将这些Bean做成了一些默认的配置类，同时利用`/META-INF/spring.factories`这个文件来指定要加载的默认配置。这样当Spring Boot应用启动的时候，就会根据引入的各种Starter中的`/META-INF/spring.factories`文件所指定的配置类去加载Bean。而Spring Boot 2.7中，有一个不推荐使用的内容就是关于这个`/META-INF/spring.factories`文件的，在Spring Boot 3开始将移除对`/META-INF/spring.factories`的支持。
![img](https://upload-images.jianshu.io/upload_images/1447174-7f1483a62fe2652f.png?imageMogr2/auto-orient/strip|imageView2/2/w/937/format/webp)

那么具体怎么改呢？下面以之前我们编写的一个swagger的starter为例，它的`/META-INF/spring.factories`内容是这样的：
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.spring4all.swagger.SwaggerAutoConfiguration
```
我们只需要创建一个新的文件：`/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`，内容的话只需要直接放配置类就可以了，比如这样：
```
com.spring4all.swagger.SwaggerAutoConfiguration
```
注意：这里多了一级spring目录。
如果你觉得维护这个太麻烦的话，还可以使用mica-auto来让他自动生成，具体怎么用可以看之前发的[这篇文章](https://links.jianshu.com/go?to=https%3A%2F%2Fblog.didispace.com%2Fspring-factories-mico-auto%2F)。

---

- [如何优雅的实现Spring Boot接口参数加密解密？](https://mp.weixin.qq.com/s/SMC1rGh3ALc4YSN0KojJ1Q)
- [SpringBoot接口层统一加密解密](https://mp.weixin.qq.com/s/mdo99iuBpJcTLwpfj-_Csw)
- [Spring Boot接口数据加解密技巧](https://mp.weixin.qq.com/s/5VJcjjjBE-WRbWuq8Wto4g)
- [不管是spring.factories还是最新的imports文件，这个神器帮你全自动生成！](https://mp.weixin.qq.com/s/ASBRANcdMI2VXflyvD6wiA)
- [spring-boot-starter-swagger](https://github.com/SpringForAll/spring-boot-starter-swagger)
- [图文并茂，Spring Boot Starter万字详解！还有谁不会？](https://mp.weixin.qq.com/s/RqiDZ83Unp1v6yKbehao8A)
- [五分钟说清楚Spring Boot的自动配置原理](https://mp.weixin.qq.com/s/Mh7ltwytt5K0yeqjbQJi_w)
- [Spring Boot自动装配原理，这一篇就够了！](https://mp.weixin.qq.com/s/FRI02LzEGhkVW7V7MvDL6A)
- [Tomcat在SpringBoot中是如何启动的？](https://mp.weixin.qq.com/s/Jh0zv6fkxflWY3IgRL9SvQ)
- [手把手带你剖析Springboot启动原理！](https://mp.weixin.qq.com/s?__biz=Mzg2MDYzODI5Nw==&mid=2247493940&idx=1&sn=a76e59fb9574f7fd5332185416c40ced&source=41#wechat_redirect)
- [一个注解，搞定SpringBoot自动化启动](https://mp.weixin.qq.com/s/S5LXIKkATark3Mg-ki71Kw)
- [SpringBoot自动装配的原理分析](https://mp.weixin.qq.com/s/4-rDXdQBVqLIbdUHPqhu2Q)
- [SpringBoot自动装配原理](https://mp.weixin.qq.com/s/1zdk-Gqh5JcZaUil-JWCUA)
- [一文看懂SpringBoot启动流程！](https://mp.weixin.qq.com/s/YptVdZYAAmZ7UetGBYeUqg)