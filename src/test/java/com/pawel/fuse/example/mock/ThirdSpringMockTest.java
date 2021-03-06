package com.pawel.fuse.example.mock;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by pmackiewicz on 2016-08-25.
 */
public class ThirdSpringMockTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/third-mock-context.xml");
    }

    @Test
    public void testHelloBean() throws Exception {
        String reply = template.requestBody("direct:hello", "Camel developer", String.class);
        assertEquals("Hello Camel developer", reply);
    }

}

