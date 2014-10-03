package concurrent.readwritelock;

import java.util.concurrent.TimeUnit;

public class SimpleReadWriteLock {
	private int readers;
	private int writers;
	private int waitingWriters;

	/**
	 * Get a read lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockRead() throws InterruptedException {
		while (writers > 0 || waitingWriters > 0) {
			this.wait();
		}
		readers++;
	}

	/**
	 * Release a read lock
	 */
	public synchronized void unLockRead() {
		readers--;
		notifyAll();
	}

	/**
	 * Get a write lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockWrite() throws InterruptedException {
		waitingWriters++;
		while (readers > 0 || writers > 0) {
			this.wait();
		}
		waitingWriters--;
		writers++;
	}

	/**
	 * Release a write lock
	 */
	public synchronized void unLockWrite() {
		writers--;
		notifyAll();
	}
}