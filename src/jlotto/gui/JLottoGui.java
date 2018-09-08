package jlotto.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jlotto.factory.JLottoFactory;
import jlotto.frames.InfoDialog;
import jlotto.listener.JLottoListener;
import jlotto.panels.JKenoPanel;
import jlotto.panels.JLottoPanel;

public class JLottoGui extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/* for positioning the GUI-JFrame on the system screen */
	private Dimension sDim;
	
	/* main panel */
	private JPanel mainPanel;
	private JKenoPanel kenoPanel;
	private JLottoPanel lottoPanel;
	
	/* the TopMenuBar of this GUI */
	private JMenu fileMenu, newDrawings, helpMenu;
	private JMenuBar menuBar;
	private JMenuItem newKenoDrawing, newLottoDrawing, 
					closeItem, openDB, versionItem, openManualItem;
	
	/* helping or extending classes */
	
	private JLDBGui jdbcGui;
	private JLottoFactory lottoFactory;
	private JLottoListener jll;
	
	/* a JFrame for showing which actions are taken 
	 * during the use of JLotto
	 */
	private InfoDialog infoFrame;
	
	/**
	 * Creates a new instance of JLottoGui
	 */
	public JLottoGui() {
		
		/* create necessary classes and initialize the GUI */
		
		kenoPanel = new JKenoPanel();
		lottoPanel = new JLottoPanel();
		
		jdbcGui = new JLDBGui(this);
		lottoFactory = new JLottoFactory();
		
		jll = new JLottoListener();
		jll.setParent(this);
		jll.setJDBCGui(this.jdbcGui);
		
		infoFrame = new InfoDialog();
		jdbcGui.setInfoFrame(infoFrame);
		jll.setInfoFrame(infoFrame);
		
		initGuiConstraints();
		initGUI();
	}
	
	/*
	 * Initializes title, visibility size and closing operation
	 * of this instance
	 */
	private void initGuiConstraints() {
		
		setTitle("JLotto - JKeno Version 0.1 beta");
		setVisible(true);
		setSize(1024, 768);
		setFrameInCenter();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/*
	 * initializes and composes the components of this GUI 
	 */
	private void initGUI() {
		
		/*---------- LAYOUT SETTINGS ----------*/
		
		
		/*------------ JMENU -------------------*/
		
		newDrawings = new JMenu("Neue Ziehung");
		newKenoDrawing = new JMenuItem("Kenoziehung");
		newLottoDrawing = new JMenuItem("Lottoziehung");
		
		newDrawings.add(newKenoDrawing);
		newDrawings.add(newLottoDrawing);
		
		openDB = new JMenuItem("Datenbankanwendung öffnen");
		closeItem = new JMenuItem("Beenden");
		
		fileMenu = new JMenu("Datei");
		fileMenu.add(newDrawings);
		fileMenu.add(openDB);
		fileMenu.addSeparator();
		fileMenu.add(closeItem);
		
		versionItem = new JMenuItem("Version");
		openManualItem = new JMenuItem("Handbuch");
		
		helpMenu = new JMenu("Hilfe");
		helpMenu.add(versionItem);
		helpMenu.addSeparator();
		helpMenu.add(openManualItem);

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
		
		// register action commands
		newLottoDrawing.setActionCommand("neue_lottoziehung");
		newKenoDrawing.setActionCommand("neue_kenoziehung");
		openDB.setActionCommand("oeffne_datenbankanwendung");
		closeItem.setActionCommand("jlotto_beenden");
		versionItem.setActionCommand("zeige_jlotto_version");
		openManualItem.setActionCommand("zeige_jlotto_handbuch");
				
		// register JMenuItems for the action listening class
		newLottoDrawing.addActionListener(jll);
		newKenoDrawing.addActionListener(jll);
		openDB.addActionListener(jll);
		closeItem.addActionListener(jll);
		versionItem.addActionListener(jll);
		openManualItem.addActionListener(jll);
		
		
		/*-------- JFRAME PANEL SETTING -----------*/
		mainPanel = new JPanel();
		
		getContentPane().add(mainPanel);
		validate();
	}
	
	/*
	 * Sets the position of this application to the center view
	 * of the system Desktop
	 */
	private void setFrameInCenter() {
		sDim = getToolkit().getScreenSize();
		int x_dim = sDim.width;
		int y_dim = sDim.height;
		int x = getWidth();
		int y = getHeight();
		setLocation(x_dim / 2 - x / 2, y_dim / 2 - y / 2);
	}
	
	
	/**
	 * Clears the content of a JTextField.
	 * <p></p>
	 * @param field the JTextField to be clear
	 */
	public void clearFields(JTextField field) {
		field.setText("");
	}
	
	/**
	 * Fills a JTextField with generated numbers from JLottoFactory
	 * <p></p>
	 * @param field the JTextField to be fill with numbers
	 * @param numbers the numbers to fill in the JTextFields
	 */
	public void fillField(JTextField field, int[] numbers) {
		
		String content = "";
		int i = 0;
		
		while(i < numbers.length - 1) 
		{
			content += numbers[i] + ", ";
			i++;
		}
		content += numbers[i];
		field.setText(content);
	}
	
	public JKenoPanel getKenoDrawingPanel() {
		return kenoPanel;
	}
	
	public JLottoPanel getLottoDrawingPanel() {
		return lottoPanel;
	}
	
	/*
	 * Returns the main panel of JLottoGui to do operations
	 * on it, like switching between new Keno or Lotto Drawing
	 * 
	 * @return the main JPanel of JLottoGui.class
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	/**
	 * Returns the InfoFrame object of this instance
	 * <p></p>
	 * @return InfoFrame - infoFrame
	 */
	public InfoDialog getInfoFrame() {
		return this.infoFrame;
	}
}