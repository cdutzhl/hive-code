package cn.scu.imc.hiver.service;

import cn.scu.imc.api.vo.Groups;

import java.util.List;

public interface IGroupsService {

     boolean addGroup(Groups groups);

     Groups getGroupById(Integer groupId);

     List<Groups> listAll();

     boolean update(Groups groups);

     boolean addUserToGroup(Integer groupId, List<Integer> userIds);

     void deleteUserFromGroup(Integer groupId, List<Integer> userIds);

     boolean isAdmin(Integer groupId);
}
