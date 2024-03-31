package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.bo.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {



}
