package com.hascode.tutorial.client;
 
import java.io.IOException;
 
import com.hascode.tutorial.config.Configuration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
 
public class SampleConsumer implements Runnable {
 private static final String EXCHANGE_NAME = "test";
 
 private static final String ROUTING_KEY = "test";
 
 private static final boolean NO_ACK = false;
 
 public void run() {
 ConnectionFactory factory = new ConnectionFactory();
 factory.setUsername(Configuration.USERNAME);
 factory.setPassword(Configuration.PASSWORD);
 factory.setHost(Configuration.HOSTNAME);
 factory.setPort(Configuration.PORT);
 Connection conn;
 try {
 conn = factory.newConnection();
 Channel channel = conn.createChannel();
 channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
 String queueName = channel.queueDeclare().getQueue();
 channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);
 QueueingConsumer consumer = new QueueingConsumer(channel);
 channel.basicConsume(queueName, NO_ACK, consumer);
 while (true) { // you might want to implement some loop-finishing
 // logic here ;)
 QueueingConsumer.Delivery delivery;
 try {
 delivery = consumer.nextDelivery();
 System.out.println("received message: " + new String(delivery.getBody()) + " in thread: " + Thread.currentThread().getName());
 channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
 } catch (InterruptedException ie) {
 continue;
 }
 }
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
}