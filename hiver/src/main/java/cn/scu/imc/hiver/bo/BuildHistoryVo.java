package cn.scu.imc.hiver.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildHistoryVo {


    private String builder;

    private String duration;

    private Date startDate;

    private String buildStatus;

}
