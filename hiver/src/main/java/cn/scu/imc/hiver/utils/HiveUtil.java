package cn.scu.imc.hiver.utils;

import cn.hutool.core.date.DateUtil;
import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.service.IUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class HiveUtil {

    private static final String STANDARD_DATE  = "yyyy-HH-mm HH:mm:ss";

    private static IUserService staticUservice;

    @PostConstruct
    public void setStaticUservice(){
        staticUservice = userService;
    }

    @Resource
    private IUserService userService;

    public static String getToken(User user) throws UnsupportedEncodingException {
        return JWT.create().withAudience(user.getId().toString())
                .withSubject(user.getUserName())
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }


    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
                throw new RuntimeException("请先登录");
        }
        String userId = JWT.decode(token).getAudience().get(0);
        return staticUservice.findById(Integer.valueOf(userId));
    }


    public static Integer getUserId(String token) {
        String userId = JWT.decode(token).getAudience().get(0);
        return Integer.valueOf(userId);
    }

    public static boolean isAdmin(Integer userId) {
        User user = staticUservice.findById(userId);
        if (Integer.valueOf(0).equals(user.getStatus()) && Integer.valueOf(1).equals(user.getManager())
                || Integer.valueOf(2).equals(user.getManager())) {
            return true;
        }
        return false;

    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String now() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_DATE);
        return simpleDateFormat.format(new Date());
    }



}
