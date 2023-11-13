package cn.scu.imc.user.controller;

import cn.scu.imc.api.vo.User;
import cn.scu.imc.user.service.IUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private IUserService userService;


    @RequestMapping(value = "/get/{id}")
    public Object getProduct(@PathVariable("id") long id){
        User user = userService.findById(id);
        if(user == null){
            throw new RuntimeException("商品已下架");
        }
        return user;
    }

    @RequestMapping("/add")
    public Object add(@RequestBody User user){
        return userService.saveUser(user);
    }
    @RequestMapping("/list")
    public  Object list(){
        return userService.findAll();
    }



}
