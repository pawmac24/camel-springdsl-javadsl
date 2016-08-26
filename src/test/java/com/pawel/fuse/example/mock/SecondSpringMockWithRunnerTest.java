package com.pawel.fuse.example.mock;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/second-mock-context.xml"})
public class SecondSpringMockWithRunnerTest {

    @Autowired
    private CamelContext context;

    @Autowired
    private ProducerTemplate template;

    @EndpointInject(uri = "mock:finish")
    MockEndpoint mockFinish;

    @EndpointInject(uri = "mock:first")
    MockEndpoint mockFirst;

    @Test
    public void testMockEndpoints() throws Exception {
        mockFinish.expectedMessageCount(3);
        mockFinish.expectedBodiesReceived("Funny World", "First Value", "Second Value");
        //or in any order
        //mockFinish.expectedBodiesReceivedInAnyOrder("First Value", "Second Value", "Funny World");

        mockFirst.expectedMessageCount(1);
        mockFirst.expectedBodiesReceived("First Value");

        template.sendBody("direct:start", "Funny World");
        template.sendBodyAndHeader("direct:start", "Hello World", "myCommand", "firstCommand");
        template.sendBodyAndHeader("direct:start", "Hate World", "myCommand", "secondCommand");

        // now lets assert that the mock:xxx endpoint received yyy messages
        //mockFirst.assertIsSatisfied();
        //mockFinish.assertIsSatisfied();

        //or use this for all mock endpoints
        MockEndpoint.assertIsSatisfied(context);
    }
}
