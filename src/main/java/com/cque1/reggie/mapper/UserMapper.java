package com.cque1.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cque1.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
