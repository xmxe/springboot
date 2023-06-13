package com.xmxe.config;


import com.xmxe.anno.AopAction;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect//表示这是一个切面
/*
 * 当方法符合切点规则不符合环绕通知的规则时候，执行的顺序如下@Before→@After→@AfterRunning(如果有异常→@AfterThrowing)
 * 当方法符合切点规则并且符合环绕通知的规则时候，执行的顺序如下@Around→@Before→@After→@Around执行ProceedingJoinPoint.proceed()之后的操作→@AfterRunning(如果有异常→@AfterThrowing)
 */
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 可以统一定义切点
     * 1.@within是可以通过aop拦截类上的注解
     * 2.@annotation是通过aop拦截方法上的注解
     * 3.@args是可以通过aop拦截参数上的注解
     * 4.@bean拦截bean的id 如bean("aaa") 拦截bean id为aaa的所有方法
     * 5.@this:当前对象(代理对象)类是指定的类或者其子类
     * 6.@target:对前对象(代理对象)的目标对象(被代理对象)是指定的类或者其子类
     * 其中@within()和this()中的区别:一个是程序体,而另一个为对象执行。
     */
    @Pointcut("@annotation(com.xmxe.anno.AopAction)")
    public void pointcut() {}

    /**
     * 统一定义切点
     * 第一个*表示要拦截的目标方法返回值任意(也可以明确指定返回值类型),第二个*表示包中的任意类(也可以明确指定类),第三个*表示类中的任意方法
     * 最后面的两个点表示方法参数任意、个数任意、类型任意 ()表示没有参数,(..)参数不限,(*,String)第一个参数不限类型,第二参数为String.
     */
    @Pointcut("execution(* com.xmxe.service.Aop*.*(..))")
    public void pointcut2() {}

    /**
     * @param joinPoint 包含了目标方法的关键信息
     * @deprecated @Before表示这是一个前置通知,即在目标方法执行之前执行,注解中需要填入切点
     */
    @Before("pointcut() || pointcut2()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        logger.info("@Before==[{}] ",name);
        System.out.println("方法执行前执行......before");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("<=====================================================");
        logger.info("请求来源： =》" + request.getRemoteAddr());
        logger.info("请求URL：" + request.getRequestURL().toString());
        logger.info("请求方式：" + request.getMethod());
        logger.info("响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("请求参数：" + Arrays.toString(joinPoint.getArgs()));
        logger.info("------------------------------------------------------");
    }

    /**
     * 后置通知
     * @param joinPoint 包含了目标方法的所有关键信息
     * @deprecated @After表示这是一个后置通知,即在目标方法执行之后执行
     */
    @After("pointcut() && pointcut2()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        logger.info("@After==[{}] ",name);
    }

    /**
     * @param joinPoint 包含了目标方法的关键信息
     * @deprecated @AfterReturning表示这是一个返回通知,即有目标方法有返回值的时候才会触发
     * 该注解中的returning属性表示目标方法返回值的变量名,这个需要和参数一一对应
     * 注意:目标方法的返回值类型要和这里方法返回值参数的类型一致,否则拦截不到,如果想拦截所有(包括返回值为void),则方法返回值参数可以为Object
     */
    @AfterReturning(value = "pointcut()",returning = "r")
    public void returing(JoinPoint joinPoint,Object r) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        logger.info("@AfterReturning--->r===[{}],name===[{}]",r,name);

        // 1.获取切入点所在目标对象
        Object targetObj = joinPoint.getTarget();
        logger.info("获取切入点所在目标对象==[{}]",targetObj.getClass().getName());
        // 2.获取切入点方法的名字
        String methodName = joinPoint.getSignature().getName();
        logger.info("切入方法名字==[{}]",methodName);
        // 3.获取方法上的注解
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            AopAction apiLog = method.getAnnotation(AopAction.class);
            logger.info("切入方法注解==[{}]",apiLog);
        }

        // 4.获取方法的参数
        Object[] args = joinPoint.getArgs();
        for(Object o :args){
            logger.info("切入方法的参数==[{}]",o);
        }
    }

    /**
     * 异常通知
     * @param joinPoint 包含了目标方法的关键信息
     * @param e 目标方法所抛出的异常,注意这个参数必须是目标方法所抛出的异常或者所抛出的异常的父类,只有这样才会捕获。
     * 如果想拦截所有,参数类型声明为Exception,如果在@Around中捕获了异常,catch中没有重新抛出新的异常不会触发AfterThrowing
     */
    @AfterThrowing(value = "pointcut()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Exception e) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        logger.info("@AfterThrowing--->e===>[{}],name==[{}] ",e.getMessage(),name);

    }

    /**
     * 环绕通知
     * 环绕通知是集大成者,可以用环绕通知实现上面的四个通知,这个方法的核心有点类似于在这里通过反射执行方法
     * @param pjp 相比JoinPoint多个了proceed()方法
     * @return 注意这里的返回值类型最好是Object,和拦截到的方法相匹配
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        Object proceed = null;
        try {
            // 这个相当于method.invoke方法,我们可以在这个方法的前后分别添加日志,就相当于是前置/后置通知
            proceed = pjp.proceed();
            logger.info("try @Around环绕通知-{},{}",pjp.getTarget(),pjp.getSignature());
        } catch (Throwable throwable) {
        	throwable.printStackTrace();
        	logger.error("catch @Around环绕通知");
        	throw new RuntimeException("为了触发AfterThrowing抛出的异常");

        }
        return proceed;
    }
}