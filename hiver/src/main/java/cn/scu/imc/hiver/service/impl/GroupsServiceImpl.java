package cn.scu.imc.hiver.service.impl;


import cn.scu.imc.hiver.entity.GroupUser;
import cn.scu.imc.hiver.entity.Group;
import cn.scu.imc.hiver.repository.GroupRepository;
import cn.scu.imc.hiver.repository.GroupUserRepository;
import cn.scu.imc.hiver.service.IGroupsService;
import cn.scu.imc.hiver.utils.HiveUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsServiceImpl implements IGroupsService {


    @Resource
    private GroupRepository groupRepository;
    @Resource
    private GroupUserRepository groupUserRepository;

    @Override
    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getGroupById(Integer groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (!groupOpt.isPresent()) {
            throw new RuntimeException("当前组不存在");
        }

        return groupOpt.get();
    }

    @Override
    public List<Group> listAll() {
        return groupRepository.findAll();
    }

    @Override
    public boolean update(Group group) {
        groupRepository.save(group);
        return true;
    }

    @Override
    @Transactional
    public boolean addUserToGroup(Integer groupId, List<Integer> userIds) {
        Integer userId = HiveUtil.getCurrentUser().getId();
        List<GroupUser> groupUser = groupUserRepository.findByGroupIdAndUserId(groupId, userId);
        if (!HiveUtil.isAdmin(userId) && groupUser.stream().noneMatch(item -> Integer.valueOf(1).equals(item.getLevel())
                || Integer.valueOf(2).equals(item.getLevel()))) {
            throw new RuntimeException("你不是项目组管理员，无法添加人员");
        }
        userIds.forEach(item -> {
            GroupUser userInGroup = GroupUser.builder()
                    .userId(item)
                    .groupId(groupId)
                    .level(3)
                    .build();
            groupUserRepository.save(userInGroup);
        });
        return true;
    }

    @Override
    @Transactional
    public void deleteUserFromGroup(Integer groupId, List<Integer> userIds) {
        Integer userId = HiveUtil.getCurrentUser().getId();
        List<GroupUser> groupUser = groupUserRepository.findByGroupIdAndUserId(groupId, userId);
        if (!HiveUtil.isAdmin(userId) && groupUser.stream().noneMatch(item -> Integer.valueOf(1).equals(item.getLevel())
                || Integer.valueOf(2).equals(item.getLevel()))) {
            throw new RuntimeException("你不是项目组管理员，无法删除项目组人员");
        }
        groupUserRepository.deleteByGroupIdAndUserId(groupId, userIds);

    }

    @Override
    public boolean isAdmin(Integer groupId) {
        Integer userId = HiveUtil.getCurrentUser().getId();
        List<GroupUser> groupUser = groupUserRepository.findByGroupIdAndUserId(groupId, userId);
        if (HiveUtil.isAdmin(userId) || groupUser.stream().anyMatch(item -> Integer.valueOf(1).equals(item.getLevel())
                || Integer.valueOf(2).equals(item.getLevel()))) {
           return true;
        }
        return false;
    }
}
