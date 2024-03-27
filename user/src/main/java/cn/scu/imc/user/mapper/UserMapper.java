package cn.scu.imc.user.mapper;



import cn.scu.imc.user.vo.User;

import java.util.List;

public interface UserMapper {

    boolean saveUser(User user);

    public User findById(Long id);

    public List<User> findAll();
}
