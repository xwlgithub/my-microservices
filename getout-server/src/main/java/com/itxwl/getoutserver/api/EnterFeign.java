package com.itxwl.getoutserver.api;

import com.itxwl.getoutserver.api.EnterFeignHystrix.EnterFeignException;
import com.itxwl.getoutserver.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 服务与服务之间的互相消费
 */
@FeignClient(value = "enter-server",fallback = EnterFeignException.class)
@Service
public interface EnterFeign{
    /**
     * 调用enter 服务 查询接口
     * @return
     */
    @GetMapping(value = "user/selectAll")
     List<User> selectAll() ;

}
