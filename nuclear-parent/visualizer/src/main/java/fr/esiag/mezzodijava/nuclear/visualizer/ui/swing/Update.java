package fr.esiag.mezzodijava.nuclear.visualizer.ui.swing;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;


public class Update implements Runnable{
	
	private JFrame frame;
	private int counter;
	private JProgressBar pbar;
	
	public Update(JFrame f,int counter,JProgressBar pbar) {
		// TODO Auto-generated constructor stub
		this.frame=f;
		this.counter=counter;
		this.pbar=pbar;
	}


	
	@Override
	public void run() {


		while(counter !=102){
			pbar.setVisible(false);
			pbar.setValue(counter);
			frame.repaint();
			counter+=2;
			pbar.setVisible(true);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public JProgressBar getPbar() {
		return pbar;
	}

	public void setPbar(JProgressBar pbar) {
		this.pbar = pbar;
	}
	
	
}
