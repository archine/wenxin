package com.aj.wenxin.controller;

import com.aj.wenxin.service.UserService;
import com.gj.anno.NotNull;
import com.gj.utils.resp.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author colin
 * @date 2019-03-11
 **/
@RestController
public class TestController {
    @Resource
    private UserService userService;

    @RequestMapping("/test")
    public String test() {
        return "hello wenXin";
    }

    @PostMapping("/loginOrReg")
    @NotNull
    public ResultVo loginOrReg(String userName, String password) {
        return userService.loginAndReg(userName, password);
    }

    @PostMapping("/searchUser")
    @NotNull
    public ResultVo searchUser(Long meId, String userName) {
        return userService.searchUser(meId, userName);
    }
}
