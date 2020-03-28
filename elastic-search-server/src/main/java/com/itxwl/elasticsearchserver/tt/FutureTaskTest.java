package com.itxwl.elasticsearchserver.tt;

import java.util.concurrent.Callable;

public class FutureTaskTest  implements Callable<String> {

    //模拟传入的实体类，用于逻辑处理
    private String json;

    public FutureTaskTest(String json){
        this.json = json;
    }
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"正在执行");
        System.out.println("准备休眠1s,模拟逻辑执行时间");
        //Thread.sleep(1000);
        System.out.println("``````````"+json);
        return json;
    }
}
