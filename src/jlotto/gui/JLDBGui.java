package jlotto.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import jlotto.frames.InfoDialog;
import jlotto.listener.JLDBGuiListener;
import jlotto.factory.JLottoFactory;
//import jlotto.utils.DynJMenu;

public class JLDBGui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/* for the necessary of positioning purpose on the system screen */
	private Dimension sDim;
	
	/* GUI Components */
	// JComboBox for selecting tables of a database
	private JComboBox selectionList;
	
	// panels for the components
	private JPanel mainPanel, inputPanel;
	private JPanel chosenLottoNumbersPanel, chosenKenoNumbersPanel;
	private JPanel numbersMainPanel;
	private JScrollPane scrollPanel;
	
	// menu components
	private JMenu dataMenu, editMenu, helpMenu;
	private JMenuBar menuBar;
	private JMenuItem makeDB, openDB, showDB, closeDB, exit, version, help;
	private JMenuItem generatedData;
	private JMenuItem dbConfig;
	
	/* JTextFields for presenting the chosen or
	 * generated lotto or keno numbers
	 */
	private JTextField[] lottoFields;
	private JTextField[] kenoFields;
	
	/* self explaining */
	private TitledBorder titledBorder;
	
	/* parent gui */
	private JLottoGui parentGui;
	
	/* Listener class */
	private JLDBGuiListener jldbListener;
	
	/* the information frame to show the user the actions done by this instance */
	private InfoDialog infoFrame;
	
	/* the Factory producing JLabels and JTextFields */
	private JLottoFactory lottoFactory;
	
	/* JMenu needed for recently opened data */
//	private DynJMenu recentDataMenu;
	
	/**
	 * Creates a new instance of JDBCTestGUI
	 */
	public JLDBGui(JLottoGui parentGui) {
		
		this.parentGui = parentGui;
		
		/* initialize factory */
		this.lottoFactory = new JLottoFactory();
		
		/* create listener class */
		this.jldbListener = new JLDBGuiListener();
		this.jldbListener.setParent(this);
		
//		/* create the sub menu */
//		this.recentDataMenu = new DynJMenu("Zuletzt verwendete ...");
//		/* register DynJMenu on JLDBListener class 
//		 * in order to access the public methods of DynJMenu
//		 */
//		this.jldbListener.setDynJMenu(this.recentDataMenu);
//		/* link to JLDBListener class in order to send ActionEvents to it */
//		this.recentDataMenu.setListener(this.jldbListener);
		
		/* initialize the TextFields
		 * necessary to present chosen numbers
		 */
		this.lottoFields = parentGui.getLottoDrawingPanel().createLottoTextFields();
		this.kenoFields = parentGui.getKenoDrawingPanel().createKenoTextFields();
		
		/* initialize GUI */
		this.initGuiConstraints();
		this.setFrameInCenter();
		this.initGUI();
	}
	
	/*
	 * Defines configuration about the presentation of JLDBGui
	 */
	private void initGuiConstraints() {
		this.setTitle("JDBC Testumgebung 0.1");
		this.setSize(800, 600);
		this.setVisible(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	/*
	 * Initialize the GUI of JDBCTestGUI
	 */
	private void initGUI() {
		
		/* create the JMenuItem of the considering JMenu */
		// for the Data - JMenu
		this.makeDB = new JMenuItem("Neue Datenbank anlegen");
		this.openDB = new JMenuItem("Datenbank öffnen");
		this.showDB = new JMenuItem("Standard Datenbank anzeigen");
		this.generatedData = new JMenuItem("Gezogene Zahlen importieren");
		this.closeDB = new JMenuItem("Datenbank schliessen");
		this.exit = new JMenuItem("Beenden");
	    // for the Edit - JMenu
		this.dbConfig = new JMenuItem("Konfiguration");
		// for the Help - JMenu
		this.version = new JMenuItem("Info");
		this.help = new JMenuItem("Handbuch");
		
		/* set action listener on the JMenuItems */
		this.makeDB.addActionListener(this.jldbListener);
		this.openDB.addActionListener(this.jldbListener);
		this.showDB.addActionListener(this.jldbListener);
		this.generatedData.addActionListener(this.jldbListener);
		this.closeDB.addActionListener(this.jldbListener);
		this.exit.addActionListener(this.jldbListener);
		this.dbConfig.addActionListener(this.jldbListener);
		this.version.addActionListener(this.jldbListener);
		this.help.addActionListener(this.jldbListener);
		
		/* create the main JMenu parts and add their considering 
		 * JMenuItems
		 */
		this.dataMenu = new JMenu("Datei");
		this.dataMenu.add(this.makeDB);
		this.dataMenu.add(this.openDB);
//		this.dataMenu.add(this.recentDataMenu);
		this.dataMenu.add(new javax.swing.JSeparator());
		this.dataMenu.add(this.showDB);
		this.dataMenu.add(this.generatedData);
		this.dataMenu.add(this.closeDB);
		this.dataMenu.add(new javax.swing.JSeparator());
		this.dataMenu.add(this.exit);
		
		this.editMenu = new JMenu("Bearbeiten");
		this.editMenu.add(this.dbConfig);
		
		this.helpMenu = new JMenu("Hilfe");
		this.helpMenu.add(this.version);
		this.helpMenu.add(new javax.swing.JSeparator());
		this.helpMenu.add(this.help);
		
		/* set the appearing order of the sub menus onto the menu bar */
		this.menuBar = new JMenuBar();
		this.menuBar.add(this.dataMenu);
		this.menuBar.add(this.editMenu);
		this.menuBar.add(this.helpMenu);
		this.setJMenuBar(this.menuBar);
		
		/* create the selection box for achieving views on the tables of
		 * a database
		 */
		this.selectionList = new JComboBox();
		this.selectionList.setActionCommand("Tabellenauswahl");
		this.selectionList.addActionListener(this.jldbListener);
		
		/* create the panel of the table view */
		this.scrollPanel = new JScrollPane();
		this.scrollPanel.setVerticalScrollBarPolicy(
				javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPanel.setHorizontalScrollBarPolicy(
				javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		/* create a panel for interacting components */
		this.inputPanel = new JPanel();
		this.inputPanel.add(this.selectionList);
		
		/* create the panel needed for presenting generated and
		 * imported numbers
		 * 
		 * Important : these are added to the MainPanel by 
		 *             pushing the JMenuItem 'generatedData'
		 *             south of this GUI and later shown
		 *             with 'showNumbersPanel()' where
		 *             call to 'hideAndClearNumberPanel()'
		 *             is pre-called
		 */
		this.numbersMainPanel = new JPanel();
		this.chosenLottoNumbersPanel = new JPanel();
		this.chosenKenoNumbersPanel = new JPanel();
		
		/* base panel configuration of JLDBGui */
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new java.awt.BorderLayout());
		this.mainPanel.add(this.inputPanel, "North");
		this.mainPanel.add(this.scrollPanel, "Center");
		
		this.getContentPane().add(this.mainPanel);
		this.validate();
	}
	
	/*
	 * Adds several JComponents on a given JPanel
	 * <p></p>
	 * @param comps the JComponents to add
	 * @param panel the JPanel which should add the JComponents
	 */
	private void addComponents(javax.swing.JComponent[] comps, JPanel panel) {
		for(int i = 0; i < comps.length; i++) {
			panel.add(comps[i]);
		}
	}
	
	/**
	 * Deletes all entries/items from the internal javax.swing.JComboBox -
	 * selectionList
	 */
	public void clearBoxComponents() {
		this.selectionList.removeAllItems();
		this.selectionList.repaint();
		this.selectionList.validate();
	}
	
	/**
	 * Deletes all components of the presentation ( interaction area ) panel of
	 * the main panel
	 */
	public void clearTablePanel() {
		this.scrollPanel.removeAll();
		this.scrollPanel.repaint();
		this.scrollPanel.validate();
	}
	
	/**
	 * Removes all components of the JMenu - recentDataMenu
	 */
	public void clearRecentDataMenu() {
//		/* be sure the recentdataMenu has already elements to remove */
//		try {
//			this.recentDataMenu.removeAll();
//			this.recentDataMenu.repaint();
//			this.recentDataMenu.validate();
//		}
//		catch (NullPointerException e) {
//		}
	}
	
	/**
	 * Hides the JPanel containing the presentation of 
	 * the chosen lotto or keno numbers
	 */
	public void hideAndClearNumberPanel() {
		this.numbersMainPanel.setVisible(false);
		this.chosenLottoNumbersPanel.removeAll();
		this.chosenKenoNumbersPanel.removeAll();
		this.chosenLottoNumbersPanel.repaint();
		this.chosenKenoNumbersPanel.repaint();
		this.mainPanel.repaint();
		this.mainPanel.validate();
	}
	
	/**
	 * Sets the content of a given integer array
	 * into an internal JTextField array, <br></br>
	 * in order to add this onto the JPanel for the chosen lotto numbers.
	 * <p></p>
	 * @param lottoNumbers the lotto numbers of an integer array
	 */
	public void setChosenLottoNumbers(int[] lottoNumbers) {
		for(int i = 0; i < lottoNumbers.length; i++) {
			this.lottoFields[i].setText("" + lottoNumbers[i]);
		}
		this.infoFrame.setInfoText("---> Lottozahlen wurden aufgenommen");
	}
	
	/**
	 * Sets the content of a given integer array
	 * into an internal JTextField array, <br></br>
	 * in order to add this onto the JPanel for the chosen keno numbers.
	 * <p></p>
	 * @param kenoNumbers the keno numbers of an integer array
	 */
	public void setChosenKenoNumbers(int[] kenoNumbers) {
		for(int i = 0; i < kenoNumbers.length; i++) {
			this.kenoFields[i].setText("" + kenoNumbers[i]);
		}
		this.chosenKenoNumbersPanel.validate();
	}
	
	/**
	 * Sets the InfoFrame for this instance and for its listener class
	 * <p></p>
	 * @param infoFrame an instance of InfoFrame
	 */
	public void setInfoFrame(InfoDialog infoFrame) {
		this.infoFrame = infoFrame;
		this.jldbListener.setInfoFrame(this.infoFrame);
	}
	
	/*
	 * Sets the position of this application to the center view
	 * of the system Desktop
	 */
	private void setFrameInCenter() {
		this.sDim = this.getToolkit().getScreenSize();
		int x_dim = this.sDim.width;
		int y_dim = this.sDim.height;
		int x = this.getWidth();
		int y = this.getHeight();
		this.setLocation(x_dim / 2 - x / 2, y_dim / 2 - y / 2);
	}
	
	/**
	 * Shows the JPanel representing the JTextFields and their content
	 * of lotto or keno numbers in the parent GUI - JLDBGui
	 */
	public void showNumbersPanel() {
		
		/* first of all clear previous content of that .. */
		this.hideAndClearNumberPanel();
		
		/* create Panels and Labels needed for presentation
		 * of chosen numbers of lotto and keno
		 */
		JPanel lottoPanel = new JPanel();
		JPanel kenoPanel = new JPanel();
		
		lottoPanel.setLayout(new GridLayout(2, 6));
		kenoPanel.setLayout(new GridLayout(2, 6));
		
		JLabel lottoLab = new JLabel(" gezogene Lottozahlen : ");
		JLabel kenoLab = new JLabel(" gezogene KenoZahlen : ");
		
		/* create Labels for defining which number is in line */
		JLabel[] lottoLabels = parentGui.getLottoDrawingPanel().createLottoLabels();
		JLabel[] kenoLabels = parentGui.getKenoDrawingPanel().createKenoLabels();
		
		/* and add them to the concerning JPanel */
		this.addComponents(lottoLabels, lottoPanel);
		this.addComponents(this.lottoFields, lottoPanel);
		this.addComponents(kenoLabels, kenoPanel);
		this.addComponents(this.kenoFields, kenoPanel);
		
		/*--------- JPanel composition --------------------*/
		this.chosenLottoNumbersPanel.add(lottoLab);
		this.chosenLottoNumbersPanel.add(lottoPanel);
		this.chosenKenoNumbersPanel.add(kenoLab);
		this.chosenKenoNumbersPanel.add(kenoPanel);
		
		this.titledBorder = new TitledBorder("Ziehungszahlen");
		
		this.numbersMainPanel.setLayout(new GridLayout(2, 1));
		this.numbersMainPanel.add(this.chosenLottoNumbersPanel);
		this.numbersMainPanel.add(this.chosenKenoNumbersPanel);
		this.numbersMainPanel.setBorder(this.titledBorder);
		
		this.mainPanel.add(this.numbersMainPanel, "South");
		this.numbersMainPanel.setVisible(true);
		this.mainPanel.repaint();
		this.mainPanel.validate();
	}
	
	/**
	 * Returns the internal javax.swing.JComboBox used to store the table
	 * names of a database, if that is opened via the GUI.
	 * <p></p>
	 * @return javax.swing.JComboBox - selectionList
	 */
	public JComboBox getSelectionBox() {
		return this.selectionList;
	}
	
	/**
	 * Returns the JMenu concerning for refresh and adding JMenuItems
	 * ( DynamicJMenu )
	 * <p></p>
	 * @return an instance of DynJMenu referring to JLDBGui
	 */
//	public DynJMenu getDynJMenu() {
//		return this.recentDataMenu;
//	}
	
	/**
	 * Returns the internal used JScrollPanel of JLDBGui
	 * <p></p>
	 * @return javax.swing.JScrollPane - scrollPanel
	 */
	public JScrollPane getScrollPanel() {
		return this.scrollPanel;
	}
	
	/**
	 * Returns the EventListener class used by JLDBGui
	 * <p></p>
	 * @return JLDBListener - jldbListener
 	 */
	public JLDBGuiListener getJLDBListener() {
		return this.jldbListener;
	}
	
	/*
	 * Prints the chosen keno or lotto numbers in the InfoFrame.
	 * If none of them is generated or chosen in JLottoGui, this
	 * will print the empty-message on the InfoFrame 
	 * <p></p>
	 * @param numbers chosen lotto or keno numbers
	 * @return the chosen lotto or keno numbers, 
	 *  otherwise an error-empty-message
	 *
	private String printNumbers(int[] numbers) {
		String s = "";
		int i = 0;
		while(i < numbers.length - 1) {
			s += numbers[i] + ", ";
			i++;
		}
		s += numbers[i];
		return s;
	}
	*/
}
