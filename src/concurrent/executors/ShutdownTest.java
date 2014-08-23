package concurrent.executors;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutdownTest {
	public static synchronized void output(int id) {
		for (int i = 1; i <= 10; i++) {
			System.out.println("#" + id + ": " + i + " output");
		}
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new SimpleTask(1));
		exec.execute(new SimpleTask(2));
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Runnable> unexec = exec.shutdownNow();
		System.out.println(unexec.size());
	}

}

class SimpleTask implements Runnable {
	private int id;

	public SimpleTask(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ShutdownTest.output(id);

	}
}
