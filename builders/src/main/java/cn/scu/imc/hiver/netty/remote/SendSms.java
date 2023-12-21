package cn.scu.imc.hiver.netty.remote;

import cn.scu.imc.api.vo.netty.UserInfo;

/**
 *
 *类说明：信息发送接口
 */
public interface SendSms {
    /*发送短信*/
    boolean sendMail(UserInfo user);

}

