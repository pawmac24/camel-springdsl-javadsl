package com.pawel.fuse.example;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by pmackiewicz on 2016-08-19.
 */
@Component
public class ContentBasedFileRouter extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file:src/data?noop=true")
            .process(new MyProcessor())
            .choice()
                .when(xpath("/person/city = 'London'"))
                    .log("UK messesage")
                    .to("file:target/messages/uk")
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others");
    }
}
