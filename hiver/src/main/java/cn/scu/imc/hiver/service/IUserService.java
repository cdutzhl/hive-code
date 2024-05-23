package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.UserResponse;
import cn.scu.imc.hiver.entity.User;

import java.util.List;

public interface IUserService {


     boolean saveUser(User user);

     User findById(Integer id);

     User getUserByUsername(String username);

     List<UserResponse> findAll();

     void update(User user);

     void delete(Integer id);
}
