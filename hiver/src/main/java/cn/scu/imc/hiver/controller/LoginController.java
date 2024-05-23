package cn.scu.imc.hiver.controller;


import cn.scu.imc.hiver.bo.LoginRequest;
import cn.scu.imc.hiver.bo.LoginResponse;
import cn.scu.imc.hiver.entity.User;
import cn.scu.imc.hiver.service.IUserService;
import cn.scu.imc.hiver.utils.HiveUtil;
import cn.scu.imc.hiver.utils.JbcryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws UnsupportedEncodingException {
        String userName = request.getUserName();
        String password = request.getPassword();
        User user = userService.getUserByUsername(userName);
        if (user != null && JbcryptUtil.match(password, user.getPassword())) {
            String token = HiveUtil.getToken(user);
            return ResponseEntity.ok(new LoginResponse(userName, token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }



}