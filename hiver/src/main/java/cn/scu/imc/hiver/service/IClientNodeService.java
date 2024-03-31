package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.ClientNode;

import java.util.List;

public interface IClientNodeService {


     boolean save(List<ClientNode> clientNodes);

     ClientNode findById(Integer id);

     List<ClientNode> listAll();

     List<ClientNode> getClientNodeByServer(String serviceName);

     List<ClientNode> getActiveClientNodeByName(String serviceName);

     void update(ClientNode clientNode);

     void delete(Integer id);

}
