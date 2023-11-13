package cn.scu.imc.api.vo;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(nullable = false)
    private Date createDate;

    @CreatedBy
    @Column(nullable = false)
    private Integer createId;

    @LastModifiedDate
    @Column
    private Date updateDate;

    @Column
    @LastModifiedBy
    private Integer updateId;

    @Data
    @Entity
    @Table(name="pipeline")
    @EntityListeners(AuditingEntityListener.class)
    public static class Pipeline extends BaseEntity implements Serializable {

       private static final long serialVersionUID = 19871928973816127L;


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column
        private Integer projectId;

        @Column()
        private String content;





    }
}
