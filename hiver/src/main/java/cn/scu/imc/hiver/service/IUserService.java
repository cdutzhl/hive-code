package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.User;

import java.util.List;

public interface IUserService {


     boolean saveUser(User user);

     User findById(Integer id);

     User getUserByUsername(String username);

     List<User> findAll();

     void update(User user);

     void delete(Integer id);
}
