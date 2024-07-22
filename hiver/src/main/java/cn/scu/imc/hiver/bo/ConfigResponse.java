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

    public String getLevel() {
        return Integer.valueOf(1).equals(level) ? "系统" : "项目";
    }

    public String getActive() {
        return "Y".equals(active) ? "生效" : "不生效";
    }
}
