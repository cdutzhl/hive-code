package cn.scu.imc.hiver.interceptor;


import cn.scu.imc.hiver.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnsupportedEncodingException {
//        String token = request.getHeader("Authorization");
//        if (!(handler instanceof HandlerMethod))  {
//            return true;
//        } else {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            //加了@NoLoginAccess注解的不校验是否登陆
//            NoLoginAccess methodAnnotation = handlerMethod.getMethodAnnotation(NoLoginAccess.class);
//            if (methodAnnotation != null) {
//                return true;
//            }
//
//        }
//        if (StringUtils.isEmpty(token)) {
//            throw new RuntimeException("Token不存在，请先登陆系统");
//        }
//        Integer userId = HiveUtil.getUserId(token);
//        User user = userService.findById(userId);
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//        try {
//            verifier.verify(token);
//        } catch (JWTVerificationException e) {
//            Date expireTime = JWT.decode(token).getExpiresAt();
//            Instant now = Instant.now();
//            if (expireTime.toInstant().isBefore(now)) {
//                throw new RuntimeException("登陆过期，请重新登陆");
//            } else {
//                throw new RuntimeException("Token无效，请重新登陆");
//            }
//        }
//        return true;
//    }
        return true;
    }
}
