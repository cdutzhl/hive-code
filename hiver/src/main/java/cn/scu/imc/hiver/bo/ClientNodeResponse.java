package cn.scu.imc.hiver.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientNodeResponse {


    private Integer id;

    private String serverName;

    private String code;

    private String ip;

    private String port;

    private String active;

    private String describeMsg;

    public String getActive() {
        return "Y".equals(active) ? "生效" : "不生效";
    }
}
