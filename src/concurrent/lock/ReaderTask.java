package concurrent.lock;

/**
 * Reader task to read data from Dictionary
 * 
 * @author Charles Chen
 * 
 */
public class ReaderTask implements Runnable {
	private int taskId;
	private Dictionary dict;

	public ReaderTask(int taskId, Dictionary dict) {
		this.taskId = taskId;
		this.dict = dict;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (String key : dict.keySet()) {
			System.out.println("Reader Task#" + taskId + ": " + key + "["
					+ dict.get(key) + "]");
		}
	}

}
