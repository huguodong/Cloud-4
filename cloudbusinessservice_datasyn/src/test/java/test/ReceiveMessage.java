package test;


//import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 测试用
 * 
 * 消息接收类
 * 
 * @createTime:Apr 7, 2013 5:11:11 PM
 * @version:0.1
 * @lastVersion: 0.1
 * @updateTime:
 * @changesSum:
 * 
 */

/*public class ReceiveMessage implements MessageListener {
	//useInactivityMonitor=false
	private static final String url = "http://localhost:8999";
	private static final String QUEUE_NAME = "TEST";

	public void receiveMessage() {
		Connection connection = null;//wireFormat.maxInactivityDuration=0
		try {
			try {
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
				connectionFactory.setTrustAllPackages(true);
				connection = connectionFactory.createConnection();
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			connection.start();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(QUEUE_NAME);
			// 消息接收者，也就是消费者
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 * protected void consumeMessagesAndClose(Connection connection,Session session, MessageConsumer consumer) throws JMSException {
		while (true){
			//ActiveMQTextMessage
			consumer.setMessageListener(this);
			Message message = consumer.receive(9999);		
			if (message != null) {
				if(message instanceof ActiveMQTextMessage){
					ActiveMQTextMessage mqMsg=(ActiveMQTextMessage)message;
					String closeMsg=mqMsg.getText();
					if("close".equals(closeMsg)){
						consumer.close();
						session.close();
						connection.close();
					}
				}
				//onMessage(message);
			}
		} 
	}
	@Override
	public void onMessage(Message message) {
		
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String msg = txtMsg.getText();
				System.out.println("Received:" + msg);
			}
			System.out.println(message.getJMSMessageID());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		ReceiveMessage rm = new ReceiveMessage();
		rm.receiveMessage();
	}
}*/