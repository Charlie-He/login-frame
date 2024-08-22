package org.example.loginframe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.loginframe.entity.dto.User;
import org.example.loginframe.entity.req.RegisterReq;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService extends IService<User>, UserDetailsService {
    void register(RegisterReq req);


    String SuccessLogin(Authentication authentication);
}
