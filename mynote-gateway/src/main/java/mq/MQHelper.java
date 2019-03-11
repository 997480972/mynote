package mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ 获取连接工具类
 * @author Administrator
 */
public class MQHelper {
	static ConnectionFactory connectionFactory = new ConnectionFactory();
	static {
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
	}
	
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection createConnection(){
		Connection connection = null;
		try {
			connection = connectionFactory.newConnection();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
