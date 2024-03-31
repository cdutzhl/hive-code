package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.bo.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Integer> {



}
