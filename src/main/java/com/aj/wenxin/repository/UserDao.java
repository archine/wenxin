package com.aj.wenxin.repository;

import com.aj.wenxin.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * @author colin
 * @date 2019-03-11
 **/
public interface UserDao extends BaseMapper<User> {
    User findByName(String userName);
}
