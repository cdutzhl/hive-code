package cn.scu.imc.hiver.service.impl;


import cn.scu.imc.hiver.bo.ClientNodeForm;
import cn.scu.imc.hiver.bo.ClientNodeResponse;
import cn.scu.imc.hiver.entity.ClientNode;
import cn.scu.imc.hiver.repository.ClinetNodeRepository;
import cn.scu.imc.hiver.service.IClientNodeService;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientNodeServiceImpl implements IClientNodeService {

    @Resource
    private ClinetNodeRepository clinetNodeRepository;


    @Override
    public boolean save(ClientNodeForm clientNodeForm) {
        ClientNode clientNode = clientNodeForm.getId() != null ? findById(clientNodeForm.getId()) : new ClientNode();
        clientNode.setCode(clientNodeForm.getCode());
        clientNode.setIp(clientNodeForm.getIp());
        clientNode.setPort(clientNodeForm.getPort());
        clientNode.setServerName(clientNodeForm.getServerName());
        clientNode.setActive(clientNodeForm.getActive());
        clientNode.setDescribeMsg(clientNodeForm.getDescribeMsg());
        clinetNodeRepository.save(clientNode);
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
    public Paging<ClientNodeResponse> findAll(int pageIndex, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);
        Page clientNodes = clinetNodeRepository.findAll(pageRequest);
        Paging<ClientNodeResponse> clientNodePaging = new Paging<>();
        clientNodePaging.setTotal(clientNodes.getTotalElements());
        clientNodePaging.setPageSize(pageSize);
        clientNodePaging.setPageIndex(pageIndex);
        List<ClientNode> clientNodeList = clientNodes.getContent();
        List<ClientNodeResponse> configResponse = clientNodeList.stream()
                .map(e -> new ClientNodeResponse(e.getId() ,e.getServerName(), e.getCode(), e.getIp(), e.getPort(),
                        e.getActive(), e.getDescribeMsg())).collect(Collectors.toList());
        clientNodePaging.setData(configResponse);
        return clientNodePaging;
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
