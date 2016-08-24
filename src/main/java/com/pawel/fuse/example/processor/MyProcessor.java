package com.pawel.fuse.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

/**
 * Created by pmackiewicz on 2016-08-19.
 */
public class MyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        System.out.println("Some processing MessageId : " + exchange.getIn().getMessageId());

        System.out.println("Some headers inside In Message");
        System.out.println("Some processing CamelHttpUri : " + exchange.getIn().getHeader(Exchange.HTTP_URI));
        System.out.println("Some processing operationName : " + exchange.getIn().getHeader("operationName"));
        System.out.println("Some processing Host : " + exchange.getIn().getHeader("Host"));
        System.out.println("Some processing CamelHttpMethod : " + exchange.getIn().getHeader(Exchange.HTTP_METHOD));
        System.out.println("Some processing Content-Type : " + exchange.getIn().getHeader(Exchange.CONTENT_TYPE));

        List<Object> myBody = (List<Object>)exchange.getIn().getBody();
        for (Object myItem : myBody) {
            System.out.println("myItem inside In Body = " + myItem);
        }

        System.out.println("Body inside In Message" + exchange.getIn().getBody());

    }
}
