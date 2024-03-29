package cn.scu.imc.hiver.service;

import cn.scu.imc.api.vo.Config;

import java.util.List;

public interface IConfigService {


     boolean saveConfig(Config config);

     Config findById(Integer id);

     List<Config> listAll();

     List<Config> listByConfigCode(String configCode);

     Config findByConfigCodeAndConfigValue(String configCode, String configValue);

     void update(Config config);

}
