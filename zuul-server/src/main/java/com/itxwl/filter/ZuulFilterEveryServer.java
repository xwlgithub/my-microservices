package com.itxwl.filter;

import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Component
public class ZuulFilterEveryServer extends ZuulFilter {
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
     * @throws MyException
     */
    @Override
    public Object run() throws MyException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        String tocken = request.getParameter("access_tocken");
//        if (tocken==null||tocken.isEmpty()){
//            throw  new MyException(ExceptionEnum.TOCKEN_IS_NOTHAVE);
//        }
        return null;
    }
}
