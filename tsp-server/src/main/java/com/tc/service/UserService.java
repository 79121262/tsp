package com.tc.service;


import com.tc.tsp.core.annotation.processor;
import com.tc.tsp.core.annotation.service;

/**
 * Created by cai.tian on 2017/11/30.
 */
@service(version="1.0.0")
@processor(classname="com.tc.service.UserServiceHelper$Processor")
public interface UserService {
    void sayHello();
}
