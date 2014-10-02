package concurrent.keeporder.method2;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Keep thread execute order, using lock to control
 * 
 * @author Charles Chen
 * @date 2014Äê8ÔÂ26ÈÕ
 */
public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		int threadCount = 3;
		CyclicBarrier cb = new CyclicBarrier(threadCount);
		Object[] objs = new Object[threadCount + 1];
		for (int i = 0; i < objs.length; i++) {
			if (i == 0 || i == objs.length - 1) {
				objs[i] = null;
				continue;
			}
			objs[i] = new Object();
		}
		for (int i = 0; i < threadCount; i++) {
			exec.execute(new PrintTask(objs[i], objs[i + 1], cb, "String#" + i));
		}

		try {
			TimeUnit.MILLISECONDS.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		exec.shutdownNow();
	}
}

class PrintTask implements Runnable {
	private Object pre;
	private Object self;
	private CyclicBarrier cb;
	private String printed;

	public PrintTask(Object pre, Object self, CyclicBarrier cb, String printed) {
		// TODO Auto-generated constructor stub
		this.pre = pre;
		this.self = self;
		this.cb = cb;
		this.printed = printed;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (!Thread.interrupted()) {
				cb.await();
				if (pre != null) {
					synchronized (pre) {
						pre.wait();
					}
				}

				System.out.println(printed);
				TimeUnit.MILLISECONDS.sleep(1000);

				if (self != null) {
					synchronized (self) {
						self.notify();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
