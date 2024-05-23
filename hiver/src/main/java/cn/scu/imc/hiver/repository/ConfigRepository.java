package cn.scu.imc.hiver.repository;


import cn.scu.imc.hiver.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {


    @Query("select config From Config config where configCode=:configCode and configValue=:configValue and active='Y'")
    Config findByConfigCodeAndConfigValue(@Param(value = "configCode") String configCode,
                                                @Param(value = "configValue") String configValue);


    List<Config> findByConfigCode(@Param(value = "configCode") String configCode);





}
