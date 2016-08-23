package com.pawel.fuse.example;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ClaimProcessor {

    public ClaimOutput process(ClaimInput input) throws Exception {
        // get the id of the input
        //ClaimInput input = exchange.getIn().getBody(ClaimInput.class);

        System.out.println("ClaimProcessor process input = " + input);

        // set reply including the id
        ClaimOutput output = new ClaimOutput();
        output.setClaimNo("A00099484");
        output.setCustomerName(input.getCustomerName());
        output.setStatus("DONE");
        //exchange.getOut().setBody(output);
        return output;
    }

    public ClaimStatus cancel(String claimNo) throws Exception {

        System.out.println("ClaimProcessor cancel claimNo = " + claimNo);
        ClaimStatus status = new ClaimStatus();
        status.setClaimNo(claimNo);
        status.setStatus("OK");

        return status;
    }

    public ClaimStatus status(String id, Exchange exchange) throws Exception {
        // get the id of the input
        String myId = exchange.getIn().getBody(String.class);

        System.out.println("ClaimProcessor status id = " + id);
        System.out.println("ClaimInput :[" + ClaimInput.class.getPackage().getName() + "]");

        // set reply including the id
        ClaimStatus status = new ClaimStatus();
        status.setCustomerID(id);
        status.setPolno("A123456789");
        status.setClaimNo("34567789");
        status.setStatus("OK");

        return status;
    }

    public List<String> prepareList(String polno, Exchange exchange) {
        // get the id of the input
        String myPolno = exchange.getIn().getBody(String.class);

        System.out.println("ClaimProcessor polno = " + polno);

        final List<String> params = new ArrayList<String>();
        params.add(polno);

        return params;
    }
}
