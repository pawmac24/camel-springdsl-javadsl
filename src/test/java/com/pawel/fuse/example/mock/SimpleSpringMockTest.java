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
        MockEndpoint mockEndPoint = getMockEndpoint("mock:foo");

        mockEndPoint.expectedMessageCount(3);
        mockEndPoint.expectedBodiesReceived("Hello World", "Hate World", "Funny World");

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Hate World");
        template.sendBody("direct:start", "Funny World");

        // now lets assert that the mock:foo endpoint received 2 messages
        mockEndPoint.assertIsSatisfied();

        List<Exchange> exchangeList = mockEndPoint.getExchanges();
        assertEquals(3, exchangeList.size());

        assertTrue(exchangeList.get(0).getIn().getBody(String.class).contains("World"));
        assertTrue(exchangeList.get(1).getIn().getBody(String.class).contains("World"));
        assertTrue(exchangeList.get(2).getIn().getBody(String.class).contains("World"));
    }

    @Test
    public void testIfWorldIsInsideMockFoo() throws Exception {
        MockEndpoint mockEndPoint = getMockEndpoint("mock:foo");

        mockEndPoint.expectedMessageCount(3);
        mockEndPoint.message(0).body().contains("World");
        mockEndPoint.message(1).body().contains("World");
        mockEndPoint.message(2).body().contains("World");

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Hate World");
        template.sendBody("direct:start", "Funny World");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testIfWorldIsInsideMockFooInSimplerWay() throws Exception {
        MockEndpoint mockEndPoint = getMockEndpoint("mock:foo");

        mockEndPoint.expectedMessageCount(3);
        mockEndPoint.allMessages().body().contains("World");

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Hate World");
        template.sendBody("direct:start", "Funny World");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testMockFooInAnyOrder() throws Exception {
        MockEndpoint mockEndPoint = getMockEndpoint("mock:foo");

        mockEndPoint.expectedMessageCount(2);
        mockEndPoint.expectedBodiesReceivedInAnyOrder("Hate World","Hello World");

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Hate World");

        // now lets assert that the mock:foo endpoint received 2 messages
        mockEndPoint.assertIsSatisfied();

        List<Exchange> exchangeList = mockEndPoint.getExchanges();
        assertEquals(2, exchangeList.size());

        assertTrue(exchangeList.get(0).getIn().getBody(String.class).contains("Hello"));
        assertTrue(exchangeList.get(1).getIn().getBody(String.class).contains("Hate"));
    }

    @Test
    public void testMockFoo2() throws Exception {
        MockEndpoint mockEndPoint = getMockEndpoint("mock:foo2");

        mockEndPoint.expectedMessageCount(2);
        mockEndPoint.expectedBodiesReceived("Bye World", "Bye World");

        template.sendBody("direct:start2", "Hello World");
        template.sendBody("direct:start2", "Hate World");

        // now lets assert that the mock:foo2 endpoint received 2 messages
        mockEndPoint.assertIsSatisfied();

        List<Exchange> exchangeList = mockEndPoint.getExchanges();
        assertEquals(2, exchangeList.size());

        assertTrue(exchangeList.get(0).getIn().getBody(String.class).contains("Bye"));
        assertTrue(exchangeList.get(1).getIn().getBody(String.class).contains("Bye"));
    }

    @Test
    public void testExchangeInMockFoo2() throws Exception {
        MockEndpoint mockEndPoint = getMockEndpoint("mock:foo2");

        mockEndPoint.expectedMessageCount(2);
        mockEndPoint.expectedBodiesReceived("Bye World", "Bye World");
        mockEndPoint.allMessages().body().contains("Bye World");
        mockEndPoint.allMessages().headers().isNotNull();

        template.sendBody("direct:start2", "Hello World");
        template.sendBody("direct:start2", "Hate World");

        // now lets assert that the mock:foo2 endpoint received 2 messages
        mockEndPoint.assertIsSatisfied();

        List<Exchange> exchangeList = mockEndPoint.getExchanges();
        assertEquals(2, exchangeList.size());

        assertTrue(exchangeList.get(0).getIn().getHeaders().containsKey("breadcrumbId"));
        assertTrue(exchangeList.get(1).getIn().getHeaders().containsKey("breadcrumbId"));
        assertEquals(1, exchangeList.get(0).getIn().getHeaders().size());
        assertEquals(1, exchangeList.get(1).getIn().getHeaders().size());

        assertNull(exchangeList.get(0).getOut().getBody());
        assertNull(exchangeList.get(1).getOut().getBody());
    }
}
