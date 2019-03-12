package com.aj.wenxin;

import com.aj.wenxin.netty.WsServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author colin
 * @date 2019-03-11
 **/
@Component
@Slf4j
public class NettyBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                WsServer.getInstance().start();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("------------netty启动异常---------------");
            }
        }
    }
}
