package cn.scu.imc.hiver.bo;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
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

}
