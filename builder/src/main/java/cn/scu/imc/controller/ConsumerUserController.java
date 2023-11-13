package cn.scu.imc.controller;

import cn.scu.imc.service.user.IUserClientService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class ConsumerUserController {
    @Resource
    private IUserClientService userClientService;

    @RequestMapping("/getAll")
    public Object getAll(){
        return userClientService.getAll();
    }
    @RequestMapping("/getUserById/{id}")
    public Object getUserById(@PathVariable("id") long id){
        return userClientService.getUserById(id);
    }
}
