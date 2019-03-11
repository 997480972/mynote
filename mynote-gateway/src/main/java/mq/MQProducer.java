package mq;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * 消息生产者
 * @author Administrator
 */
public class MQProducer {
	private String queueName; //队列名
	private String exchange; //交换机名
//	private String routingKey; //路由键
	private Connection connection;
	private Channel channel;

	public MQProducer() throws IOException{
		//获取连接
		connection = MQHelper.createConnection();
		//获得通道
		channel = connection.createChannel();
	}
	/**
	 * 构造工作模式生产者，消息直接发送到队列中
	 * @param queueName
	 * @throws IOException
	 */
	public MQProducer(String queueName) throws IOException{
		this();
		this.queueName = queueName;
		if(StringUtils.isNotBlank(queueName)){
			//声明一个队列
			channel.queueDeclare(queueName, true, false, false, null);
		}
	}
	/**
	 * 路由模式，主题模式，发布订阅模式生产者
	 * 构造使用交换机的生产者，不需要声明队列，消息直接发送到交换机
	 * @param exchange 交换机名
	 * @param type 交换机类型，exchange不为空时，type必传值:direct(路由模式), topic(主题模式), headers, fanout(发布订阅模式)
	 * @throws IOException 
	 */
	public MQProducer(String exchange, String type) throws IOException{
		this();
		this.exchange = exchange;
		//direct
		//如果路由键完全匹配的话,消息才会被投放到相应的队列.
		//fanout
		//当发送一条消息到fanout交换器上时,它会把消息投放到所有附加在此交换器的上的队列.
		//topic
		//设置模糊的绑定方式,"*"操作符将"."视为分隔符,匹配单个单词;"#"操作符没有分块的概念,它将任意"."均视为关键字的匹配部分,能够匹配多个字符.
		if(StringUtils.isNotBlank(exchange)){
			//声明交换机， type可选:direct（处理路由键）, topic, headers, fanout(发布订阅模式：不处理路由键)
			//交换机作用：接受生产者的消息，向队列推送消息
			try {
				channel.exchangeDeclare(exchange, type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		//路由模式：如果路由键完全匹配的话,消息才会被投放到相应的队列.amq.direct是rabbitMQ默认的持久化的交换机
		MQProducer mqProducer = new MQProducer("amq.direct","direct");//
		for (int i = 0; i < 10; i++) {
			mqProducer.send("direct：" +i,"hello");
		}
		mqProducer.close();
		//发布订阅模式: 当发送一条消息到fanout交换器上时,它会把消息投放到所有附加在此交换器的上的队列.
		MQProducer mqProducerFanout = new MQProducer("amq.fanout","fanout");//
		for (int i = 0; i < 10; i++) {
			mqProducerFanout.send("fanout：" +i,"");
		}
		mqProducerFanout.close();
	}
	/**
	 * 发送消息
	 * @param msg 消息
	 * @param routingKey 路由key
	 */
	public void send(String msg, String routingKey){
		if(StringUtils.isBlank(msg)){
			return;
		}
		try {
			if(exchange == null){
				exchange = "";
			}
			if(routingKey == null){
				routingKey = "";
			}
			if(StringUtils.isNotBlank(queueName)){
				routingKey = queueName; //有声明队列，则消息直接发送到队列中
			}
			// 发送消息 ,并持久化消息，mq重启后消息不会丢失
			channel.basicPublish(exchange, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
			System.out.println(" [x] Sent '" + msg + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close(){
		// 关闭通道和连接  
		try {
			if(channel != null){
				channel.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
