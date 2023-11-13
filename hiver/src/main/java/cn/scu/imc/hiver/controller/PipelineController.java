package cn.scu.imc.hiver.controller;

import cn.scu.imc.api.vo.Pipeline;
import cn.scu.imc.hiver.service.IPipelineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/pipeline")
public class PipelineController {


    @Resource
    private IPipelineService pipelineService;


    @GetMapping(value = "/get/{id}")
    public Pipeline getProject(@PathVariable("id") Integer id){
        return pipelineService.findById(id);
    }


    @PutMapping("/add")
    public boolean add(@RequestBody Pipeline pipeline){
        pipelineService.save(pipeline);
        return true;
    }


    @PostMapping("/update")
    public boolean update(@RequestBody Pipeline pipeline){
        pipelineService.update(pipeline);
        return true;
    }



}
