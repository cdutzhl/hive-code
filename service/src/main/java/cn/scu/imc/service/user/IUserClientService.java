package cn.scu.imc.service.user;

import cn.scu.imc.api.vo.User;
import cn.scu.imc.service.conf.FeignClientConf;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "USER-CLIENT" ,configuration = FeignClientConf.class)
public interface IUserClientService {


    @RequestMapping("/user/insert")
    public void insertUser(User user);


    @RequestMapping("/user/getUserById/{id}")
    public Object getUserById(@PathVariable("id") long id);


    @RequestMapping("/user/getAll")
    public Object getAll();
}
