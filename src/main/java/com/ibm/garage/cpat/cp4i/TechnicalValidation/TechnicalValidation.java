package com.ibm.garage.cpat.cp4i.TechnicalValidation;


import com.ibm.garage.cpat.cp4i.FinancialMessage.FinancialMessage;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.json.JsonObject;


@ApplicationScoped
public class TechnicalValidation {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicalValidation.class);

    // @Incoming annotation denotes the incoming channel that we'll be reading from.
    // The @Outgoing denotes the outgoing channel that we'll be sending to.
    @Incoming("pre-technical-check")
    @Outgoing("post-technical-check")
    @Broadcast
    public Flowable<FinancialMessage> processCompliance(FinancialMessage financialMessage) {

        FinancialMessage receivedMessage = financialMessage;

        LOGGER.info("Message received from topic = {}", receivedMessage);

        if (receivedMessage.technical_validation) {
            /*
            Check whether technical_valiation is true. If so
            we flip the boolean value to indicate that this service has processed it.
            */
            receivedMessage.technical_validation = false;
        
            return Flowable.just(receivedMessage);
        }

        else {
            return Flowable.empty();
        }

        // return (receivedMessage.compliance_services) ? Flowable.just(complianceCheckComplete(receivedMessage)) : Flowable.empty();
    }
}