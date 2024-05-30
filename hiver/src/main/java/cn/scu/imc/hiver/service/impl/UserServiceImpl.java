package cn.scu.imc.hiver.service.impl;


import cn.scu.imc.hiver.bo.UserRequestForm;
import cn.scu.imc.hiver.bo.UserResponse;
import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.repository.UserRepository;
import cn.scu.imc.hiver.service.IUserService;
import cn.scu.imc.hiver.utils.JbcryptUtil;
import cn.scu.imc.hiver.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {


    @Resource
    private UserRepository userRepository;

    @Override
    public boolean saveUser(UserRequestForm userRequestForm) {
        if (userRepository.findByUserName(userRequestForm.getUserName()) != null) {
            throw new RuntimeException(String.format("用户:%s 已经存在", userRequestForm.getUserName()));
        }
        User user = new User();
        user.setUserName(userRequestForm.getUserName());
        user.setPassword(JbcryptUtil.encode(user.getPassword()));
        user.setEmail(userRequestForm.getEmail());
        user.setStatus(0);
        if (user.getManager() == null) {
            user.setManager(0);
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
    public Paging<UserResponse> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page users = userRepository.findAll(pageRequest);
        Paging<UserResponse> userPaging = new Paging<>();
        userPaging.setTotal(users.getTotalElements());
        userPaging.setPageSize(size);
        userPaging.setPageIndex(page);
        List<User> usersList = users.getContent();
        List<UserResponse> userResponse = usersList.stream()
                .map(e -> new UserResponse(e.getId() ,e.getUserName(), e.getEmail(), e.getManager())).collect(Collectors.toList());
        userPaging.setData(userResponse);
        return userPaging;
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
        userRepository.delete(user);
    }
}
