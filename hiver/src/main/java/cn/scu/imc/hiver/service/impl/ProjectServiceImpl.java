package cn.scu.imc.hiver.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.scu.imc.hiver.bo.ProjectVo;
import cn.scu.imc.hiver.entity.Group;
import cn.scu.imc.hiver.entity.GroupUser;
import cn.scu.imc.hiver.entity.Project;
import cn.scu.imc.hiver.repository.GroupUserRepository;
import cn.scu.imc.hiver.repository.ProjectRepository;
import cn.scu.imc.hiver.service.IGroupsService;
import cn.scu.imc.hiver.service.IProjectService;
import cn.scu.imc.hiver.utils.HiveUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void addProject(ProjectVo projectVo) {
        if (CollectionUtil.isNotEmpty(projectRepository.getByProjectName(projectVo.getProjectName()))) {
            throw new RuntimeException("项目名重复");
        }
        Integer createId = HiveUtil.getCurrentUser().getId();
        Project pj = new Project();
       // pj.setStatus(1);
        pj.setProjectName(projectVo.getProjectName());
        pj.setCreateId(createId);
        pj.setCreateDate(new Date());
        pj.setUpdateId(createId);
        pj.setUpdateDate(new Date());
       // pj.setDesc(projectVo.getDesc());
        Project project = projectRepository.save(pj);
        Group group = new Group();
        group.setProjectName(projectVo.getProjectName());
        group.setCreateId(createId);
        group.setCreateDate(new Date());
        group.setUpdateId(createId);
        group.setUpdateDate(new Date());
        groupsService.addGroup(group);
        if(CollectionUtil.isNotEmpty(projectVo.getGroupUsers())) {
            List<GroupUser> groupUser = projectVo.getGroupUsers().stream().map(e -> {
                return GroupUser.builder()
                        .groupId(project.getId())
                        .level(0)
                        .userId(createId)
                        .build();
            }).collect(Collectors.toList());
            groupUserRepository.saveAll(groupUser);
        }
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
