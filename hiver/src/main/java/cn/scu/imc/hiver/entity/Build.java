package cn.scu.imc.hiver.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Data
@Entity
@Table(name = "build")
@EntityListeners(AuditingEntityListener.class)
public class Build extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer projectId;

    @Column
    private Integer version;

    @Column
    private Time duration;

    //当前的项目状态  status：0 成功  status：1  失败
    @Column(name = "\"status\"")
    private Integer status;




}
