package com.itxwl.filter;

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

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String tocken = request.getParameter("access_tocken");
        if (tocken==null||tocken.isEmpty()){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            ctx.setResponseBody("请携带tocken参数!");
        }
        return null;
    }
}
