package cn.scu.imc.hiver.controller;


import cn.scu.imc.hiver.bo.ClientNodeForm;
import cn.scu.imc.hiver.bo.ClientNodeResponse;
import cn.scu.imc.hiver.entity.ClientNode;
import cn.scu.imc.hiver.service.IClientNodeService;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/clientNode")
public class ClientNodeController {


    @Resource
    private IClientNodeService clientNodeService;


    @GetMapping(value = "/get/{id}")
    public Object getUser(@PathVariable("id") Integer id){
        return clientNodeService.findById(id);
    }


    @PutMapping("/add")
    public Object add(@RequestBody ClientNodeForm clientNodeForm){
        return clientNodeService.save(clientNodeForm);
    }

    @GetMapping("/list")
    public Paging<ClientNodeResponse> list(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return clientNodeService.findAll(pageIndex,pageSize);
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
