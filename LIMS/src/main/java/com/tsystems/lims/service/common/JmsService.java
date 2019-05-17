package com.tsystems.lims.service.common;

import lombok.extern.log4j.Log4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;


@Log4j
public class JmsService {

    public static void sendMessage(String queue, String messageStr) {
        new Thread(() -> {
            Connection connection = null;
            Session session = null;
            try {
                Context namingContext = null;
                final Properties env = new Properties();
                env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
                env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:9090");
                env.put(Context.SECURITY_PRINCIPAL, "jmsuser");
                env.put(Context.SECURITY_CREDENTIALS, "qwerty");
                namingContext = new InitialContext(env);

                ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup("jms/RemoteConnectionFactory");
                System.out.println("Got ConnectionFactory ");

                Destination destination = (Destination) namingContext.lookup(queue);
                System.out.println("Got JMS Endpoint ");

                connection = connectionFactory.createConnection("jmsuser", "qwerty");
                connection.start();
                System.out.println("Got Connection start");

                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                System.out.println("Got Session");

                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                TextMessage message = session.createTextMessage(messageStr);
                producer.send(message);
                System.out.println("Message sent" + messageStr);

            } catch (Exception e) {
                log.info("ERROR " + e.getMessage());
                for (StackTraceElement el :e.getStackTrace())
                    log.info(el.toString());
            }
            finally {
                if(session!=null) {
                    try {
                        session.close();
                        System.out.println("Session closed");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
                if (connection!=null) {
                    try {
                        connection.close();
                        System.out.println("Connection closed");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }


    protected void sendJmsNotification() {
        sendMessage("jms/queue/exampleQueue", "message");
    }
}
