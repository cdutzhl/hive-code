package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {



}
