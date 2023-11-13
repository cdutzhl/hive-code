package cn.scu.imc.user.controller;

import cn.scu.imc.user.utils.SSOFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Object userInfo = request.getSession().getAttribute(SSOFilter.USER_INFO);

        modelAndView.setViewName("index");
        modelAndView.addObject("user", userInfo);

        request.getSession().setAttribute("test","123");
        return modelAndView;
    }
}
