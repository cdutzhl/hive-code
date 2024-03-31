package cn.scu.imc.hiver.controller;

import cn.scu.imc.hiver.bo.Config;
import cn.scu.imc.hiver.service.IConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/config")
public class ConfigController {


    @Resource
    private IConfigService configService;


    @GetMapping(value = "/get/{id}")
    public Object getConfig(@PathVariable("id") Integer id){
        return  configService.findById(id);
    }


    @PutMapping("/add")
    public Object add(@RequestBody Config config){
        return configService.saveConfig(config);
    }


    @GetMapping("/list")
    public List<Config> list(){
        return configService.listAll();
    }

    @GetMapping("/listByConfigCode/{configCode}")
    public List<Config> listByConfigCode(@PathVariable("configCode") String configCode){
        return configService.listByConfigCode(configCode);
    }


    @GetMapping("/findConfig/{configCode}/{configValue}")
    public Config findConfigByCodeAndValue(@PathVariable("configCode") String configCode,
                                                 @PathVariable("configValue") String configValue){
        return configService.findByConfigCodeAndConfigValue(configCode, configValue);
    }


    @PostMapping("/update")
    public boolean update(@RequestBody Config config){
        configService.update(config);
        return true;
    }





}
