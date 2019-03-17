package com.aj.wenxin.service.impl;

import com.aj.wenxin.entity.MyFriend;
import com.aj.wenxin.entity.User;
import com.aj.wenxin.enums.UserEnum;
import com.aj.wenxin.repository.MyFriendDao;
import com.aj.wenxin.repository.UserDao;
import com.aj.wenxin.service.UserService;
import com.gj.utils.GjUtil;
import com.gj.utils.resp.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Archine
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private MyFriendDao myFriendDao;

    @Override
    public ResultVo loginAndReg(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        User userDb = userDao.selectOne(user);
        //注册
        if (userDb == null) {
            user.setPassword(GjUtil.md5(password));
            user.setClientId(UUID.randomUUID().toString());
            boolean b = user.insert();
            if (b) {
                return ResultVo.success();
            }
            return ResultVo.error();
        } else {//登录
            Map<String, Object> map = new HashMap<>();
            if (!userDb.getPassword().equals(GjUtil.md5(password))) {
                return ResultVo.error("密码错误");
            }
            map.put("id", userDb.getId());
            map.put("userName", userDb.getUserName());
            map.put("clientId", userDb.getClientId());
            map.put("faceImg", userDb.getFaceImg());
            return ResultVo.success(map);
        }
    }

    @Override
    public ResultVo searchUser(Long meId, String userName) {
        User user = userDao.findByName(userName);
        if (user == null) {
            return ResultVo.error(UserEnum.NO_EXIST.getTip());
        }
        if (user.getId().equals(meId)) {
            return ResultVo.error(UserEnum.IS_ME.getTip());
        }
        MyFriend myFriend = myFriendDao.selectOne(MyFriend.builder().meId(meId).friendId(user.getId()).build());
        if (myFriend != null) {
            return ResultVo.error(UserEnum.ALREADY_FRIEND.getTip());
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("id", user.getId());
        map.put("userName", user.getUserName());
        map.put("faceImg", user.getFaceImg());
        return ResultVo.success(map);
    }
}
