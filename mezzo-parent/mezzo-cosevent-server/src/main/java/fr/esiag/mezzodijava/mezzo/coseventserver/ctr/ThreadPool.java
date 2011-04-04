package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPool {

    private static int corePoolSize = 51;
    private int maxPoolSize;
    private static long keepAliveTime = 5000;
    // private static int maxQueue = 5;

    private ThreadPoolExecutor threadPool = null;
    final SynchronousQueue<Runnable> queue;

    private static Logger log = LoggerFactory.getLogger(ThreadPool.class);

    public ThreadPool(int maxPoolSize) {
	this.maxPoolSize = maxPoolSize;
	queue = new SynchronousQueue<Runnable>();
	threadPool = new ThreadPoolExecutor(corePoolSize,
		(corePoolSize > this.maxPoolSize) ? corePoolSize
			: this.maxPoolSize, keepAliveTime,
		TimeUnit.MILLISECONDS, queue);
    }

    public int getActiveCount() {
	return threadPool.getActiveCount();
    }

    public void runTask(Runnable task) {
	// System.out.println("Task count.."+threadPool.getTaskCount() );
	// System.out.println("Queue Size before assigning the task.."+queue.size()
	// );
	threadPool.execute(task);
	// System.out.println("Queue Size after assigning the task.."+queue.size()
	// );
	// System.out.println("Pool Size after assigning the task.."+threadPool.getActiveCount()
	// );
	// System.out.println("Task count.."+threadPool.getTaskCount() );
	System.out.println("Task count.." + queue.size());

    }

    public void shutDown() {
	threadPool.shutdown();
    }

    public static void main(String[] args) {
	ThreadPool tp = new ThreadPool(10);
	// for each consumer subscribed and connected

	// run a thread with the task to send event
	tp.runTask(new ThreadEvent("nuclear"));

	// after, shutdown
	tp.shutDown();
    }

    public void setMaxSize(int capacity) {
	if (capacity > threadPool.getCorePoolSize()) {
	    threadPool.setMaximumPoolSize(capacity);
	} else {
	    threadPool.setMaximumPoolSize(threadPool.getCorePoolSize());
	}

    }
}
