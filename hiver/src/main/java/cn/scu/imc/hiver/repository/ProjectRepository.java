package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.entity.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {


    List<Project> getByProjectName(String projectName);
}
