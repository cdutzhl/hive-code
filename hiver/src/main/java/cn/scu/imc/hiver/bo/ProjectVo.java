package cn.scu.imc.hiver.bo;


import lombok.Data;

import java.util.List;
@Data
public class ProjectVo {

    private Integer id;

    private String projectName;

    private String desc;

    private List<GroupUserVo> groupUsers;
}