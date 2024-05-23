package cn.scu.imc.hiver.service.impl;


import cn.scu.imc.hiver.entity.Config;
import cn.scu.imc.hiver.repository.ConfigRepository;
import cn.scu.imc.hiver.service.IConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements IConfigService {

    @Resource
    private ConfigRepository configRepository;


    @Override
    public boolean saveConfig(Config config) {
        configRepository.save(config);
        return true;
    }

    @Override
    public List<Config> listAll() {
        return configRepository.findAll();
    }

    @Override
    public Config findById(Integer id) {
        Optional<Config> configOpt = configRepository.findById(id);
        if(configOpt.isPresent()){
            return configOpt.get();
        }
        throw new RuntimeException(String.format("当前配置(id=%d)不存在", id)) ;
    }

    @Override
    public List<Config> listByConfigCode(String configCode) {
        return configRepository.findByConfigCode(configCode);
    }

    @Override
    public Config findByConfigCodeAndConfigValue(String configCode, String configValue) {
        return configRepository.findByConfigCodeAndConfigValue(configCode, configValue);
    }

    @Override
    public void update(Config config) {
        configRepository.save(config);
    }
}
