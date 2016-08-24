package com.pawel.fuse.example.mock;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SimpleMockTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {

        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").log("log:first").to("mock:foo");
                from("direct:start2").log("log:second").transform(constant("Bye World")).to("mock:foo2");
            }
        };
    }

    @Test
    public void testMockFoo() throws Exception {
        MockEndpoint resultEndpoint = getMockEndpoint("mock:foo");

        resultEndpoint.expectedMessageCount(2);
        resultEndpoint.expectedBodiesReceived("Hello World", "Hate World");

        template.sendBody("direct:start", "Hello World");
        template.sendBody("direct:start", "Hate World");

        // now lets assert that the mock:foo endpoint received 2 messages
        resultEndpoint.assertIsSatisfied();
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
    }
}
