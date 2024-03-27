package cn.scu.imc.hiver.bo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "client_node")
@EntityListeners(AuditingEntityListener.class)
public class ClientNode extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String serverName;

    @Column
    private String ip;

    @Column
    private String port;

    /**
     * active:Y  生效中    active != Y   无效
     */
    @Column
    private String active;

    @Column
    private String describeMsg;


}
