package cn.scu.imc.hiver.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pipeline")
@EntityListeners(AuditingEntityListener.class)
public class Pipeline extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer buildId;

    @Column
    private String content;



}
