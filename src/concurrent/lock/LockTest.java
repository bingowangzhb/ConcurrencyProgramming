package concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

	private final Lock lock = new ReentrantLock();
	private int count = 0;

	/**
	 * Get count with try lock
	 * 
	 * @return
	 */
	public int getCountInTryLock() {
		if (lock.tryLock()) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				return count++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		return -1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LockTest lt = new LockTest();
		Thread t1 = new Thread(new GetCountTask(lt), "Thread1");
		Thread t2 = new Thread(new GetCountTask(lt), "Thread2");

		t1.start();
		t2.start();
	}
}
