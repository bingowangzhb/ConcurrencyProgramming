package concurrent.simulate.teller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Cilent test for teller customer service simulation
 * @author Charles Chen
 * @date 2014Äê9ÔÂ4ÈÕ
 */
public class ClientTest {
	private static int MAX_SIZE = 100;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		CustomerQueue customers = new CustomerQueue(MAX_SIZE);
		exec.execute(new CustomerGenerator(customers));
		exec.execute(new CustomerServiceMonitor(customers, 1000, exec));
		System.out.println("Press any keys to exit...");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		exec.shutdownNow();
	}

}
