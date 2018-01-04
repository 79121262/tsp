package com.tc.tsp.test;

/**
 * Created by cai.tian on 2017/12/4.
 */
public class client {

    @org.junit.Test
    public void demo() {
        UserService userService = new UserServiceHelper.UserServiceClient();
        userService.sysHello("hello tc");
    }
}
