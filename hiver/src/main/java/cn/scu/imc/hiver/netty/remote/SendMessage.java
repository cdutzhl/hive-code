package cn.scu.imc.hiver.netty.remote;


import cn.scu.imc.hiver.bo.netty.Command;

/**
 *
 *类说明：短信息发送接口
 */
public interface SendMessage {
    /*发送短信*/
    boolean sendMsg(Command command);

}
