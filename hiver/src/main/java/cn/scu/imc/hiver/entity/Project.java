package cn.scu.imc.hiver.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "project")
@EntityListeners(AuditingEntityListener.class)
public class
Project extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String projectName;

    @Column
    private String repository;

    @Column
    private String branch;

    @Column
    private Integer groupId;

    //当前的项目状态  status：1 启用状态  status：0  删除状态
    @Column(name = "\"status\"")
    private Integer status;

    @Column(name = "\"desc\"")
    private String desc;

    @Column(name = "pipeline_script")
    private String pipelineScript;


}
