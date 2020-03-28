package com.itxwl.filter;

import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.util.JwtUtils;
import com.itxwl.util.ParamEveryUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;


@Component
public class ZuulFilterEveryServer extends ZuulFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * pre：可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post：在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 优先级为0，数字越大，优先级越低
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * // 是否执行该过滤器，此处为true，说明需要过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //RequestContext currentContext = RequestContext.getCurrentContext();
        //if ((boolean)currentContext.get("isSuccess")==true){
        return true;
        //}
        //return false;
    }

    /**
     * 请求验证-判断请求头是否携带tocken
     * @return
     * @throws
     */
    @Override
    @SuppressWarnings("all")
    public Object run()  {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String method = request.getMethod();
        String accessTocken = request.getHeader(ParamEveryUtil.TOCKEN_PARAM_NAME);
        if (!StringUtils.isEmpty(method)) {
            if (method.equals(ParamEveryUtil.METHOD_TYPE)) {
                return null;
            }
            if (StringUtils.isEmpty(accessTocken)){
                throw  new MyException(ExceptionEnum.AUTH_NO_EVERYS);
            }
            //存放tocken -redis
//            redisTemplate.opsForValue().set(new JwtUtils().parseJwt(accessTocken).getId(),accessTocken);
        }
        return null;
    }
}
