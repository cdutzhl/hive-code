package cn.scu.imc.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.nio.charset.Charset;
import java.util.Base64;

public class AuthorizedFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取当前请求的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //授权信息
        String auth = "admin:cib1234";
        //将账号和密码进行加密处理
        byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
         String info = "Basic "+ new String(encodeAuth);
         //将用户名和密码设置进请求头
        currentContext.addZuulRequestHeader("Authorization",info);
        return null;
    }
}
