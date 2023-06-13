package com.xmxe.config.listen;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听器：
 * listener是servlet规范中定义的一种特殊类。用于监听servletContext、HttpSession和servletRequest等域对象的创建和销毁事件。
 * 监听域对象的属性发生修改的事件,用于在事件发生前、发生后做一些必要的处理。其主要可用于以下方面：1、统计在线人数和在线用户2、系统启动时加载初始化信息3、统计网站访问量4、记录用户访问路径。
 */
@Component
public class CustomListener implements ApplicationListener<MyEvent>{

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        myEvent.printMsg();
    }
}

// 开发者可以实现ApplicationListener接口，监听到Spring容器的关闭事件（ContextClosedEvent）,来做一些特殊的处理
// public class MyListener implements ApplicationListener<ContextClosedEvent> {
//     @Override
//     public void onApplicationEvent(ContextClosedEvent event) {
//         // 做容器关闭之前的清理工作
//     }
// }

// public class CustomListener implements ApplicationListener<AvailabilityChangeEvent> {
//     Logger logger = LogManager.getLogger(CustomListener.class);
//     @Override
//     public void onApplicationEvent(AvailabilityChangeEvent availabilityChangeEvent) {
//         logger.info("监听到事件：" + availabilityChangeEvent);
//         if (ReadinessState.ACCEPTING_TRAFFIC == availabilityChangeEvent.getState()){
//             logger.info("应用启动完成，可以请求了……");
//         }
//     }
// }

/*
 * Spring Boot启动事件顺序 ApplicationListener中的泛型的类型,可以在MyEvent里面实现某一个接口来监听不同的事件
 * 1、ApplicationStartingEvent:这个事件在Spring Boot应用运行开始时，且进行任何处理之前发送（除了监听器和初始化器注册之外）。
 * 2、ApplicationEnvironmentPreparedEvent:这个事件在当已知要在上下文中使用Spring环境（Environment）时，在Spring上下文（context）创建之前发送。
 * 3、ApplicationContextInitializedEvent:这个事件在当Spring应用上下文（ApplicationContext）准备好了，并且应用初始化器（ApplicationContextInitializers）已经被调用，在bean的定义（bean definitions）被加载之前发送。
 * 4、ApplicationPreparedEvent:这个事件是在Spring上下文（context）刷新之前，且在bean的定义（bean definitions）被加载之后发送。
 * 5、ApplicationStartedEvent:这个事件是在Spring上下文（context）刷新之后，且在application/command-line runners被调用之前发送。
 * 6、ApplicationReadyEvent:这个事件在任何application/ command-line runners调用之后发送。
 * 7、AvailabilityChangeEvent:这个事件紧随上个事件之后发送，状态：ReadinessState.ACCEPTING_TRAFFIC，表示应用可以开始准备接收请求了。
 * 8、ApplicationFailedEvent:这个事件在应用启动异常时进行发送
 */