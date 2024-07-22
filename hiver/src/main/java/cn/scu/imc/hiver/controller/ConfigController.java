package cn.scu.imc.hiver.controller;

import cn.hutool.http.HttpStatus;
import cn.scu.imc.hiver.bo.ConfigForm;
import cn.scu.imc.hiver.bo.ConfigResponse;
import cn.scu.imc.hiver.entity.Config;
import cn.scu.imc.hiver.service.IConfigService;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/config")
public class ConfigController {


    @Resource
    private IConfigService configService;


    @GetMapping(value = "/get/{id}")
    public Object getConfig(@PathVariable("id") Integer id){
        return  configService.findById(id);
    }


    @PutMapping("/add")
    public Object add(@RequestBody ConfigForm config){
        return configService.saveConfig(config);
    }



    @GetMapping("/list")
    public Paging<ConfigResponse> list(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return configService.findAll(pageIndex,pageSize);
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



    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable("id") Integer id){
        configService.delete(id);
        return ResponseEntity.status(HttpStatus.HTTP_OK);
    }



}
