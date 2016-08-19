package com.pawel.fuse.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by pmackiewicz on 2016-08-19.
 */
public class MyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Some processing: "
                + exchange.getIn().getHeader("CamelFileName"));

    }
}
