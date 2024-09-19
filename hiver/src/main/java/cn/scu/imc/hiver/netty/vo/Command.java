package cn.scu.imc.hiver.netty.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Command {

    //项目名
    private String projectName;
    //当前构建版本
    private long version;

    private String stageName;

    private String commands;

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    @Override
    public String toString() {
        return "Command{" +
                "projectName='" + projectName + '\'' +
                ", version=" + version +
                ", stageName='" + stageName + '\'' +
                ", commands='" + commands + '\'' +
                '}';
    }
}
