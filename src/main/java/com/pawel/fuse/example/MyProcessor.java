package com.pawel.fuse.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by pmackiewicz on 2016-08-19.
 */
public class MyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        System.out.println("Some processing MessageId : " + exchange.getIn().getMessageId());

        System.out.println("Some processing CamelFileName : " + exchange.getIn().getHeader("CamelFileName"));
        System.out.println("Some processing CamelFileLength : " + exchange.getIn().getHeader("CamelFileLength"));
        System.out.println("Some processing CamelFileAbsolutePath : " +  exchange.getIn().getHeader("CamelFileAbsolutePath"));
        System.out.println("Some processing CamelFilePath : " + exchange.getIn().getHeader("CamelFilePath"));
        System.out.println("Some processing CamelFileContentType : " + exchange.getIn().getHeader("CamelFileContentType"));
        System.out.println("Some processing CamelFileLastModified : " + exchange.getIn().getHeader("CamelFileLastModified"));

        System.out.println("Message In in Body " + exchange.getIn().getBody());

    }
}
