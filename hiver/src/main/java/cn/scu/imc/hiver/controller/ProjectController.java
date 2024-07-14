package cn.scu.imc.hiver.controller;


import cn.scu.imc.hiver.bo.ProjectVo;
import cn.scu.imc.hiver.entity.Project;
import cn.scu.imc.hiver.service.IProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/project")
public class ProjectController {


    @Resource
    private IProjectService projectService;


    @GetMapping(value = "/get/{id}")
    public Project getProject(@PathVariable("id") Integer id){
        return projectService.getByProjectId(id);
    }


    @PutMapping("/add")
    public boolean add(@RequestBody ProjectVo project){
        projectService.addProject(project);
        return true;
    }


    @GetMapping("/list")
    public List<Project> list(){
        return projectService.findAll();
    }


    @PostMapping("/update")
    public boolean update(@RequestBody Project project){
        projectService.update(project);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        Project project = projectService.getByProjectId(id);
        project.setStatus(2);
        projectService.update(project);
        return true;
    }




}
