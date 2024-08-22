package org.example.loginframe.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    /**
     * ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     *
     */

    private String password;
    /**
     * 昵称
     */

    private String nickname;
    /**
     * 邮箱
     */

    private String email;
    /**
     * 头像
     */

    private String userPic;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 修改时间
     */

    private Date updateTime;
    /**
     * 角色
     */

    private String role;


}
