package cn.scu.imc.hiver.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserRequestForm implements Serializable {


    private static final long serialVersionUID = 1L;

    private String userName;

    private String email;

    private String password;

    private Integer manager;


}
