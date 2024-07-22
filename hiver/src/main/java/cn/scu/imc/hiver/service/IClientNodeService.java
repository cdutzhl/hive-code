package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.ClientNodeForm;
import cn.scu.imc.hiver.bo.ClientNodeResponse;
import cn.scu.imc.hiver.entity.ClientNode;
import cn.scu.imc.hiver.utils.Paging;

import java.util.List;

public interface IClientNodeService {


     boolean save(ClientNodeForm clientNodeForm);

     ClientNode findById(Integer id);

     List<ClientNode> getClientNodeByServer(String serviceName);

     List<ClientNode> getActiveClientNodeByName(String serviceName);

     void update(ClientNode clientNode);

     void delete(Integer id);

     Paging<ClientNodeResponse> findAll(int pageIndex, int pageSize);
}
