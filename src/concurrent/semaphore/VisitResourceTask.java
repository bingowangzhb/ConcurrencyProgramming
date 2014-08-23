package concurrent.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VisitResourceTask implements Runnable {
	private ResourcePool pool;
	private int id;

	public VisitResourceTask(ResourcePool pool, int id) {
		this.pool = pool;
		this.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("#" + id
				+ ": Trying to get reosurce from the pool...");
		Resource resource = pool.getResource();
		System.out.println("#" + id + ": Got a resource, name is "
				+ resource.getName());
		try {
			TimeUnit.MICROSECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.release(resource);
		System.out.println("#" + id + ": Released the resource named "
				+ resource.getName());

	}

	public static void main(String[] args) {
		List<Resource> resources = new ArrayList<Resource>();
		for (int i = 0; i < 3; i++) {
			resources.add(new Resource("res#" + i));
		}
		ResourcePool pool = new ResourcePool(resources);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 4; i++) {
			exec.execute(new VisitResourceTask(pool, i));
		}
	}

}
