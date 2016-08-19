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

        System.out.println("Some processing CamelFileName : " + exchange.getIn().getHeader(Exchange.FILE_NAME) );
        System.out.println("Some processing CamelFileLength : " + exchange.getIn().getHeader(Exchange.FILE_LENGTH));
        System.out.println("Some processing CamelFileAbsolutePath : " +  exchange.getIn().getHeader("CamelFileAbsolutePath"));
        System.out.println("Some processing CamelFilePath : " + exchange.getIn().getHeader(Exchange.FILE_PATH));
        System.out.println("Some processing CamelFileContentType : " + exchange.getIn().getHeader(Exchange.FILE_CONTENT_TYPE));
        System.out.println("Some processing CamelFileLastModified : " + exchange.getIn().getHeader(Exchange.FILE_LAST_MODIFIED));

        System.out.println("Message In in Body " + exchange.getIn().getBody());

    }
}
