package jlotto.dialogs;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jlotto.listener.JLDBGuiListener;

public class DBLogin extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JButton confirm, deny;
	private JLabel userLabel, passwordLabel;
	private JPanel mainPanel, buttonPanel, mainInputPanel, userInputPanel, passInputPanel;
	private JTextField userInputField, passInputField;
	
	private String userName, userPassword;
	
	private JLDBGuiListener listener;
	
	/**
	 * Creates a new instance of DBSelectionDialog
	 */
	public DBLogin() {
		this.userName = null;
		this.userPassword = null;
		this.initDialogConstraints();
		this.initDialog();
	}
	
	/*
	 * initializes settings for the presentation of DBLogin dialog
	 */
	private void initDialogConstraints() {
		this.setSize(300, 200);
		this.setResizable(false);
		this.setVisible(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/* 
	 * initializes the GUI of DBLogin dialog
	 */
	private void initDialog() {
		
		this.confirm = new JButton("Login");
		this.deny = new JButton("Abbrechen");
		
		this.confirm.setActionCommand("USER_LOGIN_BUT");
		this.deny.setActionCommand("LOGIN_DENIED_BUT");
		
		this.userInputField = new JTextField(8);
		this.passInputField = new JTextField(12);
		
		this.userLabel = new JLabel("Benutzer : ");
		this.passwordLabel = new JLabel("Password : ");
		
		this.buttonPanel = new JPanel();
		this.buttonPanel.add(this.confirm);
		this.buttonPanel.add(this.deny);
		
		this.userInputPanel = new JPanel();
		this.userInputPanel.add(this.userLabel);
		this.userInputPanel.add(this.userInputField);
		
		this.passInputPanel = new JPanel();
		this.passInputPanel.add(this.passwordLabel);
		this.passInputPanel.add(this.passInputField);
		
		this.mainInputPanel = new JPanel();
		this.mainInputPanel.setLayout(new java.awt.GridLayout(2, 1));
		this.mainInputPanel.add(this.userInputPanel);
		this.mainInputPanel.add(this.passInputPanel);
		
		this.mainPanel = new JPanel();
		this.mainPanel.add(this.mainInputPanel, "North");
		this.mainPanel.add(this.buttonPanel, "Center");
		
		this.getContentPane().add(this.mainPanel);
		this.validate();
	}
	
	/**
	 * Checks if user and suer password for a database are set
	 *<p></p>
	 *@return <b><code>true</code></b> if input is done, 
	 *        otherwise <b><code>false</code></b>
	 */
	public boolean hasInput() {
		return (this.userName != null) && (this.userPassword != null);
	}
	
	/**
	 * Sets the user name who tries to connect to a database
	 * <p></p>
	 * @param user the user name of identity which is used to access a database
	 */
	public void setDBUser(String user) {
		this.userName = user;
	}
	
	/**
	 * Sets the password of a user who tries to connect to a database
	 * <p></p>
	 * @param password the user password which is needed to connect to a database
	 */
	public void setDBPassword(String password) {
		this.userPassword = password;
	}
	
	/**
	 * Defines the JLDBListener class to use for this instance
	 * <p></p>
	 * @param listener an instance of JLDBListener
	 */
	public void setActionListener(JLDBGuiListener listener) {
		this.listener = listener;
		this.confirm.addActionListener(this.listener);
		this.deny.addActionListener(this.listener);
	}
	
	/**
	 * Sets the appearance position referring towards its parent frame,<br></br>
	 * when the parent frame calls the <code>setVisible(true)</code> method on this
	 * instance
	 * <p></p>
	 * @param parent the parent frame calling an instance of DBLogin  
	 */
	public void setInCenterOfParent(JFrame parent) {
		int xPosMid = (int)parent.getLocationOnScreen().getX() + this.getSize().width;
		int yPosMid = (int)parent.getLocationOnScreen().getY() + this.getSize().height;
		int xLoc = (xPosMid - this.getSize().width / 4) + 20;
		this.setLocation(xLoc, yPosMid);
		
	}
	/**
	 * Returns the user name of a database from the concerning JTextField
	 * <p></p>
	 * @return the user name who wants to connect to database
	 */
	public String getUser() {
		return this.userName;
	}
	
	/**
	 * Returns the password of a user of a database from the concerning JTextField
	 * <p></p>
	 * @return the password of a database user, who is trying to login
	 */
	public String getPass() {
		return this.userPassword;
	}
	
	/**
	 * Returns the JTextField which is necessary for the input of the user name
	 * of a database
	 * <p></p>
	 * @return javax.swing.JTextField - userInputField
	 */
	public JTextField getUserTxtField() {
		return this.userInputField;
	}
	
	/**
	 * Returns the JTextField which is necessary for the input of the user password
	 * of a database
	 * <p></p>
	 * @return javax.swing.JTextField - passInputField
	 */
	public JTextField getPassTxtField() {
		return this.passInputField;
	}
	
}
