package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ProgressMonitorPanel extends JPanel implements ActionListener{

	/**
	 * @param args
	 */
	
	private static ProgressMonitor pbar;
	private static int counter=0;
	private static boolean isOK=true;
	
	
	public ProgressMonitorPanel(boolean isOK) {
		super(true);
		this.isOK=isOK;
		pbar=new ProgressMonitor(this,"Subscribing Progress", "Subscribing . . .", 0, 100);
		
		Timer timer=new Timer(100,this);
		timer.start();
		
		
		
	}
	
	class UpdateBar implements Runnable {

		@Override
		public void run() {
			if(pbar.isCanceled()){
				pbar.close();
				System.exit(1);
				isOK=false;
			}
			pbar.setProgress(counter);
			pbar.setNote("Operation is "+counter +" complete");
			counter+=2;
			
		}
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new UpdateBar());
		
	}


	public static boolean isOK() {
		return isOK;
	}


	public static void setOK(boolean isOK) {
		ProgressMonitorPanel.isOK = isOK;
	}
	
	

}
