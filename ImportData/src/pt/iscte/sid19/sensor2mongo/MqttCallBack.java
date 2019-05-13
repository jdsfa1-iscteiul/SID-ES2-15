package pt.iscte.sid19.sensor2mongo;

import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


/**
 * Classe que lida com o recebimento de mensagens
 * 
 * @author admin
 *
 */
public class MqttCallBack implements MqttCallback {

	private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private MongoWriter writer;
	
	public MqttCallBack() {
		// start writer
		writer = new MongoWriter(queue);
		writer.start();
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(new String(message.getPayload()));
		queue.offer(new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}
}
