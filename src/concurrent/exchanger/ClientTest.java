package concurrent.exchanger;

import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientTest {
	public static volatile boolean isEmpty = true;
	
	public static void main(String[]args){
		ExecutorService exec = Executors.newCachedThreadPool();
		Exchanger<List<Product>> exchanger = new Exchanger<List<Product>>();
		int size = 5;
		exec.execute(new Producer(exchanger,size));
		exec.execute(new Consumer(exchanger));
		try {
			TimeUnit.MILLISECONDS.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exec.shutdownNow();
	}
}
