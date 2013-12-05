package ten.simplews;

public class Client {

	public static void main(String[] args) {

		GreetingImplService service = new GreetingImplService();
		GreetingImpl greeting = service.getGreetingImplPort();
		
		String response = greeting.sayHello("Smithfield. Agent Smith!");
		System.out.println("Response from web service is: " + "\n" + response);

	}

}
