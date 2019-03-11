package mq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;
/**
 * 消息消费者
 * @author Administrator
 */
public class MQConsumer {
	private String queueName; //队列名
	private Channel channel;
	
	public MQConsumer() throws IOException{
		//获取连接
		Connection connection = MQHelper.createConnection();
		//获得通道
		channel = connection.createChannel();
	}
	
	public MQConsumer(String queneName, Integer prefetchCount) throws IOException{
		this(queneName, prefetchCount, null, null);
	}
	
	/**
	 * 路由模式，主题模式，发布订阅模式消费者
	 * 队列绑定交换机
	 * @param queneName 队列名
	 * @param prefetchCount 预取消息数量
	 * @param exchange 交换机名
	 * @param routingKey 路由键
	 * @throws IOException
	 */
	public MQConsumer(String queneName, Integer prefetchCount, String exchange, String routingKey) throws IOException{
		this();
		if(queneName != null){
			this.queueName = queneName;
			//声明一个队列
			channel.queueDeclare(queueName, true, false, false, null);
		}
		if(prefetchCount != null){
			//同一时刻通道只会发送几条消息给消费者
            channel.basicQos(prefetchCount);
		}
		if(exchange != null){
			if(routingKey == null){
				routingKey = "";
			}
			//绑定交换机和队列
			channel.queueBind(queueName, exchange, routingKey);
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1.简单模式
		MQConsumer mqConsumer = new MQConsumer("hello",null);
		mqConsumer.receive();
		//2.工作模式: 生产者为消息队列中生产消息,多个消费者争抢执行权利,谁抢到谁执行.实用场景:秒杀业务 抢红包等
		MQConsumer workConsumer = new MQConsumer("hello",null);
		workConsumer.receive();
		//3.发布订阅模式:生产者将消息发送交换机，交换机在将消息发给N个队列，消费者连到响应队列取消息即可，此功能比较适合将某单一系统的简单业务数据消息广播给所有接口
        //应用场景:邮件群发,群聊天,广告
		//it just broadcasts all the messages it receives to all the queues it knows. 
		MQConsumer fanoutMQConsumer = new MQConsumer("hello",null, "amq.fanout", "");
		fanoutMQConsumer.receive();
		//路由模式：如果路由键完全匹配的话,消息才会被投放到相应的队列.amq.direct是rabbitMQ默认的持久化的交换机
		MQConsumer directMQConsumer = new MQConsumer("hello",null, "amq.direct", "");
		directMQConsumer.receive();
	}
	public void receive() {
		try {
	        System.out.println("[*] Waiting for messages.");
	        //消费消息
            boolean autoAck = false;//true：自动确认，消息分发给消费者后就会从内存中；false:手动确认，如果有一个消费者挂掉就会给其他消费者，消费者发送一个消息应答rabbitMQ才会删除消息
            //
            //消息广播到所有绑定的队列中
//            String exchange = "amq.direct";
//            channel.exchangeDeclare(exchange, "fanout");
            // 指定一个队列
//         	channel.queueDeclare(queueName, true, false, false, null);
//            channel.queueBind(queueName, exchange,"hello");
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println(Thread.currentThread() + "消费的路由键：" + routingKey);
                    System.out.println("消费的内容类型：" + contentType);
                    System.out.println("消费的消息体内容：");
                    String bodyStr = new String(body, "UTF-8");
                    System.out.println(bodyStr);
                    long deliveryTag = envelope.getDeliveryTag();
                    System.out.println(deliveryTag);
                    //确认消息
                    channel.basicAck(deliveryTag, false);
                }
            };
            channel.basicConsume(queueName, autoAck, defaultConsumer);
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//		        String message = new String(delivery.getBody(), "UTF-8");
//		        System.out.println(" [x] Received '" + message + "'");
//		        delivery.getEnvelope().getDeliveryTag()
//		    };
//		    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
