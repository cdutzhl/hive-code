package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.ProjectVo;
import cn.scu.imc.hiver.entity.Project;

import java.util.List;

public interface IProjectService {

     void addProject(ProjectVo project);

     Project getByProjectId(Integer id);

     void update(Project project);

     List<Project> findAll();

     List<Project> findUserProject (Integer userId) ;

}
