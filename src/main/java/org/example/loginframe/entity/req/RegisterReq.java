package org.example.loginframe.entity.req;


import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String repassword;
}

