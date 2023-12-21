package cn.scu.imc.api.vo.netty;

public class Command {

    private String commands;

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "Command{" +
                "commands='" + commands + '\'' +
                '}';
    }
}
