package cn.scu.imc.hiver.controller;


import cn.scu.imc.hiver.annotation.NoLoginAccess;
import cn.scu.imc.hiver.bo.BuildHistoryVo;
import cn.scu.imc.hiver.bo.ProjectStatusVo;
import cn.scu.imc.hiver.bo.ProjectVo;
import cn.scu.imc.hiver.entity.Project;
import cn.scu.imc.hiver.service.IBuildService;
import cn.scu.imc.hiver.service.IProjectService;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/project")
public class ProjectController {


    @Resource
    private IProjectService projectService;
    @Resource
    private IBuildService buildService;


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

    @GetMapping(value = "/get/projectStatus")
    public Paging<ProjectStatusVo> getProjectStatuslist(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return projectService.getProjectStatuslist(pageIndex,pageSize);

    }
    @NoLoginAccess
    @PostMapping(value = "/build/projectId/{projectId}")
    public void build(@PathVariable("projectId")Integer projectId) throws IOException {
         projectService.build(projectId);
    }

    @GetMapping(value = "/build/history/projectId/{projectId}")
    public List<BuildHistoryVo> buildHistory(@PathVariable("projectId")Integer projectId) throws IOException {
        return buildService.getHistoryBuild(projectId);
    }

    @GetMapping(value = "/get/logs")
    public List<String> getLogs(){
        return projectService.getLogs();

    }



}
