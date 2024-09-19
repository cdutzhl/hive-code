package cn.scu.imc.hiver.netty.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommandResponse {


    //项目名
    private String projectName;
    //当前构建版本
    private long version;

    private String stageName;

    //命令执行状态
    private boolean status;

    private String content;

}
