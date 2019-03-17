package com.aj.wenxin.service;

import com.gj.utils.resp.ResultVo;

/**
 * @author Archine
 **/
public interface UserService {
    /**
     * 登录或注册
     * @param password  密码
     * @param userName  用户名
     * @return resultVo
     */
    ResultVo loginAndReg(String userName, String password);

    /**
     * 查找用户
     * @param meId 自己的id
     * @param userName 搜索的用户名
     * @return resultVo
     */
    ResultVo searchUser(Long meId, String userName);

}
