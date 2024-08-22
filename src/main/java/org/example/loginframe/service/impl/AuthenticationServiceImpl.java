package org.example.loginframe.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.loginframe.entity.Result;
import org.example.loginframe.entity.dto.User;
import org.example.loginframe.entity.req.RegisterReq;
import org.example.loginframe.entity.vo.AuthorizeVo;
import org.example.loginframe.mapper.UserMapper;
import org.example.loginframe.service.AuthenticationService;
import org.example.loginframe.utils.JwtUtil;
import org.example.loginframe.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AuthenticationServiceImpl extends ServiceImpl<UserMapper, User> implements AuthenticationService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.query().eq("username", username).one();
        //TODO:由于后面登录成功后返回jwt的时候还会需要使用user对象，为了防止多次查询数据库。可以考虑把此次查询到的user信息存入threadlocal中
        UserHolder.ReqInfo reqInfo = new UserHolder.ReqInfo();
        reqInfo.setUser(user);
        UserHolder.saveReqInfo(reqInfo);
        if(user==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    @Override
    public void register(RegisterReq req) {
        User user = new User();
    }

    @Override
    public String SuccessLogin(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = UserHolder.getReqInfo().getUser();
        String token = JwtUtil.createJwt(principal, user.getUsername(), user.getId());
        UserHolder.getReqInfo().setToken(token);

        AuthorizeVo authorizeVo = new AuthorizeVo();
        authorizeVo.setUsername(user.getUsername());
        authorizeVo.setToken(token);
        authorizeVo.setExpire(new Date(System.currentTimeMillis()+JwtUtil.expire));
        authorizeVo.setRole(user.getRole());

        //登录成功，将token写入redis,方便后续登出时的销毁
        stringRedisTemplate.opsForValue().set(token,user.getId().toString(),JwtUtil.expire, TimeUnit.SECONDS);
        return JSONUtil.toJsonStr(Result.success(authorizeVo));
    }
}