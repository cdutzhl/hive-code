package cn.scu.imc.user.service.impl;


import cn.scu.imc.user.mapper.UserMapper;
import cn.scu.imc.user.service.IUserService;
import cn.scu.imc.user.vo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean saveUser(User user) {

        return userMapper.saveUser(user);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
