package com.meteor.module;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class Comunity {

	String host;
	Channel channel;
	Boolean autoACK = true;
	String Message="";
	
	String Coumunity_EX = "Couminity_EX";
	String Coumunity_R_prefix = "id.";
	
	
	public Comunity(String addr) {
		this.host = addr;
		rabbit_createChannel();		
	}
	
	public void rabbit_createChannel(){
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost(host);
		
		
		try {
			Connection conn = cf.newConnection();
			this.channel = conn.createChannel();
			channel.exchangeDeclare(Coumunity_EX, "topic");						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	public void create_sender(String id , String text){
		try{
			channel.basicPublish(Coumunity_EX, Coumunity_R_prefix+id, 
			MessageProperties.PERSISTENT_TEXT_PLAIN, text.getBytes() );
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public void create_customer(){
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		try{
				String queue_name = channel.queueDeclare().getQueue();
				channel.queueBind(queue_name, Coumunity_EX, "id.#");
				channel.basicConsume(queue_name,autoACK,consumer );
				Delivery delivery;
		
		while(true){
				delivery = consumer.nextDelivery();
				Message = new String(delivery.getBody());
				System.out.println(delivery.getEnvelope().getRoutingKey() + " : " + Message);
		}
		}catch(ShutdownSignalException | ConsumerCancelledException
				| InterruptedException | IOException e ){
			e.printStackTrace();
		}
	}
	
	
	
}
