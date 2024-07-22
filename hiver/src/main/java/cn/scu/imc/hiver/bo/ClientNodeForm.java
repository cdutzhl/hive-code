package cn.scu.imc.hiver.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientNodeForm implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String serverName;

    private String code;

    private String ip;

    private String port;

    private String active;

    private String describeMsg;

}
