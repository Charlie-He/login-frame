package org.example.loginframe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.loginframe.entity.dto.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}