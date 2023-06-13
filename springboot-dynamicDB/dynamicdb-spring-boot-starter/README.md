# SpringBoot多数据源starter组件

## 引入starter依赖
本组件为上传至maven中央仓库，因此需要手动安装到本地仓库后，方可引入组件
```xml
<dependency>
    <groupId>com.gitee.kenewstar.multi.datasource</groupId>
    <artifactId>multi-datasource-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```

## 修改SpringBoot配置文件
default为默认数据源，必须配置， master为可选数据源，名称可自定义。  
数据源的属性名称为对应的dsType数据源类型的属性字段
```yaml
spring:
  datasource:
    multi:
      data-source-prop-map:
        default:
          dsType: com.zaxxer.hikari.HikariDataSource
          jdbcUrl: jdbc:mysql://localhost:3306/test
          username: root
          password: kenewstar
        master:
          dsType: com.zaxxer.hikari.HikariDataSource
          jdbcUrl: jdbc:mysql://localhost:3306/test2
          username: root
          password: kenewstar
```

## 使用multi-datasource
直接在指定的方法上添加@DataSource注解即可，注解的默认值为default,数据源的切换通过注解的值进行切换。
值为application.yml中配置的default,master等  
案例如下：
```java
@Service
public class PersonService {

    @Resource
    private PersonMapper personMapper;

    @DataSource("master")
    @Transactional(rollbackFor = Exception.class)
    public void insertPerson() {
        personMapper.insert(new Person(null, "kk", 12));
        personMapper.insert(new Person(null, "kk", 12));
    }

}
```

## 源码
- [如何自定义SpringBoot多数据源的starter组件](https://mp.weixin.qq.com/s/KxirYIxKJy6v4xge_-NRFg)
- [gitee源码](https://gitee.com/kenewstar/multi-datasource-spring-boot-starter)