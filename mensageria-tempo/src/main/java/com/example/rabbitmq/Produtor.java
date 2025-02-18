package com.example.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
// import com.rabbitmq.client.MessageProperties;

public class Produtor {
    private static final String QUEUE_NAME = "fila_teste1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            boolean durable = false;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            long startTime = System.currentTimeMillis(); 

            for (int i = 1; i <= 1_000_000; i++) {
                long timestamp = System.currentTimeMillis();
                String message = i + "-" + timestamp;
                channel.basicPublish("", QUEUE_NAME,null, message.getBytes("UTF-8"));

                if (i % 100_000 == 0) {
                    System.out.println("Enviadas: " + i + " mensagens...");
                }
            }

            long endTime = System.currentTimeMillis(); // Tempo final
            System.out.println("⏱ Tempo total de envio: " + (endTime - startTime) + " ms");
        }
    }
}
