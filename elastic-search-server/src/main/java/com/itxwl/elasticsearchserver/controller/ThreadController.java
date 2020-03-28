package com.itxwl.elasticsearchserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.itxwl.elasticsearchserver.tt.Analye;
import com.itxwl.elasticsearchserver.tt.FutureTaskTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/dd")
public class ThreadController {
    @Autowired
    private ExecutorService executorService;

    @GetMapping("/testfuture")
    public List<String> testfuture() throws ExecutionException, InterruptedException {
        List<String> returns=new LinkedList<>();
        List<String> myListNames=new LinkedList<>();
        myListNames.add("张三");
        myListNames.add("李四");
        myListNames.add("王五");
        List<Future<?>> futures = new ArrayList<Future<?>>(myListNames.size());
        for (int i = 0; i < myListNames.size(); i++) {
            String name = myListNames.get(i);
            try {
                FutureTaskTest task = new FutureTaskTest(name);
                Future<?> future = executorService.submit(task);//异步非阻塞
                futures.add(future);//添加到列表，供后期get
            /*System.out.println(json.toJSONString());
            future.get();*/
            } catch (Exception ex) {
                ex.printStackTrace();
            }/*finally {
            list.add(json);
        }*/

        }
        futures.stream().forEach(item -> {
            try {
                returns.add((String) item.get());//等待执行完成，添加到list，供返回
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

//        long endTime = System.currentTimeMillis();
//        long total = (endTime-startTime)/1000;
        return returns;
    }
}
