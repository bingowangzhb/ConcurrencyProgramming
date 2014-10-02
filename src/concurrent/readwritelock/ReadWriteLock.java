package concurrent.readwritelock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Read write lock
 * 
 * @author Charles Chen
 * @date 2014Äê10ÔÂ2ÈÕ
 */
public class ReadWriteLock {
	private Map<Thread, Integer> readingThreads;
	private Thread writingThread;
	private volatile AtomicInteger writeRequest;
	private volatile AtomicInteger writeAccesses;

	public ReadWriteLock() {
		// TODO Auto-generated constructor stub
		readingThreads = new ConcurrentHashMap<Thread, Integer>();
		writingThread = null;
		writeRequest = new AtomicInteger(0);
		writeAccesses = new AtomicInteger(0);
	}

	/**
	 * Get read lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void readLock() throws InterruptedException {
		Thread thread = Thread.currentThread();
		while (!canGrantReadAccess(thread)) {
			this.wait();
		}
		// Put into reader threads map and count the accesses
		readingThreads.put(thread, getReadAccessCount(thread) + 1);
	}

	/**
	 * Release read lock
	 */
	public synchronized void unReadLock() {
		Thread thread = Thread.currentThread();
		if (!isReader(thread)) {
			throw new IllegalMonitorStateException("Current thread does not"
					+ " hold a read lock on this ReadWriteLock");
		}
		int accessCount = getReadAccessCount(thread) - 1;
		// If current thread has not more read accesses, remove it
		if (accessCount <= 0) {
			readingThreads.remove(thread);
		} else {
			readingThreads.put(thread, accessCount);
		}
		notifyAll();
	}

	/**
	 * Get a write lock
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void writeLock() throws InterruptedException {
		this.writeRequest.incrementAndGet();
		Thread thread = Thread.currentThread();
		while (!canGrantWriteLock(thread)) {
			this.wait();
		}
		this.writeRequest.decrementAndGet();
		this.writeAccesses.incrementAndGet();
		this.writingThread = thread;
	}

	/**
	 * Release the write lock
	 */
	public synchronized void unWriteLock() {
		Thread thread = Thread.currentThread();
		if (!isWriter(thread)) {
			throw new IllegalMonitorStateException("Current thread does not"
					+ " hold a write lock on this ReadWriteLock");
		}
		this.writeAccesses.decrementAndGet();
		if (this.writeAccesses.get() <= 0)
			this.writingThread = null;
		notifyAll();
	}

	/**
	 * Check if the thread can be granted read access
	 * 
	 * @param thread
	 * @return
	 */
	private boolean canGrantReadAccess(Thread thread) {
		// If the thread has already read or write access, it can be granted
		// read access
		if (isReader(thread) || isWriter(thread))
			return true;

		// If there are writers or writer requests, it cannot be granted read
		// access
		if (hasWriter() || writeRequest.get() > 0)
			return false;
		return true;
	}

	/**
	 * Check if the thread can be granted write access
	 * 
	 * @return
	 */
	private boolean canGrantWriteLock(Thread thread) {
		if (isOnlyReader(thread) || isWriter(thread))
			return true;
		if (hasReaders() || hasWriter())
			return false;
		return true;
	}

	/**
	 * Check if there are readers currently
	 * 
	 * @return
	 */
	private boolean hasReaders() {
		return readingThreads.size() > 0 ? true : false;
	}

	/**
	 * Get the read access count of the thread
	 * 
	 * @param thread
	 * @return
	 */
	private int getReadAccessCount(Thread thread) {
		if (readingThreads.get(thread) == null) {
			return 0;
		}
		return readingThreads.get(thread).intValue();
	}

	/**
	 * Check if there is a writer currently
	 * 
	 * @return
	 */
	private boolean hasWriter() {
		return writingThread != null ? true : false;
	}

	/**
	 * Check if the thread is one of current readers
	 * 
	 * @param thread
	 * @return
	 */
	private boolean isReader(Thread thread) {
		if (readingThreads.get(thread) != null
				&& readingThreads.get(thread).intValue() > 0)
			return true;
		return false;
	}

	/**
	 * Check if the thread is the current writer
	 * 
	 * @param thread
	 * @return
	 */
	private boolean isWriter(Thread thread) {
		if (writingThread != null && writingThread == thread)
			return true;
		return false;
	}

	/**
	 * Check if the thread is the only reader currently
	 * 
	 * @param thread
	 * @return
	 */
	private boolean isOnlyReader(Thread thread) {
		if (readingThreads.size() == 1 && readingThreads.get(thread) != null)
			return true;
		return false;
	}
}
