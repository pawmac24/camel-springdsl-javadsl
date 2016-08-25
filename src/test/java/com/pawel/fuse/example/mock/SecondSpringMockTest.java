package com.pawel.fuse.example.mock;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by pmackiewicz on 2016-08-25.
 */
public class SecondSpringMockTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("META-INF/spring/second-mock-context.xml");
    }

    @Test
    public void testMockEndpoints() throws Exception {
        MockEndpoint mockFinish = getMockEndpoint("mock:finish");
        MockEndpoint mockFirst = getMockEndpoint("mock:first");

        mockFinish.expectedMessageCount(3);
        mockFinish.expectedBodiesReceived("Funny World", "First Value", "Second Value");
        mockFirst.expectedMessageCount(1);
        mockFirst.expectedBodiesReceived("First Value");

        template.sendBody("direct:start", "Funny World");
        template.sendBodyAndHeader("direct:start", "Hello World", "myCommand", "firstCommand");
        template.sendBodyAndHeader("direct:start", "Hate World", "myCommand", "secondCommand");

        // now lets assert that the mock:xxx endpoint received yyy messages
        mockFirst.assertIsSatisfied();
        mockFinish.assertIsSatisfied();

        //or use this for all mock endpoints
        //assertMockEndpointsSatisfied();
    }

    @Test
    public void testMockAllEndpoints() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                // mock all endpoints
                mockEndpoints();
            }
        });

        MockEndpoint mockFinish = getMockEndpoint("mock:finish");
        MockEndpoint mockFirst = getMockEndpoint("mock:first");
        MockEndpoint mockDirectFirst = getMockEndpoint("mock:direct:first");

        mockFinish.expectedMessageCount(3);
        mockFinish.expectedBodiesReceived("Funny World", "First Value", "Second Value");
        mockFirst.expectedMessageCount(1);
        mockFirst.expectedBodiesReceived("First Value");
        mockDirectFirst.expectedBodiesReceived("Hello World");
        mockDirectFirst.expectedMessageCount(1);

        template.sendBody("direct:start", "Funny World");
        template.sendBodyAndHeader("direct:start", "Hello World", "myCommand", "firstCommand");
        template.sendBodyAndHeader("direct:start", "Hate World", "myCommand", "secondCommand");

        //or use this for all mock endpoints
        assertMockEndpointsSatisfied();

        // additional test to ensure correct endpoints in registry
        assertNotNull(context.hasEndpoint("direct:start"));
        assertNotNull(context.hasEndpoint("direct:first"));
        assertNotNull(context.hasEndpoint("direct:second"));
        assertNotNull(context.hasEndpoint("mock:first"));
        assertNotNull(context.hasEndpoint("mock:second"));
        assertNotNull(context.hasEndpoint("mock:finish"));
        // all the endpoints was mocked
        assertNotNull(context.hasEndpoint("mock:direct:start"));
        assertNotNull(context.hasEndpoint("mock:direct:first"));
        assertNotNull(context.hasEndpoint("mock:direct:second"));
    }

    @Test
    public void testWhatInsideInExchangeInDirectFirst() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                // mock all endpoints
                mockEndpoints();
            }
        });

        MockEndpoint mockDirectFirst = getMockEndpoint("mock:direct:first");
        mockDirectFirst.expectedBodiesReceived("Hello World", "Hate World", "Funny World");
        mockDirectFirst.expectedMessageCount(3);

        template.sendBodyAndHeader("direct:start", "Hello World", "myCommand", "firstCommand");
        template.sendBodyAndHeader("direct:start", "Hate World", "myCommand", "firstCommand");
        template.sendBodyAndHeader("direct:start", "Funny World", "myCommand", "firstCommand");

        //or use this for all mock endpoints
        assertMockEndpointsSatisfied();

        List<Exchange> exchangeList = mockDirectFirst.getExchanges();
        assertEquals(3, exchangeList.size());
        assertTrue(exchangeList.get(0).getIn().getBody(String.class).contains("Hello World"));
        assertTrue(exchangeList.get(1).getIn().getBody(String.class).contains("Hate World"));
        assertTrue(exchangeList.get(2).getIn().getBody(String.class).contains("Funny World"));

        assertTrue(exchangeList.get(0).getIn().getHeaders().containsKey("myCommand"));
        assertTrue(exchangeList.get(1).getIn().getHeaders().containsKey("myCommand"));
        assertTrue(exchangeList.get(2).getIn().getHeaders().containsKey("myCommand"));
        assertTrue(exchangeList.get(0).getIn().getHeaders().containsValue("firstCommand"));
        assertTrue(exchangeList.get(1).getIn().getHeaders().containsValue("firstCommand"));
        assertTrue(exchangeList.get(2).getIn().getHeaders().containsValue("firstCommand"));
    }

    @Test
    public void testWhatInsideInExchangeInDirectFirstSecondVersion() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                // mock only direct:first
                mockEndpoints("direct:first");
            }
        });

        MockEndpoint mockDirectFirst = getMockEndpoint("mock:direct:first");
        mockDirectFirst.expectedBodiesReceived("Hello World", "Hate World", "Funny World");
        mockDirectFirst.message(0).body().contains("World");
        mockDirectFirst.message(0).body().startsWith("Hello");

        mockDirectFirst.message(1).body().contains("World");
        mockDirectFirst.message(1).body().startsWith("Hate");

        mockDirectFirst.message(2).body().contains("World");
        mockDirectFirst.message(2).body().startsWith("Funny");

        //mockDirectFirst.allMessages().body().contains("World");
        mockDirectFirst.expectedMessageCount(3);

        template.sendBodyAndHeader("direct:start", "Hello World", "myCommand", "firstCommand");
        template.sendBodyAndHeader("direct:start", "Hate World", "myCommand", "firstCommand");
        template.sendBodyAndHeader("direct:start", "Funny World", "myCommand", "firstCommand");

        //or use this for all mock endpoints
        assertMockEndpointsSatisfied();
        //
        // check which endpoints was mocked or not mocked
        assertNull(context.hasEndpoint("mock:direct:start"));
        assertNotNull(context.hasEndpoint("mock:direct:first"));
        assertNull(context.hasEndpoint("mock:direct:second"));
    }
}

