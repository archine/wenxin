package com.aj.wenxin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author colin
 * @date 2019-03-11
 **/
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {
        return "hello wenXin";
    }
}
