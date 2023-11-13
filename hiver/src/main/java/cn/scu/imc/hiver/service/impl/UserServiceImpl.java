package cn.scu.imc.hiver.service.impl;

import cn.scu.imc.api.vo.User;
import cn.scu.imc.hiver.repository.UserRepository;
import cn.scu.imc.hiver.service.IUserService;
import cn.scu.imc.hiver.utils.JbcryptUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {


    @Resource
    private UserRepository userRepository;

    @Override
    public boolean saveUser(User user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new RuntimeException(String.format("用户:%s 已经存在", user.getUserName()));
        }
        user.setPassword(JbcryptUtil.encode(user.getPassword()));
        if (user.getManager() == null) {
            user.setManager(0);
        }
        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public User findById(Integer id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("用户不存在");
        }
        return userOpt.get();
    }

    @Override
    public User getUserByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(JbcryptUtil.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        User user = findById(id);
        if (user == null) {
            throw new RuntimeException(String.format("用户:%d 不存在", id));
        }
        user.setStatus(2);
        userRepository.save(user);
    }
}
