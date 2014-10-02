package concurrent.readwritelock;

import java.util.concurrent.TimeUnit;

public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadWriteLock lock = new ReadWriteLock();
		Resource res = new Resource(lock);

		// testReaderWriter(res);
		// testMultiReaders(res);
		testMultiWriters(res);
	}

	public static void testReaderWriter(Resource res) {
		new Thread(new ReadTask(res), "reader").start();
		new Thread(new WriteTask(res), "writer").start();
	}

	public static void testMultiReaders(Resource res) {
		new Thread(new ReadTask(res), "reader-01").start();
		new Thread(new ReadTask(res), "reader-02").start();
		new Thread(new ReadTask(res), "reader-03").start();
	}

	public static void testMultiWriters(Resource res) {
		new Thread(new WriteTask(res), "writer-01").start();
		new Thread(new WriteTask(res), "writer-02").start();
		new Thread(new WriteTask(res), "writer-03").start();
	}
}

/**
 * Resource POJO
 * 
 * @author Charles Chen
 * @date 2014年10月2日
 */
class Resource {
	private ReadWriteLock lock;

	public Resource(ReadWriteLock lock) {
		// TODO Auto-generated constructor stub
		this.lock = lock;
	}

	public void read() {
		try {
			lock.readLock();
			for (int i = 0; i < 5; i++) {
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println("Read " + i);
			}
		} catch (Exception e) {
		} finally {
			lock.unReadLock();
		}
	}

	public void write() {
		try {
			lock.writeLock();
			for (int i = 0; i < 5; i++) {
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println("Write " + i);
			}
		} catch (Exception e) {
		} finally {
			try {
				lock.unWriteLock();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

/**
 * Reader
 * 
 * @author Charles Chen
 * @date 2014年10月2日
 */
class ReadTask implements Runnable {
	private Resource res;

	public ReadTask(Resource res) {
		// TODO Auto-generated constructor stub
		this.res = res;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		res.read();
	}
}

/**
 * Writer
 * 
 * @author Charles Chen
 * @date 2014年10月2日
 */
class WriteTask implements Runnable {
	private Resource res;

	public WriteTask(Resource res) {
		// TODO Auto-generated constructor stub
		this.res = res;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		res.write();
	}
}