package fr.esiag.mezzodijava.mezzo.admin.server;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class execBatch {
  public static void go(String[] command) {

    Runtime runtime = Runtime.getRuntime();
    Process p;
    try {
      DataOutputStream jhlog = new DataOutputStream (new FileOutputStream("LOG"));
      p = runtime.exec (command);
      System.out.println ("Exec-ing process examp.bat");
      Misc.redirect(p, jhlog);
      p.waitFor();
    }
    catch (IOException e) {
      System.out.println ("Qap.runTests(IOException) " + e);
    }
   catch (InterruptedException e){
          System.out.println ("Process for testapp was interrupted");
          System.err.println ( e );
   }

  }
}
