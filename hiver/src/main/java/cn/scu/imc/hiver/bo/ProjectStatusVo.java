package cn.scu.imc.hiver.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusVo {

    private Integer id;

    private String projectName;

    private Date lastSuccess;

    private Date lastFail;

    private Time duration;

}
