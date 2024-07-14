package cn.scu.imc.hiver.controller;

import cn.scu.imc.hiver.entity.Group;
import cn.scu.imc.hiver.service.IGroupsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/group")
public class GroupController {


    @Resource
    private IGroupsService groupsService;


    @GetMapping(value = "/get/{id}")
    public Group getGroup(@PathVariable("id") Integer id){
        Group group = groupsService.getGroupById(id);
        if(group == null){
            throw new RuntimeException(String.format("用户组:%d不存在", id));
        }
        return group;
    }


    @PutMapping("/add")
    public boolean add(@RequestBody Group group){
        groupsService.addGroup(group);
        return true;
    }


    @PutMapping("/add/user/group")
    public boolean addUserToGroup(@RequestParam  Integer groupId,@RequestParam  List<Integer> userIds){
        return groupsService.addUserToGroup(groupId,userIds);
    }



    @PutMapping("/delete/user/group")
    public boolean deleteUserFromGroup(@RequestParam  Integer groupId,@RequestParam  List<Integer> userIds){
        groupsService.deleteUserFromGroup(groupId,userIds);
        return true;
    }

    @GetMapping("/groupAdmin/groupId/{groupId}")
    public boolean isAdmin(@PathVariable("groupId") Integer groupId) {
        return groupsService.isAdmin(groupId);
    }




    @GetMapping("/list")
    public List<Group> list(){
        return groupsService.listAll();
    }


    @PostMapping("/update")
    public boolean update(@RequestBody Group group){
        groupsService.update(group);
        return true;
    }




}
