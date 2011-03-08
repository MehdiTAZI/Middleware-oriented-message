package fr.esiag.mezzodijava.mezzo.it;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainServerLauncher implements Runnable {

	Method mainMethod;

	long delay;

	String[] args;

	public MainServerLauncher(Class<?> mainClass, long delay,
		String... args) {
	    this(mainClass,"main",delay, args);
	}
	
	public MainServerLauncher(Class<?> mainClass,String method, long delay,
		String... args) {
	    this.delay = delay;
	    this.args = args;
	    try {

		// Create the array of Argument Types
		Class<?>[] argTypes = { String[].class };

		// Now find the method
		mainMethod = mainClass.getMethod(method, argTypes);
		// System.out.println(mainMethod);

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	public void go() throws InterruptedException {
	    Thread t = new Thread(this);
	    t.setDaemon(true);
	    t.start();
	    Thread.sleep(this.delay);
	}

	@Override
	public void run() {
	    // Create the actual argument array
	    Object passedArgv[] = { args };

	    // Now invoke the method.
	    try {
		mainMethod.invoke(null, passedArgv);
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
	}

    }