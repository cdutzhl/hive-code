package cn.scu.imc.hiver.bo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "config")
@EntityListeners(AuditingEntityListener.class)
public class Config extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String configCode;

    @Column
    private String configValue;

    @Column
    private String describeMsg;

    /**
     * active:Y  生效中    active != Y   无效
     */
    @Column
    private String active;

    /**
     * level:1 系统级别的配置    level:2 项目级别配置
     */
    @Column(nullable= false)
    private Integer level;

    /**
     * 当配置为项目级别的配置时，groupId为当前配置生效的项目组
     */
    @Column
    private Integer groupId;


}
