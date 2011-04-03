package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

public class ThreadPool {
	
	private static int corePoolSize = 5;
	private static int maxPoolSize = 10;
	private static long keepAliveTime = 5000;
	private static int maxQueue = 5;
 
    private ThreadPoolExecutor threadPool = null;
    final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxQueue);
    
    private static Logger log = LoggerFactory.getLogger(ThreadPool.class);
 
    public ThreadPool()
    {
    	threadPool = new ThreadPoolExecutor(
				corePoolSize, maxPoolSize, keepAliveTime,
				TimeUnit.MILLISECONDS, queue);
    }
 
    public void runTask(Runnable task)
    {
        //System.out.println("Task count.."+threadPool.getTaskCount() );
        //System.out.println("Queue Size before assigning the task.."+queue.size() );
        threadPool.execute(task);
        //System.out.println("Queue Size after assigning the task.."+queue.size() );
        //System.out.println("Pool Size after assigning the task.."+threadPool.getActiveCount() );
        //System.out.println("Task count.."+threadPool.getTaskCount() );
        System.out.println("Task count.." + queue.size());
 
    }
 
    public void shutDown()
    {
        threadPool.shutdown();
    }


	public static void main(String[] args) {
		ThreadPool tp = new ThreadPool();
		// for each consumer subscribed and connected
		
        	// run a thread with the task to send event
        	tp.runTask(new ThreadEvent("nuclear"));
        
        // after, shutdown
        tp.shutDown();
    }
}
