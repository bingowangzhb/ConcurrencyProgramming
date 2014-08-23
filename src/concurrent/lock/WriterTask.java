package concurrent.lock;

import java.util.Random;

public class WriterTask implements Runnable {
	private int taskId;
	private Dictionary dict;

	public WriterTask(int taskId, Dictionary dict) {
		this.taskId = taskId;
		this.dict = dict;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (String key : dict.keySet()) {
			System.out.println("Writer Task#" + taskId + " is modifing key "
					+ key + "...");
			dict.set(key, "Modified by Writer Task#" + taskId);
		}
	}

}
