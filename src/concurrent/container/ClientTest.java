package concurrent.container;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		Collection<Object> collection = new CopyOnWriteArrayList<Object>();
		for (int i = 0; i < 10; i++) {
			collection.add(new String("String#" + i));
		}
		exec.execute(new IterateContainer(collection));
		exec.execute(new ModifyContainer(collection));
		
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exec.shutdown();
	}
}
