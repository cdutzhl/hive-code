package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.ProjectStatusVo;
import cn.scu.imc.hiver.bo.ProjectVo;
import cn.scu.imc.hiver.entity.Project;
import cn.scu.imc.hiver.utils.Paging;

import java.io.IOException;
import java.util.List;

public interface IProjectService {

     void addProject(ProjectVo project);

     Project getByProjectId(Integer id);

     void update(Project project);

     List<Project> findAll();

     List<Project> findUserProject (Integer userId) ;

     Paging<ProjectStatusVo> getProjectStatuslist(int pageIndex, int pageSize);

     void build(Integer projectId) throws IOException;

     List<String> getLogs();
}
