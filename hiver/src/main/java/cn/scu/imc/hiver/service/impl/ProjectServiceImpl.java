package cn.scu.imc.hiver.service.impl;


import cn.scu.imc.hiver.entity.GroupUser;
import cn.scu.imc.hiver.entity.Groups;
import cn.scu.imc.hiver.entity.Project;
import cn.scu.imc.hiver.repository.GroupUserRepository;
import cn.scu.imc.hiver.repository.ProjectRepository;
import cn.scu.imc.hiver.service.IGroupsService;
import cn.scu.imc.hiver.service.IProjectService;
import cn.scu.imc.hiver.utils.HiveUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {


    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private IGroupsService groupsService;
    @Resource
    private GroupUserRepository groupUserRepository;


    @Override
    @Transactional
    public void addProject(Project project) {
        Integer createId = HiveUtil.getCurrentUser().getId();
        Groups group = new Groups();
        group.setProjectName(project.getProjectName());
        groupsService.addGroup(group);
        project.setGroupId(group.getId());
        projectRepository.save(project);
        GroupUser groupUser = GroupUser.builder()
                .groupId(group.getId())
                .level(0)
                .userId(createId)
                .build();
        groupUserRepository.save(groupUser);

    }

    @Override
    public Project getByProjectId(Integer id) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (!projectOpt.isPresent()) {
            throw new RuntimeException("当前项目不存在");
        }
        return projectOpt.get();
    }

    @Override
    public void update(Project project) {
        projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findUserProject(Integer userId) {
       return null;
    }
}
