package com.pawel.fuse.example.router;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by pmackiewicz on 2016-08-24.
 */
@Component
public class SimpleMockRouteBuilder extends SpringRouteBuilder{

    @Override
    public void configure() throws Exception {
        from("direct:start").log("log:first").to("mock:foo");
        from("direct:start2").log("log:second").transform(constant("Bye World")).to("mock:foo2");
    }
}
