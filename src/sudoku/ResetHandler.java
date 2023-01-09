package sudoku;

import javax.swing.JTextField;

public class ResetHandler implements Runnable {
	
	private JTextField jf;
	
	public ResetHandler(JTextField jf) {
		this.jf = jf;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		jf.requestFocus();
	}

}
