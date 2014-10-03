package concurrent.readwritelock;

import java.util.HashMap;
import java.util.Map;

/**
 * Read write lock
 * 
 * @author Charles Chen
 * @date 2014Äê10ÔÂ2ÈÕ
 */
public class ReadWriteLock {
	private Map<Thread, Integer> readingThreads;
	private Thread writingThread = null;
	private int writingAccesses = 0;
	private int waitingWriters = 0;

	public ReadWriteLock() {
		// TODO Auto-generated constructor stub
		this.readingThreads = new HashMap<Thread, Integer>();
	}

	/**
	 * Get a read lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockRead() throws InterruptedException {
		Thread thread = Thread.currentThread();
		while (!this.canGrantReadAccess(thread)) {
			this.wait();
		}
		// Put current thread into list and count the read accesses
		this.readingThreads.put(thread, this.getReadAccessors(thread) + 1);
	}

	/**
	 * Release read lock
	 */
	public synchronized void unLockRead() {
		Thread thread = Thread.currentThread();
		// If current thread has not a read lock, throw exception
		if (!this.isReader(thread)) {
			throw new IllegalMonitorStateException("Calling Thread does not"
					+ " hold a read lock on this ReadWriteLock");
		}
		int accessCount = this.getReadAccessors(thread) - 1;
		if (accessCount <= 0) {
			this.readingThreads.remove(thread);
		} else {
			this.readingThreads.put(thread, accessCount);
		}
		this.notifyAll();
	}

	/**
	 * Get a write lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lockWrite() throws InterruptedException {
		this.waitingWriters++;
		Thread thread = Thread.currentThread();
		while (!this.canGrantWriteAccess(thread)) {
			this.wait();
		}
		this.waitingWriters--;
		this.writingThread = thread;
		this.writingAccesses++;
	}

	/**
	 * Release write lock
	 */
	public synchronized void unLockWrite() {
		Thread thread = Thread.currentThread();
		// If current thread has not a write lock, throw exception
		if (!this.isWriter(thread)) {
			throw new IllegalMonitorStateException("Calling Thread does not"
					+ " hold the write lock on this ReadWriteLock");
		}
		this.writingAccesses--;
		if (this.writingAccesses <= 0) {
			this.writingThread = null;
		}
		this.notifyAll();
	}

	/**
	 * Check if the thread can be granted read access
	 * 
	 * @param thread
	 * @return
	 */
	private boolean canGrantReadAccess(Thread thread) {
		// If the thread is already a reader or a writer, return true
		if (this.isReader(thread) || this.isWriter(thread))
			return true;
		// If there are writers or waiting writers, return falses
		if (this.writingThread != null || this.waitingWriters > 0)
			return false;
		return true;
	}

	/**
	 * Check if the thread can be granted write access
	 * 
	 * @param thread
	 * @return
	 */
	private boolean canGrantWriteAccess(Thread thread) {
		// If the thread is the only reader or already a writer, return true
		if (this.isOnlyReader(thread) || this.isWriter(thread))
			return true;
		// If there are readers or writers, return false
		if (this.readingThreads.size() > 0 || this.writingThread != null)
			return false;
		return true;
	}

	/**
	 * Get the count of read accesses of the thread
	 * 
	 * @param thread
	 * @return
	 */
	private int getReadAccessors(Thread thread) {
		Integer accessCount = this.readingThreads.get(thread);
		if (accessCount == null) {
			return 0;
		}
		return accessCount.intValue();
	}

	/**
	 * Check if the thread has got read access
	 * 
	 * @param thread
	 * @return
	 */
	private boolean isReader(Thread thread) {
		if (this.readingThreads.get(thread) != null
				&& this.readingThreads.get(thread).intValue() > 0)
			return true;
		return false;
	}

	/**
	 * Check if the thread is the only one that has got read access
	 * 
	 * @param thread
	 * @return
	 */
	private boolean isOnlyReader(Thread thread) {
		if (this.readingThreads.size() == 1
				&& this.readingThreads.get(thread) != null)
			return true;
		return false;
	}

	/**
	 * Check if the thread has got write access
	 * 
	 * @param thread
	 * @return
	 */
	private boolean isWriter(Thread thread) {
		return this.writingThread != null && this.writingThread == thread ? true
				: false;
	}
}
