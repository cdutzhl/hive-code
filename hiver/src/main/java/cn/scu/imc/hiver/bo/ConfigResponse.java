package cn.scu.imc.hiver.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigResponse {


    private Integer id;

    private String configCode;

    private String configValue;

    private String describeMsg;

    /**
     * active:Y  生效中    active != Y   无效
     */
    private String active;

    /**
     * level:1 系统级别的配置    level:2 项目级别配置
     */
    private Integer level;

    

}
