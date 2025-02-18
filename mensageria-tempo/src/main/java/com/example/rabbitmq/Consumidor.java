package com.example.rabbitmq;

import com.rabbitmq.client.*;

public class Consumidor {
    private static final String QUEUE_NAME = "fila_teste1";
    private static final String FINAL_QUEUE = "fila_teste_final1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Channel finalChannel = connection.createChannel();

        boolean durable = false; 
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        finalChannel.queueDeclare(FINAL_QUEUE, durable, false, false, null);

        System.out.println(" [*] Aguardando mensagens...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            String[] parts = message.split("-");
            int messageNumber = Integer.parseInt(parts[0]);
            long sentTimestamp = Long.parseLong(parts[1]);
            long receivedTimestamp = System.currentTimeMillis();
            long latency = receivedTimestamp - sentTimestamp;

            System.out.println("📩 Mensagem " + messageNumber + " - Latência: " + latency + " ms");

            // Enviar mensagens 1 e 1.000.000 para a nova fila
            if (messageNumber == 1 || messageNumber == 1_000_000) {
                finalChannel.basicPublish("", FINAL_QUEUE, null, message.getBytes("UTF-8"));
            }

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});
    }
}
