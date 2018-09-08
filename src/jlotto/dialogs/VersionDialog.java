package jlotto.dialogs;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class VersionDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Font infoFont, sunInfoFont;
	private URL imageURL;
	private JButton closeButton;
	private JLabel warnIconLabel;
	private JPanel mainPanel, txtPanel, sunDisclaimerPanel, buttonPanel;
	private JTextArea textArea, sunTxtArea;
	private ImageIcon warnImage;
	private TitledBorder txtPanelBorder, sunDisclaimerBorder;
	
	private String author;
	private String date;
	private String appName;
	private String version;
	private String infoText;
	private final String WARN_ICON_NAME = "icon_warnung.jpg";
	
	/**
	 * Creates a new instance of InfoDialog with the given parameters
	 * <p></p>
	 * @param author the author who writes an application
	 * @param date the date when the application which uses this dialog was last modified
	 * @param appName the name of the application which uses this dialog
	 * @param version the version of the application which uses this dialog
	 */
	public VersionDialog(String author, String date, String appName, String version) {
		this.author = author;
		this.date = date;
		this.appName = appName;
		this.version = version;
		this.initDialog();
	}
	
	private void initDialogConstraints() {
		this.setSize(500, 400);
		this.setTitle("JLotto Version Info");
		this.setVisible(true);
		this.setLocation(this.getWidth() / 2, this.getHeight() / 2);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
	}
	
	private void initDialog() {

		this.closeButton = new JButton("Schliessen");
		this.closeButton.addActionListener(this);
		
		this.buttonPanel = new JPanel();
		this.buttonPanel.setBackground(Color.gray);
		this.buttonPanel.add(this.closeButton);
		
		this.textArea = new JTextArea();
		this.textArea.setBackground(Color.gray);
		this.textArea.setForeground(Color.white);
		this.infoFont = new Font("Times New Roman", Font.BOLD, 14);
		this.textArea.setFont(this.infoFont);
		this.textArea.setMargin(new java.awt.Insets(5, 5, 5, 5));
		
		this.txtPanel = new JPanel();
		this.txtPanel.setBackground(Color.gray);
		this.txtPanelBorder = new TitledBorder("Info");
		this.txtPanel.setBorder(this.txtPanelBorder);
		this.txtPanel.add(this.textArea);
		
		this.sunTxtArea = new JTextArea();
		this.sunTxtArea.setForeground(Color.yellow);
		this.sunTxtArea.setBackground(Color.gray);
		this.sunInfoFont = new Font("Times New Roman", Font.ITALIC, 12);
		this.sunTxtArea.setFont(this.sunInfoFont);
		this.sunTxtArea.setText(this.getSunDisclaimer());
		this.sunTxtArea.setMargin(new java.awt.Insets(5, 5, 5, 5));
		
		/* prepare warn-icon image to be displayed in sunDisclaimerPanel */
		this.imageURL = this.getClass().getResource(this.WARN_ICON_NAME);
		this.warnImage = new ImageIcon(this.imageURL);
		this.warnIconLabel = new JLabel();
		this.warnIconLabel.setIcon(this.warnImage);
		
		this.sunDisclaimerPanel = new JPanel();
		this.sunDisclaimerPanel.setBackground(Color.gray);
		this.sunDisclaimerBorder = new TitledBorder("Java Info");
		this.sunDisclaimerPanel.setBorder(this.sunDisclaimerBorder);
		this.sunDisclaimerPanel.add(this.warnIconLabel);
		this.sunDisclaimerPanel.add(this.sunTxtArea);
		
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new java.awt.BorderLayout());
		this.mainPanel.add(this.txtPanel, "North");
		this.mainPanel.add(this.sunDisclaimerPanel, "Center");
		this.mainPanel.add(this.buttonPanel, "South");
		
		this.initDialogConstraints();
		this.getContentPane().add(this.mainPanel);
		this.setModal(false);
		this.validate();
	}
	
	/**
	 * Sets a text for the information a parent JFrame application gives
	 * to this Dialog class
	 * <p></p>
	 * @param infoText the information about a application uses this Dialog
	 */
	public void setInfoText(String infoText) {
		this.infoText = infoText;
		this.textArea.setText(this.getInfoText());
		this.repaint();
	}
	
	/*
	 * Returns the name of the application which uses an instance of InfoDialog
	 * <p></p>
	 * @return the name of the application as a String
	 */
	private String getAppName() {
		return this.appName;
	}
	
	/*
	 * Returns the author who wrote the application which uses an instance of InfoDialog
	 * <p></p>
	 * @return the authors name as a String
	 */
	private String getAuthor() {
		return this.author;
	}
	
	/*
	 * Returns the last modified or creation date of an application uses InfoDialog
	 * <p></p>
	 * @return the date of last modification or creation as a String
	 */
	private String getDate() {
		return this.date;
	}
	
	/*
	 * Returns the actual version number of an application which uses InfoDialog
	 * <p></p>
	 * @return the version number of an application uses IndfoDialog as a String
	 */
	private String getVersion() {
		return this.version;
	}
	
	/*
	 * Returns the information text about an application given to this Dialog by its parent
	 * @return
	 */
	private final String getInfoText() {
		return "Programm : " + this.getAppName() + "\n"
		+ "Version : "
		+ this.getVersion()
		+ "\n"
		+ "letzte Änderung : "
		+ this.getDate()
		+ "\nAutor : "
		+ this.getAuthor()
		+ "\n\n"
		+ this.infoText;
	}

	private final String getSunDisclaimer() {
		return "Java TM ist ein eingetragenes Markenzeichen von SUN Microsystems.\n"
				+ "Alle Uhrheberrechte sind SUN Microsystems vorbehalten.\n";
	}
	
	public void actionPerformed(ActionEvent acevt) {
		if (acevt.getSource() == this.closeButton) {
			this.setVisible(false);
		}
	}
}
