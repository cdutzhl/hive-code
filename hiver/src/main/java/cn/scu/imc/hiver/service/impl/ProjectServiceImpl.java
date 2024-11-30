package cn.scu.imc.hiver.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.scu.imc.hiver.bo.ProjectStatusVo;
import cn.scu.imc.hiver.bo.ProjectVo;
import cn.scu.imc.hiver.entity.Build;
import cn.scu.imc.hiver.entity.Group;
import cn.scu.imc.hiver.entity.GroupUser;
import cn.scu.imc.hiver.entity.Project;
import cn.scu.imc.hiver.repository.BuildRepository;
import cn.scu.imc.hiver.repository.GroupUserRepository;
import cn.scu.imc.hiver.repository.ProjectRepository;
import cn.scu.imc.hiver.service.IBuildService;
import cn.scu.imc.hiver.service.IGroupsService;
import cn.scu.imc.hiver.service.IProjectService;
import cn.scu.imc.hiver.utils.HiveUtil;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements IProjectService {


    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private IGroupsService groupsService;
    @Resource
    private GroupUserRepository groupUserRepository;
    @Resource
    private BuildRepository buildRepository;
    @Resource
    private IBuildService buildService;


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
        pj.setRepository(projectVo.getRepository());
        pj.setPipelineScript(projectVo.getPipelineScript());
        pj.setCreateId(createId);
        pj.setCreateDate(new Date());
        pj.setUpdateId(createId);
        pj.setUpdateDate(new Date());
        pj.setStatus(1);
        pj.setDesc(projectVo.getDesc());
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
        //Iterable<Project> all = projectRepository.findAll();/
        return null;
    }

    @Override
    public List<Project> findUserProject(Integer userId) {
       return null;
    }

    @Override
    public Paging<ProjectStatusVo> getProjectStatuslist(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page projects = projectRepository.findAll(pageRequest);
        Paging<ProjectStatusVo> projectPaging = new Paging<>();
        projectPaging.setTotal(projects.getTotalElements());
        projectPaging.setPageSize(size);
        projectPaging.setPageIndex(page);
        List<Project> projectsList = projects.getContent();
        List<ProjectStatusVo> projectsResponse = new ArrayList<>();
        projectsList.stream().forEach(e -> {
            List<Build> buildHistory = buildRepository.findByProjectId(e.getId());
            Optional<Build> latest = buildHistory.stream().max(Comparator.comparing(Build::getCreateDate));

            ProjectStatusVo projectStatusVo = new ProjectStatusVo();
            projectStatusVo.setId(e.getId());
            projectStatusVo.setProjectName(e.getProjectName());
            if (latest.isPresent()) {
                projectStatusVo.setDuration(HiveUtil.convertSecondsToTime(latest.get().getDuration()));
            }
            Optional<Build> latestSuccess = buildHistory.stream().filter(b -> Integer.valueOf(0).equals(b.getStatus()))
                    .max(Comparator.comparing(Build::getCreateDate));
            latestSuccess.ifPresent(p -> projectStatusVo.setLastSuccess(latestSuccess.get().getCreateDate()));
            Optional<Build> latestFail = buildHistory.stream().filter(b -> Integer.valueOf(1).equals(b.getStatus()))
                    .max(Comparator.comparing(Build::getCreateDate));
            latestFail.ifPresent(p -> projectStatusVo.setLastFail(latestFail.get().getCreateDate()));
            projectsResponse.add(projectStatusVo);

        });
        projectPaging.setData(projectsResponse);
        return projectPaging;
    }

    @Override
    @Transactional
    public void build(Integer projectId) throws IOException {
        Project project = getByProjectId(projectId);
        buildService.build(project.getId());
    }

    @Override
    public List<String> getLogs() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\hive\\workspace\\builder\\1\\build.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            lines.add("Error reading file");
        }
        return lines;

    }
}
