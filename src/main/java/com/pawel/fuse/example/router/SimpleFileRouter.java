package com.pawel.fuse.example.router;

import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by pmackiewicz on 2016-08-24.
 */
public class SimpleFileRouter extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://target/inbox").to("file://target/outbox");
    }
}
