package jlotto.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import jlotto.dialogs.DBLogin;
import jlotto.dialogs.VersionDialog;
import jlotto.frames.InfoDialog;
import jlotto.gui.DBCreateGui;
import jlotto.gui.JLDBGui;
import jlotto.dbutils.JDBCUtils;
import jlotto.utils.DynJMenu;
import jlotto.utils.ReadWriteUtils;

public class JLDBGuiListener implements ActionListener {
	
	/* the selected file(s) from JFileChooser */
	private File dbFile, saveDBFile;
	
	/* file object to save the current opened database file system url */
	private File dbInfoSave = new File("DBInfoPaths");
	
	/* the parent frame to listen for */
	private JLDBGui jdbcGui;
	
	/* the frame for configuring a new database */
	private DBCreateGui dbCreateGui;
	
	/* the login dialog for retrieving user/password name of a database
	 * in order to open a database
	 */
	private DBLogin dbLoginDialog;
	
	/* the information frame to show the user the actions done by this instance */
	private InfoDialog infoFrame;
	
	/* use full utility classes */
	private DynJMenu dynMenu;
	private JDBCUtils dbUtils;
	private ReadWriteUtils rwu;
	
	/**
	 * Creates a new instance of JLDBListener
	 */
	public JLDBGuiListener() {
		/* 
		 * create necessary instances for accessing database and retrieving
		 * information about this 
		 */
		this.dbUtils = new JDBCUtils();
		
		/* create new utility class for reading and writing
		 * to file objects
		 */
		this.rwu = new ReadWriteUtils();
		
		/*
		 * create a DynamicJMenu in order to listen
		 * to its actions
		 */
		
	}
	
	@Override
	public void actionPerformed(ActionEvent acevt) {
		
		String s = acevt.getActionCommand();

		if (s.equals("Neue Datenbank anlegen")) {

			/* create a new database using the DBCreateGui */
			this.infoFrame.setInfoText("---> Eine neue Datenbank wird erzeugt");
			this.dbCreateGui = new DBCreateGui(this);
			this.dbCreateGui.setInCenterOfParent(this.jdbcGui);
		}
		if (s.equals("Datenbank öffnen")) {

			/* open the path to the database, and connect to it */
			this.infoFrame.setInfoText("---> Eine Datenbank wird geöffnet");
			this.openDB();
		}
		if (s.equals("Standard Datenbank anzeigen")) {

			this.infoFrame.setInfoText("---> Datenbank wird angezeigt");
		}
		if (s.equals("Gezogene Zahlen importieren")) {

			/* import chosen lotto or keno numbers */
			this.infoFrame.setInfoText(
					"---> alle gezogenen Zahlen wurden übertragen");
			this.jdbcGui.showNumbersPanel();
		}
		if (s.equals("Datenbank schliessen")) {

			/* close the database and disconnect from it */
			this.infoFrame.setInfoText("Datenbank " + this.dbUtils.getDBName()
					+ " wird geschlossen");
			this.removeContent();
			this.dbUtils.closeDB();
		}
		if (s.equals("Beenden")) {
			this.removeContent();
			this.dbUtils.closeDB();
			this.jdbcGui.setVisible(false);
		}
		if (s.equals("Info")) {
			/* retrieve information about this GUI */
			VersionDialog infdiag = new VersionDialog("Hayri Emrah Kayaman",
					"Sonntag, 13.12.2009", "JLotto Datenbankanwendung",
					"0.1 beta");
			infdiag.setInfoText("JLottoDB (Datenbankanwendung) bietet die Einsicht "
					+ "auf bereits vorhandene \ndatierte Lotto-Kenoziehungen.\n"
					+ "Die Daten können in einem internen statistischem Verfahren \n"
					+ "aufgelistet werden, um somit eine Übersicht über Ziehungen \n"
					+ "und möglichen Gewinnchancen zu errechnen.\n"
					+ "Konsultieren sie dafür das Handbuch.");
		}
		if (s.equals("Handbuch")) {
			/* open user manual of JLDBGui */
			System.out.println("Handbuch für Datenbankanwendung wird angezeigt");
		}
		if (s.equals("Liste löschen")) {
			System.out.println("Liste gelöscht!");
		}
		/* handle events fired from DBLogin JDialog */
		if (s.equals("USER_LOGIN_BUT")) {
			this.doDBLoginProcess();
		}
		if (s.equals("LOGIN_DENIED_BUT")) {
			System.out.println("LOGIN by User DENIED");
			this.dbLoginDialog.setVisible(false);
		}
		/* choose a table from the selection list and show it */
		if (s.equals("Tabellenauswahl")) {
			System.out.println("item = " 
					+ this.jdbcGui.getSelectionBox().getSelectedItem().toString());
			this.dbUtils.showTableOn(
					this.jdbcGui.getSelectionBox().getSelectedItem().toString(), 
					this.jdbcGui.getScrollPanel());
		}

	}
	
	/*
	 * opens a database, when selected by the JMenuItem "Datenbank öffnen"
	 * from the JMenu of JLDBGui.java
	 */
	private void openDB() {
		
		/* if necessary, load driver */
		this.dbUtils.loadJDBCDriver("sun.jdbc.odbc.JdbcOdbcDriver");
		
		/* seek the database  */
		JFileChooser jfl = new JFileChooser();
		jfl.setMultiSelectionEnabled(false);
		jfl.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		int returnVal = jfl.showOpenDialog(this.jdbcGui);
		
		/* define, what will happen when "OK" button is pressed by user */
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
			/* select a database file and */
			this.dbFile = jfl.getSelectedFile();
			/* copy selected file for further usage in this instance */
			this.saveDBFile = this.dbFile;
			/* set the system path to the database */
			this.dbUtils.setDBSystemPath(this.dbFile.getParent());
			/* 
			 * open Database Dialog for the input of user and password data 
			 * in order to open the database
			 */
			this.dbLoginDialog = new DBLogin();
			this.dbLoginDialog.setInCenterOfParent(this.jdbcGui);
			this.dbLoginDialog.setVisible(true);
			this.dbLoginDialog.setActionListener(this);
		}
	}
	
	/*
	 * does the process of confirming user name and password for database
	 * access, the connection to the database with the given parameters,
	 * the saving of the database system folder url into the specified
	 * DBInfoPaths-File
	 */
	private void doDBLoginProcess() {
		
		/* set user and password of database */
		this.dbLoginDialog.setDBUser(
				this.dbLoginDialog.getUserTxtField().getText());
		this.dbLoginDialog.setDBPassword(
				this.dbLoginDialog.getPassTxtField().getText());
		
		/* show information about what is set */
		this.infoFrame.setInfoText("---> DB-Benutzer : " 
				+ this.dbLoginDialog.getUser());
		this.infoFrame.setInfoText("---> Benutzer Passwort : " 
				+ this.dbLoginDialog.getPass());
		
		/* check if something use full has been entered */
		if (this.dbLoginDialog.hasInput()) {
			
			/* input entered try to open connection to the database */
			this.dbUtils.openConnection(
					this.saveDBFile.getName(),
					this.dbLoginDialog.getUser(), 
					this.dbLoginDialog.getPass());
			
			/* clear previous opened database table view on scroll panel */
			this.jdbcGui.clearTablePanel();
			
			if (this.dbUtils.isDBOpen()) {
				
				/* rewrite the file with the removed content */
				this.rwu.writeToFile(this.getRecentSaveFile(), 
						this.dbUtils.getDBSystemPath() 
						 + "\\" + this.dbUtils.getDBName(), 
						true);
				
				JMenuItem item = new JMenuItem(this.dbUtils.getDBSystemPath() 
						+ "\\" + this.dbUtils.getDBName());
				
				/* add opened file directory to the recent opened data */
				this.dynMenu.addMenuItem(item);
				
				/* first of all clear previous items of selection box */
				if(this.jdbcGui.getSelectionBox().getItemCount() > 0) {
					this.jdbcGui.getSelectionBox().removeAllItems();
				}
				
				/* set all table names of a database as items in the
				 * JComboBox of the GUI
				 */
				this.dbUtils.setTableNames(this.jdbcGui.getSelectionBox());
				
				/* Dialog is not needed anymore */
				this.dbLoginDialog.setVisible(false);
			}
		}
	}
	
	/**
	 * removes the components of the main panel and the table panel
	 * of JLDBGui interface
	 */
	public void removeContent() {
		try {
			/*
			 * clear the content of the JComboBox and the table
			 * (if already is shown )
			 */
			this.jdbcGui.clearBoxComponents();
			this.jdbcGui.clearTablePanel();
			this.jdbcGui.hideAndClearNumberPanel();
			this.jdbcGui.repaint();
			this.jdbcGui.validate();
		}
		catch(NullPointerException nullComp) { // do nothing
		}
	}
	
	/**
	 * Sets the InfoFrame for JLDBListener and
	 * for the JDBCUtils class
	 * <p></p>
	 * @param infoFrame an instance of InfoFrame
	 */
	public void setInfoFrame(InfoDialog infoFrame) {
		this.infoFrame = infoFrame;
		this.dbUtils.setInfoFrame(this.infoFrame);
	}
	
	/**
	 * Sets the dynamic JMenu ( DynJMenu ) class for JLDBListener
	 * <p></p>
	 * @param menu the 
	 */
	public void setDynJMenu(DynJMenu menu) {
		this.dynMenu = menu;
	}
	
	/**
	 * Sets the parent frame who is listening to 
	 * this instance of JLDBListener
	 * <p></p>
	 * @param gui an instance of JLDBGui needed to be set
	 */
	public void setParent(JLDBGui gui) {
		this.jdbcGui = gui;
	}
	
	/*
	 * Returns the File object which is needed to safe the directory url of
	 * recently opened databases
	 * 
	 * @return DBInfoPaths File object which can be found in package.Listener
	 */
	private File getRecentSaveFile() {
		return this.dbInfoSave;
	}
}