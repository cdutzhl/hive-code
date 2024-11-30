package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.entity.Build;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildRepository extends PagingAndSortingRepository<Build, Integer> {

    List<Build> findByProjectId(Integer projectId);

}
