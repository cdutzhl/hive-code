package cn.scu.imc.hiver.bo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ConfigForm implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String configCode;

    private String configValue;

    private String describeMsg;

    private String active;

    private Integer level;

}
