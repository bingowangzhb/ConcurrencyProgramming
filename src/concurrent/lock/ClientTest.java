package concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dictionary dict = new Dictionary();
		for (int i = 0; i < 3; i++) {
			dict.set("key" + i, "value" + i);
		}
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			exec.execute(new ReaderTask(i, dict));
		}
		exec.execute(new WriterTask(0, dict));
		exec.shutdown();
	}

}
