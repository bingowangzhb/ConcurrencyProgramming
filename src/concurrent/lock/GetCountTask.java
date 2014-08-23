package concurrent.lock;

import java.util.concurrent.TimeUnit;

public class GetCountTask implements Runnable {
	public LockTest lt;

	public GetCountTask(LockTest lt) {
		this.lt = lt;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < 5; i++) {
				int count = lt.getCountInTryLock();
				if (count != -1) {
					System.out.println(Thread.currentThread().getName()
							+ ": Count is " + count);
				} else {
					System.out.println(Thread.currentThread().getName()
							+ ": Cannot get the lock, I can do something else");
				}
				TimeUnit.MILLISECONDS.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
