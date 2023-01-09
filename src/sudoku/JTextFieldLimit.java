package sudoku;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument{
	private static final long serialVersionUID = 6753159494994017415L;
	private int limit;
	public JTextFieldLimit(int limit) {
		super();
		this.limit=limit;
	}
	public JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit=limit;
	}
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
		if(str==null) return;
		
		if(str.equals(" ")){
			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			} catch (AWTException e) {
				e.printStackTrace();
			}
	
		}
		
		if(Pattern.matches("[1-9]", str))
			if((getLength()+str.length())<=limit) super.insertString(offset, str, attr);
		
	}
}