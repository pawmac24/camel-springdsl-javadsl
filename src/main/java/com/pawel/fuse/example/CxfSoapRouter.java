package com.pawel.fuse.example;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by pmackiewicz on 2016-08-19.
 */
@Component
public class CxfSoapRouter extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        from("cxf:bean:orderEndpoint")
            .process(new MyProcessor())
            .to("seda:incomingOrders")
            .transform(constant("OK"));

        from("seda:incomingOrders")
            .to("mock:end");
    }
}
