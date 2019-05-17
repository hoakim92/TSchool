package com.tsystems.lims;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

@MessageDriven(name = "MessageHandler", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/exampleQueue")})
public class MessageHandler implements MessageListener {

    @Inject
    private WsEndPoint endPoint;

    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                System.out.println("Received Message from queue: " + msg.getText());
                endPoint.sendMessage(rcvMessage);
            } else {
                System.out.println("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}