package cn.scu.imc.hiver.netty.remote;


import cn.scu.imc.hiver.netty.vo.Command;

/**
 *
 *类说明：短信息发送接口
 */
public interface SendMessage {
    /*发送短信*/
    boolean sendMsg(Command command);

}
