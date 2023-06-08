package com.pm.papermanagement.mapper;

import com.pm.papermanagement.common.entity.User;
import com.pm.papermanagement.common.model.param.UserParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int addUser(UserParam userParam);

    User selectUserByUsername(String username);
}
