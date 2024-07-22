package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.ConfigForm;
import cn.scu.imc.hiver.bo.ConfigResponse;
import cn.scu.imc.hiver.entity.Config;
import cn.scu.imc.hiver.utils.Paging;

import java.util.List;

public interface IConfigService {


     boolean saveConfig(ConfigForm configForm);

     Config findById(Integer id);

     List<Config> listAll();

     List<Config> listByConfigCode(String configCode);

     Config findByConfigCodeAndConfigValue(String configCode, String configValue);

     void update(Config config);

     Paging<ConfigResponse> findAll(int pageIndex, int pageSize);

     void delete(Integer id);
}
