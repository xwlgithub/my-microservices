package com.itxwl.getoutserver.config;

//import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.itxwl.getoutserver.service.impl.CompileShowServiceImpl;
import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@Component
//切面点
@Order(-50)
@Aspect
@EnableAspectJAutoProxy
public class DataSourceAspect   implements Ordered {

    //以业务层为切面
    @Pointcut("execution(* com.itxwl.getoutserver.service.impl..*(..))")
    //@Pointcut("@annotation(com.itxwl.getoutserver.config.DS)")
    public void dataSourcePointcut() {
    }

    //
    @SuppressWarnings("all")
    @Before("dataSourcePointcut()")
    public  void before(JoinPoint joinPoint) throws Exception {
        /**
         * 根据用户信息
         */
        Object target = joinPoint.getTarget();
        // String name = joinPoint.getSignature().getName();
        //.forName("com.itxwl.getoutserver.service.impl.CompileShowServiceImpl")
        Class aClass1 = target.getClass();
        String name = aClass1.getName();
        //创建新的类
        ClassPool pool = ClassPool.getDefault();
        //如果该类已经存在替换
        CtClass ctClass = pool.get(name);
        System.out.println("当前所在包"+name);
        /**
         *
         */
        Class  aClass=aClass1.getClass().forName("com.itxwl.getoutserver.service.impl.CompileShowServiceImpl");
        //得到类下得所有方法
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            DS ds = method.getAnnotation(DS.class);
            //修改属性值起
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(ds);
            Field f = invocationHandler.getClass().getDeclaredField("memberValues");
            f.setAccessible(true);
            Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
            //获得注解属性~
            String val = (String) memberValues.get("value");
            System.out.println("改变前:" + val);
            if (val.equals("other")){
                memberValues.put("value", "mather");
            }else {
                //覆盖之前属性值进行修改
                memberValues.put("value", "other");
            }
            System.out.println("改变后" + memberValues.get("value"));
//            Method[] declaredMethods = ds.annotationType().getDeclaredMethods();
//            for (Method method1 : declaredMethods) {
//                String invoke = (String) method1.invoke(ds, null);
//                System.out.println(invoke);
//            }
        }
    }
    //
    @SuppressWarnings("all")
    @After("dataSourcePointcut()")
    public void after(JoinPoint joinPoint) throws Exception {
            DynamicDataSourceConextHolder.clearDateSourceType();
        }

    @Override
    public int getOrder() {
        return 2;
    }
}
