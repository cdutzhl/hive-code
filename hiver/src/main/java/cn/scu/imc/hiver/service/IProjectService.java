package cn.scu.imc.hiver.service;

import cn.scu.imc.api.vo.Project;

import java.util.List;

public interface IProjectService {

     void addProject(Project project);

     Project getByProjectId(Integer id);

     void update(Project project);

     List<Project> findAll();

     List<Project> findUserProject (Integer userId) ;

}
