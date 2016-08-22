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

        from("cxf:bean:orderEndpoint").routeId("orderEndpointRouteId")
            .process(new MyProcessor())
            .to("seda:incomingOrders")
            .transform(constant("OK"))
            .log("Operation name from headers = '${header.operationName}' after transform")
            .log("Body = '${body}' after transform");

        from("seda:incomingOrders").routeId("incomingOrdersRouteId")
            .log("Operation name from headers = '${header.operationName}' inside incomingOrders")
            .log("Body = '${body}' inside incomingOrders")
            .to("mock:end");
    }
}
