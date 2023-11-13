package cn.scu.imc.hiver.repository;

import cn.scu.imc.api.vo.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PipelineRepository extends JpaRepository<Pipeline, Integer> {



}
