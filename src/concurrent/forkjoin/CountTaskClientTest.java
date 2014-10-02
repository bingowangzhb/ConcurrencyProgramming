package concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class CountTaskClientTest {
	public static int taskId = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int start = 1;
		int end = 20000;

		long startTime;
		long endTime;

		ForkJoinPool pool = new ForkJoinPool();
		startTime = System.nanoTime();
		CountTask task = new CountTask(start, end, taskId++);
		Future<Integer> countTask1 = pool.submit(task);
		try {
			System.out.println(countTask1.get());
			endTime = System.nanoTime();
			System.out.println("Duration of CountTask with Fork/Join: "
					+ (endTime - startTime));
			if (task.isCompletedAbnormally()) {
				System.out.println(task.getException());
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startTime = System.nanoTime();
		Future<Integer> countTask2 = Executors.newCachedThreadPool().submit(
				new CountTaskWithoutForkJoin(taskId++, start, end));
		try {
			System.out.println(countTask2.get());
			endTime = System.nanoTime();
			System.out.println("Duration of CountTask without Fork/Join: "
					+ (endTime - startTime));
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
