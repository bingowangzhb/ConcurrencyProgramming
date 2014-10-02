package concurrent.keeporder.method1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Keep thread execute order, using a global shared variable to control
 * 
 * @author Charles Chen
 * @date 2014Äê8ÔÂ26ÈÕ
 */
public class ClientTest {
	public static volatile AtomicInteger shared = new AtomicInteger(0);
	public final static int count = 50;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			exec.execute(new PrintTask(i, "String#" + i));
		}

		try {
			TimeUnit.MILLISECONDS.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		exec.shutdownNow();
	}

}
