package com.example.rabbitmq;

import com.rabbitmq.client.*;

public class ConsumidorCalcula {
    private static final String FINAL_QUEUE = "fila_teste_final1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(FINAL_QUEUE, false, false, false, null);

        System.out.println(" [*] Aguardando mensagens finais...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            String[] parts = message.split("-");
            int messageNumber = Integer.parseInt(parts[0]);
            long sentTimestamp = Long.parseLong(parts[1]);
            long receivedTimestamp = System.currentTimeMillis();
            long totalLatency = receivedTimestamp - sentTimestamp;

            System.out.println("⏱ Mensagem " + messageNumber + " - Tempo total: " + totalLatency + " ms");
        };

        channel.basicConsume(FINAL_QUEUE, true, deliverCallback, consumerTag -> {});
    }
}
