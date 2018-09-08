package jlotto.utils;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class GuiUtils {
	
	private JFrame parent;
	
	public GuiUtils() {
	}
	
	public GuiUtils(JFrame parent) {
		this.parent = parent;
	}

	/*
	 * Copies the content of one JTextField-Array into another one
	 * <p></p>
	 * @param fromField the sending JTextField Array
	 * @param toField the receiving JTextField Array
	 * @return the JTextField Array which has received the JTextFields from 
	 *         the sending JTextField Array
	 */
	private static JTextField[] copyFromTo(JTextField[] fromField, JTextField[] toField) {
		try {
		    /* check if fields have different length */
			/* case : sending field greater than receiving field */
			if (fromField.length > toField.length) {
				for (int i = 0; i < toField.length; i++) {
					toField[i] = fromField[i];
				}
			} else {
				/*
				 * sending JTextField Array has smaller or equal length than
				 * receiving
				 */
				for (int i = 0; i < fromField.length; i++) {
					toField[i] = fromField[i];
				}
			}
		} 
		catch(ArrayIndexOutOfBoundsException aiobe) {
			
			/* use InfoFrame.class for sending messages */
//			this.infoFrame.setInfoText(aiobe.getMessage());
			
			/* otherwise */
			aiobe.printStackTrace();
		}
		return toField;
	}
}
