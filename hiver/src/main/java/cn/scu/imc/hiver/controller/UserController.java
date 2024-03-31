package cn.scu.imc.hiver.controller;


import cn.scu.imc.hiver.bo.User;
import cn.scu.imc.hiver.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private IUserService userService;


    @GetMapping(value = "/get/{id}")
    public Object getUser(@PathVariable("id") Integer id){
        return  userService.findById(id);
    }


    @PutMapping("/add")
    public Object add(@RequestBody User user){
        return userService.saveUser(user);
    }


    @GetMapping("/list")
    public List<User> list(){
        return userService.findAll();
    }


    @PostMapping("/update")
    public boolean update(@RequestBody User user){
        userService.update(user);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        userService.delete(id);
        return true;
    }




}
