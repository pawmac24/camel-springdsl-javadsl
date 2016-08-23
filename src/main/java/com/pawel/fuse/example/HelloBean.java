package com.pawel.fuse.example;

import org.springframework.stereotype.Component;

/**
 * Created by pmackiewicz on 2016-08-23.
 */
@Component
public class HelloBean {
    public String hello(String name) {
        return "Hello " + name;
    }
}