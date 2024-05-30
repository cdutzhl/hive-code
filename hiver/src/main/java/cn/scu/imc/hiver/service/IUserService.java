package cn.scu.imc.hiver.service;


import cn.scu.imc.hiver.bo.UserRequestForm;
import cn.scu.imc.hiver.bo.UserResponse;
import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.utils.Paging;

public interface IUserService {


     boolean saveUser(UserRequestForm user);

     User findById(Integer id);

     User getUserByUsername(String username);

     Paging<UserResponse> findAll(int page, int size);

     void update(User user);

     void delete(Integer id);
}
