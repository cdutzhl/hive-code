package com.scu.imc.builder.remote;


import com.scu.imc.builder.remote.vo.UserInfo;

/**

 *
 *类说明：短信息发送接口
 */
public interface SendSms {
    /*发送短信*/
    boolean sendMail(UserInfo user);

}
