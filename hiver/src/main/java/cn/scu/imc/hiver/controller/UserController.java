package cn.scu.imc.hiver.controller;


import cn.hutool.http.HttpStatus;
import cn.scu.imc.hiver.bo.UserRequestForm;
import cn.scu.imc.hiver.bo.UserResponse;
import cn.scu.imc.hiver.bo.netty.Command;
import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.netty.rpc.client.ClientBusiHandler;
import cn.scu.imc.hiver.netty.rpc.client.FileClientHandler;
import cn.scu.imc.hiver.service.IUserService;
import cn.scu.imc.hiver.utils.Paging;
import cn.scu.imc.hiver.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FileClientHandler fileClientHandler;

    @Autowired
    private ClientBusiHandler clientBusiHandler;

    @GetMapping("/test/order/{order}")
    public void test(String order) throws InterruptedException {
        long start = System.currentTimeMillis();
        Command command = new Command();
        command.setCommands(order);
        clientBusiHandler.send(command);
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
        Thread.sleep(3000);
    }

    @GetMapping("/test")
    public void test1()  {
        SpringContextUtil.getAllBean();
    }






}
