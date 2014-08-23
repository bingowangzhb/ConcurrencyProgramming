package concurrent.daemon;

public class DaemonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread userThread = new Thread(new MyTask(3));
		Thread daemondThread = new Thread(new MyTask(6));
		daemondThread.setDaemon(true);
		userThread.start();
		daemondThread.start();
	}

}
