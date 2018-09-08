package jlotto.frames;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private String messageText;
	
	private JTextArea infoText;
	private JScrollPane scrollPanel;
	
	/**
	 * Creates a new instance of InfoFrame
	 */
	public InfoDialog() {
		this.messageText = "";
		this.infoText = new JTextArea(400, 400);
		this.scrollPanel = new JScrollPane(this.infoText);
		
		this.setSize(500, 200);
		this.setVisible(true);
		this.setTitle("Progress Informationen");
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.getContentPane().add(this.scrollPanel);
		this.validate();
	}
	
	/**
	 * Sets the Information to be displayed on the JPanel of InfoFrame
	 * <p></p>
	 * @param message a message / info text for the instance of InfoFrame
	 */
	public void setInfoText(String message) {
		this.messageText += message + "\n";
		this.infoText.setText(this.messageText);
	}
}
