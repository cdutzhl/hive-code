package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.bo.ClientNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinetNodeRepository extends JpaRepository<ClientNode, Integer> {



    List<ClientNode> findByServerName(String serverName);

    @Query(value = "SELECT c from ClientNode c where c.serverName=:serverName and c.active='Y'")
    List<ClientNode> getActiveClientNodeByName(@Param("serverName") String serverName);
}
