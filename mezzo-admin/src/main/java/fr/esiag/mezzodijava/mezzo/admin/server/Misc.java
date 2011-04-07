package fr.esiag.mezzodijava.mezzo.admin.server;
import java.lang.System;
import java.lang.String;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.IOException;

public class Misc {

  static public void log(String str, DataOutputStream dout) {
    try {
      dout.writeBytes(str + "\n");
      System.out.println(str);
    }
    catch (IOException e) {
      System.out.println ("IO Exception occurred in printing out " + str );
      System.err.println (e);
    }
  }

  public static void redirect(Process p, DataOutputStream dout){
    InputStream isStdout = p.getInputStream();

    
    Redirector rStdout = new Redirector(isStdout, dout);
    rStdout.run();

    InputStream isStderr = p.getErrorStream();
    Redirector rStderr = new Redirector(isStderr, dout);
    rStderr.run();

    try {
      rStdout.join();
      rStderr.join();
    }
    catch (InterruptedException e) {
      System.out.println ("Interrupted Exception in Misc.redirect()");
      System.out.println (e);
    }

  }

}