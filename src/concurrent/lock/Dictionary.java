package concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Shared data dictionary
 * 
 * @author Charles Chen
 * 
 */
public class Dictionary {
	// Storage data
	private Map<String, String> data = new HashMap<String, String>();
	private ReadWriteLock lock;
	private Lock readLock;
	private Lock writeLock;

	/**
	 * Constructor. Initialise locks
	 */
	public Dictionary() {
		lock = new ReentrantReadWriteLock();
		readLock = lock.readLock();
		writeLock = lock.writeLock();
	}

	/**
	 * Get value by key, using read lock
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		this.readLock.lock();
		try {
			System.out.println("Getting value of key " + key + "...");
			TimeUnit.MILLISECONDS.sleep(1000);
			return this.data.get(key);
		} catch (InterruptedException e) {
			// TODO: handle exception
			return null;
		} finally {
			this.readLock.unlock();
		}
	}

	/**
	 * Modify or add key-value, using write lock
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		this.writeLock.lock();
		try {
			System.out.println("Modifing value of key " + key + "...");
			TimeUnit.MILLISECONDS.sleep(1000);
			this.data.put(key, value);
		} catch (InterruptedException e) {
			// TODO: handle exception
		} finally {
			this.writeLock.unlock();
		}
	}

	/**
	 * Get keys set, using read lock
	 * 
	 * @return
	 */
	public Set<String> keySet() {
		this.readLock.lock();
		try {
			return this.data.keySet();
		} finally {
			this.readLock.unlock();
		}
	}
}
