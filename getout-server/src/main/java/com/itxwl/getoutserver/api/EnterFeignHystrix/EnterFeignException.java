package com.itxwl.getoutserver.api.EnterFeignHystrix;

import com.itxwl.getoutserver.api.EnterFeign;
import com.itxwl.getoutserver.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 熔断层
 */
@Service
public class EnterFeignException implements EnterFeign {
    /**
     * 查询失败!->熔断器响应提升信息给前端看
     * @return
     */
    @Override
    public User selectAll(String id) {
        User user=new User();
        user.setName("失败");
        return user;
    }
}
