package ten.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class GreetingImpl implements Greeting {

	@WebMethod
	@Override
	public String sayHello(String name) {
		return "Hello, welcome to jax-ws " + name;
	}

	public static void main(String... args) {
		Greeting grt = new GreetingImpl();
		String response = grt.sayHello("Ten");
		assert(response != null);
		System.out.println(response);
	}
}