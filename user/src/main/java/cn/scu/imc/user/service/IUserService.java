package cn.scu.imc.user.service;


import cn.scu.imc.user.vo.User;

import java.util.List;

public interface IUserService {


     boolean saveUser(User user);

     User findById(Long id);

     List<User> findAll();
}
