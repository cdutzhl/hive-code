package cn.scu.imc.hiver.service.impl;


import cn.scu.imc.hiver.bo.ConfigForm;
import cn.scu.imc.hiver.bo.ConfigResponse;
import cn.scu.imc.hiver.entity.Config;
import cn.scu.imc.hiver.repository.ConfigRepository;
import cn.scu.imc.hiver.service.IConfigService;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements IConfigService {

    @Resource
    private ConfigRepository configRepository;


    @Override
    public boolean saveConfig(ConfigForm configForm) {
        if(configForm.getId() != null) {
            Config old = configRepository.getOne(configForm.getId());
            old.setConfigCode(configForm.getConfigCode());
            old.setConfigValue(configForm.getConfigValue());
            old.setDescribeMsg(configForm.getDescribeMsg());
            old.setActive(configForm.getActive());
            old.setLevel(configForm.getLevel());
            configRepository.save(old);
        } else {
            Config config = new Config();
            config.setConfigCode(configForm.getConfigCode());
            config.setConfigValue(configForm.getConfigValue());
            config.setDescribeMsg(configForm.getDescribeMsg());
            config.setActive(configForm.getActive());
            config.setLevel(configForm.getLevel());
            configRepository.save(config);
        }
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

    @Override
    public Paging<ConfigResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page configs = configRepository.findAll(pageRequest);
        Paging<ConfigResponse> configPaging = new Paging<>();
        configPaging.setTotal(configs.getTotalElements());
        configPaging.setPageSize(size);
        configPaging.setPageIndex(page);
        List<Config> configList = configs.getContent();
        List<ConfigResponse> configResponse = configList.stream()
                .map(e -> new ConfigResponse(e.getId() ,e.getConfigCode(), e.getConfigValue(), e.getDescribeMsg(),
                        e.getActive(), e.getLevel())).collect(Collectors.toList());
        configPaging.setData(configResponse);
        return configPaging;
    }

    @Override
    public void delete(Integer id) {
        Config user = findById(id);
        if (user == null) {
            throw new RuntimeException(String.format("用户:%d 不存在", id));
        }
        configRepository.delete(user);
    }
}
