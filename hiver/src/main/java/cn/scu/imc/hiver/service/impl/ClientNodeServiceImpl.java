package cn.scu.imc.hiver.service.impl;

import cn.scu.imc.api.vo.ClientNode;
import cn.scu.imc.hiver.repository.ClinetNodeRepository;
import cn.scu.imc.hiver.service.IClientNodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ClientNodeServiceImpl implements IClientNodeService {

    @Resource
    private ClinetNodeRepository clinetNodeRepository;


    @Override
    public boolean save(List<ClientNode> clientNodes) {
        clinetNodeRepository.saveAll(clientNodes);
        return true;
    }

    @Override
    public ClientNode findById(Integer id) {
        Optional<ClientNode> clientNodeOpt = clinetNodeRepository.findById(id);
        if (clientNodeOpt.isPresent()) {
            return clientNodeOpt.get();
        }
        throw new RuntimeException("客户端节点不存在");
    }

    @Override
    public List<ClientNode> listAll() {
        return clinetNodeRepository.findAll();
    }

    @Override
    public List<ClientNode> getClientNodeByServer(String serviceName) {
        return clinetNodeRepository.findByServerName(serviceName);
    }

    @Override
    public List<ClientNode> getActiveClientNodeByName(String serviceName) {
        return clinetNodeRepository.getActiveClientNodeByName(serviceName);
    }

    @Override
    public void update(ClientNode clientNode) {
        clinetNodeRepository.save(clientNode);
    }

    @Override
    public void delete(Integer id) {
        clinetNodeRepository.deleteById(id);
    }
}
