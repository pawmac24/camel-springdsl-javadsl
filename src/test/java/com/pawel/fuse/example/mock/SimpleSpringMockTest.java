package com.pawel.fuse.example.mock;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SimpleSpringMockTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/simple-mock-context.xml");
    }

    @Test
    public void testMockFoo() throws Exception {
        MockEndpoint resultEndpoint = getMockEndpoint("mock:foo");

        resultEndpoint.expectedMessageCount(3);
        resultEndpoint.expectedBodiesReceived("Hello World", "Hate World", "Funny World");

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Hate World");
        template.sendBody("direct:start", "Funny World");

        // now lets assert that the mock:foo endpoint received 2 messages
        resultEndpoint.assertIsSatisfied();

        List<Exchange> exchangeList = resultEndpoint.getExchanges();
        assertEquals(3, exchangeList.size());

        assertTrue(exchangeList.get(0).getIn().getBody(String.class).contains("World"));
        assertTrue(exchangeList.get(1).getIn().getBody(String.class).contains("World"));
        assertTrue(exchangeList.get(2).getIn().getBody(String.class).contains("World"));
    }

    @Test
    public void testMockFooInAnyOrder() throws Exception {
        MockEndpoint resultEndpoint = getMockEndpoint("mock:foo");

        resultEndpoint.expectedMessageCount(2);
        resultEndpoint.expectedBodiesReceivedInAnyOrder("Hate World","Hello World");

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Hate World");

        // now lets assert that the mock:foo endpoint received 2 messages
        resultEndpoint.assertIsSatisfied();

        List<Exchange> exchangeList = resultEndpoint.getExchanges();
        assertEquals(2, exchangeList.size());

        assertTrue(exchangeList.get(0).getIn().getBody(String.class).contains("Hello"));
        assertTrue(exchangeList.get(1).getIn().getBody(String.class).contains("Hate"));
    }

    @Test
    public void testMockFoo2() throws Exception {
        MockEndpoint resultEndpoint = getMockEndpoint("mock:foo2");

        resultEndpoint.expectedMessageCount(2);
        resultEndpoint.expectedBodiesReceived("Bye World", "Bye World");

        template.sendBody("direct:start2", "Hello World");
        template.sendBody("direct:start2", "Hate World");

        // now lets assert that the mock:foo2 endpoint received 2 messages
        resultEndpoint.assertIsSatisfied();

        List<Exchange> exchangeList = resultEndpoint.getExchanges();
        assertEquals(2, exchangeList.size());

        assertTrue(exchangeList.get(0).getIn().getBody(String.class).contains("Bye"));
        assertTrue(exchangeList.get(1).getIn().getBody(String.class).contains("Bye"));
    }

}
