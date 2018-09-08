package jlotto.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;

public class JLottoManual extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JSplitPane splitPane;
	private JTree navTree;
	private JPanel htmlView;
	
	/**
	 * Creates a new instance of JLottoManual
	 */
	public JLottoManual() {
		this.initManualGUI();
	}
	
	/*
	 * builds the GUI of JLottoManual 
	 */
	private void initManualGUI() {
		
		this.initGUIConstraints();
		
		this.htmlView = new JPanel();
		this.navTree = new JTree();
		
		this.splitPane = new JSplitPane();
		this.splitPane.setLeftComponent(this.navTree);
		this.splitPane.setRightComponent(this.htmlView);
		this.splitPane.setDividerLocation(200);
		this.getContentPane().add(this.splitPane);
		this.validate();
	}
	
	
	/*
	 * describes the JFrame constraints like closing, size, etc.
	 */
	private void initGUIConstraints() {
		this.setSize(800, 600);
		this.setTitle("JLotto Handbuch");
		this.setVisible(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
