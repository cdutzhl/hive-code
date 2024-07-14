package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.entity.Group;

import java.util.List;

public interface IGroupsService {

     Group addGroup(Group group);

     Group getGroupById(Integer groupId);

     List<Group> listAll();

     boolean update(Group group);

     boolean addUserToGroup(Integer groupId, List<Integer> userIds);

     void deleteUserFromGroup(Integer groupId, List<Integer> userIds);

     boolean isAdmin(Integer groupId);
}
