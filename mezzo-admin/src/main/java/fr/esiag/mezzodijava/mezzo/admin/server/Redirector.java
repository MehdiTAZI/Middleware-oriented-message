package fr.esiag.mezzodijava.mezzo.admin.server;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.IOException;

public class Redirector extends Thread {
   DataOutputStream dosStream;
   InputStream iStream;
  /**
   *
   * Constructor for Redirector <br>
   * @param InputStream - the input stream of the process <br>  
   * @param DataOutputStream dos - the output stream of the output file. <br>
   *
   */
   public Redirector(InputStream is, DataOutputStream dos){
       iStream = is;
       dosStream = dos;
   } 

   public void run() {
      int ch;
      try {
         while ((ch = iStream.read()) != -1){
            try {
               dosStream.write(ch);
            }
            catch (IOException e) {
	       System.err.println(e);
            }
	 }
      }
      catch (IOException e){
	System.out.println("error occurred when trying to read process output");
	System.err.println(e);
      }
   }

}
