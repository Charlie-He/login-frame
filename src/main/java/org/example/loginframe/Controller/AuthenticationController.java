package org.example.loginframe.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.loginframe.service.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name="用户认证模块")
@RequestMapping("/api/auth")

public class AuthenticationController {

    @Resource
    private AuthenticationService authenticationService;
    //
//    @GetMapping("/register")
//    @Operation(summary = "注册")
//    public Result register(@RequestBody RegisterReq req){
//        authenticationService.register(req);
//        return Result.success();
//    }
    @GetMapping("/hello")
    public String hello() {
        return "hello!!";
    }

//    @PostMapping("/logout")
//    public Result logout(){
//        log.info("我要登出：：：：：：：");
//        authenticationService.logout();
//        return Result.success("登出成功");
//    }
}
