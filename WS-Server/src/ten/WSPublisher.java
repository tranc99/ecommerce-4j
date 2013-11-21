package ten;

import javax.xml.ws.Endpoint;

import ten.jaxws.GreetingImpl;

public class WSPublisher {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/WS/Greeting", new GreetingImpl());
		//Endpoint.create(new GreetingImpl()).stop();
		
		
	}

}
