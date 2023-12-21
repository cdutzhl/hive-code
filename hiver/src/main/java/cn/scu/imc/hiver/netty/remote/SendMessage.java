package cn.scu.imc.hiver.netty.remote;


import cn.scu.imc.api.vo.netty.Command;

/**
 *
 *类说明：短信息发送接口
 */
public interface SendMessage {
    /*发送短信*/
    boolean sendMsg(Command command);

}
