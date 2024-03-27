package cn.scu.imc.hiver.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="user")
@EntityListeners(AuditingEntityListener.class)
public class User  extends BaseEntity implements Serializable{

   private static final long serialVersionUID = 19871928973816127L;

    //User Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //User Name
    @Column
    private String userName;
    @Column
    @JSONField(serialize = false)
    private String password;
    //0:正常用户   1：用户被锁定  2：用户被删除
    @Column
    private Integer status;
   //1:系统管理员  2：系统辅助管理员 0:普通用户
    @Column
    private Integer manager;


}
