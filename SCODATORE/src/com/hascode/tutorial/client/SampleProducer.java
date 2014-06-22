package com.hascode.tutorial.client;
 
import java.io.IOException;
 
import com.hascode.tutorial.config.Configuration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
 
public class SampleProducer implements Runnable {
 private final String message;
 
 private static final String EXCHANGE_NAME = "test";
 
 private static final String ROUTING_KEY = "test";
 
 public SampleProducer(final String message) {
 this.message = message;
 }
 
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
 System.out.println("Producing message: " + message + " in thread: " + Thread.currentThread().getName());
 channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
 
 channel.close();
 conn.close();
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
}