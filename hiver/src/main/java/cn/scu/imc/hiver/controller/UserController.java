package cn.scu.imc.hiver.controller;


import cn.hutool.http.HttpStatus;
import cn.scu.imc.hiver.bo.UserRequestForm;
import cn.scu.imc.hiver.bo.UserResponse;
import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.service.IUserService;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/user")
public class UserController {


    @Resource
    private IUserService userService;


    @GetMapping(value = "/get/{id}")
    public Object getUser(@PathVariable("id") Integer id){
        return  userService.findById(id);
    }


    @PutMapping("/add")
    public Object add(@RequestBody @Validated UserRequestForm userRequestForm){
        userService.saveUser(userRequestForm);
        return ResponseEntity.status(HttpStatus.HTTP_OK);
    }


    @GetMapping("/list")
    public Paging<UserResponse> list(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return userService.findAll(pageIndex,pageSize);
    }


    @PostMapping("/update")
    public boolean update(@RequestBody User user){
        userService.update(user);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable("id") Integer id){
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.HTTP_OK);
    }




}
