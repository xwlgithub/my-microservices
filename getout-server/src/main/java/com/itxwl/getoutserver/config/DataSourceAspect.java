package com.itxwl.getoutserver.config;

//import com.baomidou.dynamic.datasource.annotation.DS;

import com.baomidou.dynamic.datasource.annotation.DS;
import javassist.*;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
//切面点
//@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
//@EnableAspectJAutoProxy
public class DataSourceAspect implements Ordered {
    //以业务层为切面
    //这块我改成controller之后  可以得
    @Pointcut("execution(* com.itxwl.getoutserver.service.impl..*(..))")//这里改成controller切面
    //@Pointcut("@annotation(com.itxwl.getoutserver.config.DS)")
    public void dataSourcePointcut() {

    }
    @SuppressWarnings("all")
    @Before("dataSourcePointcut()")
    public void before(JoinPoint joinPoint) throws Exception {
        /**
         * 根据用户信息
         */
        Object target = joinPoint.getTarget();
        // String name = joinPoint.getSignature().getName();
        //.forName("com.itxwl.getoutserver.service.impl.CompileShowServiceImpl")
        Class<?> aClass1 = target.getClass();
        //新建类
        ClassPool classPool=ClassPool.getDefault();
        CtClass ctClass = classPool.get(aClass1.getName());
        ClassFile classFile = ctClass.getClassFile();
        List methods1 = classFile.getMethods();

        String name = aClass1.getName();
        System.out.println("类路径" + name);
        Class aClass = aClass1.getClass().forName(name);
        //得到类下得所有方法

        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            //获得注解
            DS ds = method.getAnnotation(DS.class);
            //修改属性值起
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(ds);
            Field f = invocationHandler.getClass().getDeclaredField("memberValues");
            //设置可修改权限
            f.setAccessible(true);
            Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
            //获得注解属性~
            String val = (String) memberValues.get("value");
            System.out.println("改变前:" + val);
            if (val.equals("other")) {
                memberValues.put("value", "mather");
            } else {
                //覆盖之前属性值进行修改
                memberValues.put("value", "other");
            }
            System.out.println("改变后" + memberValues.get("value"));
            Method[] declaredMethods = ds.annotationType().getDeclaredMethods();
            for (Method method1 : declaredMethods) {
                String invoke = (String) method1.invoke(ds, null);
                System.out.println(invoke);
            }
        }
    }

    //
    @SuppressWarnings("all")
     @After("dataSourcePointcut()")
    public void after(JoinPoint joinPoint) throws Exception {
        System.out.println("我啥时候执行");

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
