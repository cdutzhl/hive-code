package cn.scu.imc.hiver.controller;


import cn.scu.imc.hiver.bo.ClientNode;
import cn.scu.imc.hiver.service.IClientNodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/clientNode")
public class ClientNodeController {


    @Resource
    private IClientNodeService clientNodeService;


    @GetMapping(value = "/get/{id}")
    public Object getUser(@PathVariable("id") Integer id){
        return clientNodeService.findById(id);
    }


    @PutMapping("/add")
    public Object add(@RequestBody List<ClientNode> nodes){
        return clientNodeService.save(nodes);
    }


    @GetMapping("/list")
    public List<ClientNode> list(){
        return clientNodeService.listAll();
    }


    @PostMapping("/update")
    public boolean update(@RequestBody ClientNode clientNode){
        clientNodeService.update(clientNode);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        clientNodeService.delete(id);
        return true;
    }

    @GetMapping("/listNodeByServiceName/{serverName}")
    public List<ClientNode> listNodeByServiceName(@PathVariable("serverName") String serverName){
        return clientNodeService.getClientNodeByServer(serverName);
    }

    @GetMapping("/listActiveClientNodeByName/{serverName}")
    public List<ClientNode> getActiveClientNodeByName(@PathVariable("serverName") String serverName){
        return clientNodeService.getActiveClientNodeByName(serverName);
    }




}
