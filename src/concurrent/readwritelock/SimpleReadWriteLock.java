package concurrent.readwritelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleReadWriteLock {
	private volatile AtomicInteger readers;
	private volatile AtomicInteger writers;
	private volatile AtomicInteger waitingWriters;

	public SimpleReadWriteLock() {
		// TODO Auto-generated constructor stub
		readers = new AtomicInteger(0);
		writers = new AtomicInteger(0);
		waitingWriters = new AtomicInteger(0);
	}

	/**
	 * Use a read lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void readLock() throws InterruptedException {
		while (writers.get() > 0 || waitingWriters.get() > 0) {
			this.wait();
		}
		readers.incrementAndGet();
	}

	/**
	 * Release read lock
	 */
	public synchronized void unReadLock() {
		readers.decrementAndGet();
		this.notifyAll();
	}

	/**
	 * Use a write lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void writeLock() throws InterruptedException {
		waitingWriters.incrementAndGet();
		while (readers.get() > 0 || writers.get() > 0) {
			this.wait();
		}
		waitingWriters.decrementAndGet();
		writers.incrementAndGet();
	}

	/**
	 * Release write lock
	 */
	public synchronized void unWriteLock() {
		writers.decrementAndGet();
		this.notifyAll();
	}
}