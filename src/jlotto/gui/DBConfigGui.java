package jlotto.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DBConfigGui extends JFrame {
	
	private Dimension screenDim;
	
	/**
	 * Creates a new instance of DBConfigGui
	 */
	public DBConfigGui() {
	}
	
	/**
	 * initializes settings for the GUI,like size,title, visibility etc.
	 */
	public void initGuiSettings() {
		this.setTitle("JLotto Datenbankanwendung Version 0.1");
		this.setSize(1024, 768);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setGUIScreenCenter(this.getWidth(), this.getHeight());
	}

	/*
	 * places the GUI into the center view of the systems desktop size
	 * <P>
	 * @param guiX the width of the GUI
	 * @param guiY the height of the GUI
	 */
	private void setGUIScreenCenter(int guiX, int guiY) {
		this.screenDim = this.getToolkit().getScreenSize();
		int xPosCenter = this.screenDim.width / 2 - guiX / 2;
		int yPosCenter = this.screenDim.height / 2 - guiY / 2;
		this.setLocation(xPosCenter, yPosCenter);
	}
}
