package jlotto.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import jlotto.listener.JLDBGuiListener;

public class DBCreateGui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/* declare buttons, labels, textfields (etc.) and their panels */
	private JButton confirmCreateBut, denyBut, saveLocationBut;
	private JComboBox<String> dbTypeChoice;
	private JLabel dbNameLab, dbUserLab, dbUserPassLab, dbTypeChoiceLab;
	private JLabel saveLocationLab;
	private JPanel buttonPanel, inputPanel, processInfoPanel, mainPanel;
	private JTextField dbNameTF, dbUserTF, dbUserPassTF;
	private JTextArea processInfoTA;
	
	/* a TitledBorder used for "inputPanel" and "processInfoPanel */
	private TitledBorder inputPanelBorder, processInfoBorder;
	
	/* declare the ActionListener class in order to
	 * receive ActionEvents from this GUI
	 */
	private JLDBGuiListener listener;
	
	/**
	 * Creates a new instance of DBConfigGui
	 */
	public DBCreateGui(JLDBGuiListener listener) {
		this.listener = listener;
		this.initGUI();
		this.initGuiSettings();
	}
	
	/**
	 * Reactivates an locked or deactivated JComponent
	 * <p></p>
	 * @param comp the JComponent which should be reactivated
	 * @param flag true or false to reactivate a JComponent
	 */
	public void reactivate(javax.swing.JComponent comp, boolean flag) {
		if(flag == true) {
			comp.setEnabled(flag);
		}
	}
	
	/*
	 * initializes settings for the GUI,like size,title, visibility etc.
	 */
	private void initGuiSettings() {
		this.setTitle("JLotto Datenbankerzeugung Version 0.1");
		this.setSize(500, 460);
		this.setVisible(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	/*
	 * initializes and creates the GUI of DBCreateGui
	 */
	private void initGUI() {
		
		/* create Buttons and define action commands for them
		 * in order to fire events to its ActionListener class
		 */
		this.confirmCreateBut = new JButton("Erzeugen");
		this.denyBut = new JButton("Abbrechen");
		this.confirmCreateBut.setActionCommand("DB erzeugen");
		this.confirmCreateBut.setEnabled(false);
		this.denyBut.setActionCommand("DB nicht erzeugen");
		this.confirmCreateBut.addActionListener(this.listener);
		this.denyBut.addActionListener(this.listener);
		this.saveLocationBut = new JButton("Sichern");
		this.saveLocationBut.setActionCommand("neue DB sichern");
		
		/* create the ComboBox needed to choose which database type
		 * (for Lotto or Keno ) should be created
		 */
		this.dbTypeChoice = new JComboBox<String>();
		this.dbTypeChoice.addItem("Datenbanktyp");
		this.dbTypeChoice.addItem("Lotto Datenbank");
		this.dbTypeChoice.addItem("Keno Datenbank");
		
		/* create Labels */
		this.dbNameLab = new JLabel("Datenbankname :");
		this.dbUserLab = new JLabel("Benutzername :");
		this.dbUserPassLab = new JLabel("Password :");
		this.dbTypeChoiceLab = new JLabel("Datenbanktyp  :");
		this.saveLocationLab = new JLabel("Speicherort :");
		
		/* create the JTextFields for inputing database name, user name
		 * user password
		 */
		this.dbNameTF = new JTextField(10);
		this.dbUserTF = new JTextField(10);
		this.dbUserPassTF = new JTextField(10);
		
		/* create a JTextArea for informing the user, if any input 
		 * has been left to do, or processig steps needed to create
		 * a new database
		 */
		this.processInfoTA = new JTextArea();
		this.processInfoTA.setPreferredSize(new java.awt.Dimension(400, 200));
		
		/* create the titled borders */
		this.inputPanelBorder = new TitledBorder("Einstellungen");
		this.processInfoBorder = new TitledBorder("Informationen");
		
		/* create the panels holding the components of this GUI */
		this.buttonPanel = new JPanel();
		this.buttonPanel.add(this.confirmCreateBut);
		this.buttonPanel.add(this.denyBut);
		
		this.inputPanel = new JPanel();
		this.inputPanel.setLayout(new java.awt.GridLayout(5, 2));
		this.inputPanel.add(this.dbNameLab);
		this.inputPanel.add(this.dbNameTF);
		this.inputPanel.add(this.dbUserLab);
		this.inputPanel.add(this.dbUserTF);
		this.inputPanel.add(this.dbUserPassLab);
		this.inputPanel.add(this.dbUserPassTF);
		this.inputPanel.add(this.dbTypeChoiceLab);
		this.inputPanel.add(this.dbTypeChoice);
		this.inputPanel.add(this.saveLocationLab);
		this.inputPanel.add(this.saveLocationBut);
		this.inputPanel.setBorder(this.inputPanelBorder);
		
		this.processInfoPanel = new JPanel();
		this.processInfoPanel.setBorder(this.processInfoBorder);
		this.processInfoPanel.add(this.processInfoTA);
		
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new java.awt.BorderLayout());
		this.mainPanel.add(this.inputPanel, "North");
		this.mainPanel.add(this.buttonPanel, "Center");
		this.mainPanel.add(this.processInfoPanel, "South");
		this.getContentPane().add(this.mainPanel);
		this.validate();
	}
	
	/**
	 * Sets the appearance position referring towards its parent frame,<br></br>
	 * when the parent frame calls the <code>setVisible(true)</code> method on this
	 * instance
	 * <p></p>
	 * @param parent the parent frame calling an instance of DBLogin  
	 */
	public void setInCenterOfParent(JFrame parent) {
		int xPosMid = ((int)parent.getLocationOnScreen().getX() + this.getSize().width) / 4;
		int yPosMid = ((int)parent.getLocationOnScreen().getY() + this.getSize().height) / 4;
		int xLoc = (xPosMid - this.getSize().width / 4) + 20;
		this.setLocation(xLoc, yPosMid);
		
	}
}
