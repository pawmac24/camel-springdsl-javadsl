package com.pawel.fuse.example;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.dataformat.JsonLibrary.Jackson;

/**
 * Created by pmackiewicz on 2016-08-23.
 */
@Component
public class CxfRestRouter extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        from("cxfrs:bean:statusEndpoint").routeId("statusEndpointRouteId")
            .log("${header.operationName}")
            .choice()
                .when(header("operationName").isEqualTo("status"))
                    .log("I am status")
                    .log("Body '${body}' before status")
                    .log("Headers: '${headers}' before status")
                    .to("log:before")
                    .beanRef("claimProcessor", "status")
                    .to("log:after")
                    .log("Body '${body}' after status")
                    .log("Headers: '${headers}' after status")
                .when(header("operationName").isEqualTo("restCancel"))
                    .log("I am restcancel")
                    .log("Body: '${body}' in restCancel")
                    .beanRef("claimProcessor", "prepareList")
            .end()
            .marshal().json(Jackson);
        ;
    }
}
