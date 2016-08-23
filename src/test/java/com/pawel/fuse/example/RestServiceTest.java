package com.pawel.fuse.example;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by pmackiewicz on 2016-08-23.
 */
public class RestServiceTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
    }

    @Test
    public void xxx() throws Exception {

//        String params = "12345";
//        String reply = template.requestBody("cxfrs:bean:statusEndpoint", params, String.class);
//        assertEquals("OK", reply);

    }
}
