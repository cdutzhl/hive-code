package cn.scu.imc.hiver.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Integer id;

    private String userName;

    private String email;

    //1:系统管理员  2：系统辅助管理员 0:普通用户
    private Integer role;

    public String getRole() {
        return Integer.valueOf(1).equals(role) ? "系统管理员" : Integer.valueOf(2).equals(role) ? "系统辅助管理员" : "普通用户";
    }
}
