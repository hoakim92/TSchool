package com.tsystems.lims;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.websocket.Session;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Named
@ApplicationScoped
@ServerEndpoint("/websocket/message")
public class WsEndPoint {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    public void sendMessage(Message message) throws IOException, JMSException {
        System.out.println("Try to sent message");
        for (Session s : sessions) {
            System.out.println("Session " + s.getId());
            if (s.isOpen()) {
                System.out.println("Session " + s.getId() + " open");
                s.getBasicRemote().sendText(((TextMessage) message).getText());
                System.out.println("Message sent");
            } else {
                System.out.println("Session " + s.getId() + " closed");
                sessions.remove(s);
            }
        }
    }

    @OnMessage
    public String onMessage(String name) {
        System.out.println("Say hello to '" + name + "'");
        return ("Hello " + name + " from websocket endpoint");
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New Session ");
        sessions.add(session);
    }

    @OnClose
    public void onClose(CloseReason reason) {
        System.out.println("WebSocket connection closed with CloseCode: " + reason.getCloseCode());
    }
}
