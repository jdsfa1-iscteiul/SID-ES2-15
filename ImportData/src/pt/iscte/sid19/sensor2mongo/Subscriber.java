package pt.iscte.sid19.sensor2mongo;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Estabelece uma ligação a um broker, subscreve o tópico adequado
 * e delega o tratamento das mensagens ao Callback.
 * @author grupo15
 *
 */

public class Subscriber {
	private static final String TOPIC = "/sid_lab_2019_2";
	private static final String BROKER_ADDRESS = "tcp://broker.mqtt-dashboard.com:1883";
	
	private MqttClient client;
	
	public Subscriber() {
		try {
			connect();
		} catch(MqttException e) {
			System.out.println("Something went wrong while establishing the connection to the broker");
			System.exit(1);
		}
	}

	private void connect() throws MqttException {
		client = new MqttClient(BROKER_ADDRESS, MqttClient.generateClientId());
		client.setCallback(new MqttCallBack());
		
		// set connection options
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		
		client.connect(options);
		client.subscribe(TOPIC);
	}
	
	public static void main(String[] args) {
		new Subscriber();
	}
}
