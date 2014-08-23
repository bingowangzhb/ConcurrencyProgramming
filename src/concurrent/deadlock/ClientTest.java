package concurrent.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 3;
		// Create chopsticks
		Chopstick[] chopsticks = new Chopstick[count];
		for (int i = 0; i < count; i++) {
			chopsticks[i] = new Chopstick(i);
		}

		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			exec.execute(new Philosopher(i, chopsticks[i], chopsticks[(i + 1)
					% count]));
		}
	}

}
