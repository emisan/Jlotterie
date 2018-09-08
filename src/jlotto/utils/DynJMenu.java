package jlotto.utils;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import jlotto.listener.JLDBGuiListener;

public class DynJMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	private int itemCounter;
	private String title;
	private JMenuItem deleteItem;
	private JMenuItem[] menuItems;
	
	private JLDBGuiListener listener;
	
	/**
	 * Creates a new instance of DynJMenu with the 
	 * possibility to add at least two JMenuItem objects<br></br>
	 * and a title describing the JMenu option
	 * <p></p>
	 * @param title the title of the Dynamic JMenu
	 */
	public DynJMenu(String title) {
		
		/* counts the JMenuItems added on the DynJMenu */
		this.itemCounter = 0;
		
		/* create a JMenuItem in order to delete all 
		 * entries of JMenuItems on the JMenu
		 */
		this.deleteItem = new JMenuItem("Liste löschen");
		/* take at least two JMenuItem on this instance */
		this.menuItems = new JMenuItem[2];
		/* instantiate the ActionListener class*/
		this.listener = new JLDBGuiListener();
		
		this.setText(title);
		this.addSeparator();
		this.addDeleteItem();
	}

	/**
	 * Creates a new instance of DynJMenu with the minimal  
	 * amount of JMenuItems to hold<br></br>
	 * and a title describing the JMenu option
	 * <p></p>
	 * @param title the title of the Dynamic JMenu
	 * @param itemAmount the amount of JMenuItems to add
	 */
	public DynJMenu(String title, int itemAmount) {
		
		this.itemCounter = 0;
		/* create a JMenuItem in order to delete all 
		 * entries of JMenuItems on the JMenu
		 */
		this.deleteItem = new JMenuItem("Liste löschen");
		/* initialize menuItems-Array with the amount of adding
		 * JMenuItems
		 */
		this.menuItems = new JMenuItem[itemAmount];
		this.setTitle(title);
		this.addSeparator();
		this.addDeleteItem();
	}
	
	/**
	 * Adds a JMenuItem an instance of DynJMenu.<br></br>
	 * 
	 * Adding example : <br></br>
	 * <code>
	 * DynJMenu d  = new DynJMenu("Data"); <br></br>
	 * d.addMenuItem(new JMenuItem("Open data"));
	 * </code><br></br>
	 * This will create this menu tree : <br></br>
	 * Data          <br></br>
	 * |             <br></br>
	 * --> Open Data 
	 * <p></p>
	 * @param item the JMenuItem to add
	 */
	public void addMenuItem(JMenuItem item) {
		
		if(this.itemCounter < this.menuItems.length) {
			this.menuItems[this.itemCounter] = item;
		    this.menuItems[this.itemCounter].addActionListener(this.listener);
		    this.add(this.menuItems[this.itemCounter]);
			this.addSeparator();
			this.addDeleteItem();
			this.refresh();
			this.itemCounter++;
		}
		else {
			/* the JMenuItem-Array is full, resize and retry add */
			this.menuItems = this.resize(this.menuItems);
			this.addMenuItem(item);
		}
	}
	
	/*
	 * adds the JMenuItem - deleteItem to this instance
	 */
	private void addDeleteItem() {
		this.deleteItem.addActionListener(this.listener);
		this.add(this.deleteItem);
	}
	
	/*
	 * refreshes a JMenu when JMenuItems are added or removed from it
	 */
	private void refresh() {
		this.repaint();
		this.validate();
	}
	
	/**
	 * Removes all JMenuItem(s) and other JComponents added 
	 * on a instance of DynJMenu
	 */
	public void removeAll() {
		
		for(int i = 0; i < this.itemCounter; i++) {
			this.remove(i);
		}
		this.itemCounter = 0;
		this.refresh();
	}
	
	/**
	 * Resizes an array of JMenuItem by one element more
	 * <p></p>
	 * @param menuItems an array filled with JMenuItem(s)
	 * @return a resized JMenuItem-Array
	 */
	public JMenuItem[] resize(JMenuItem[] menuItems) {
		
		JMenuItem[] temp = new JMenuItem[menuItems.length + 2];
		
		for(int i = 0; i < menuItems.length; i++) {
			temp[i] = menuItems[i];
		}
		return temp;
	}
	
	/**
	 * Sets the title for an <b>untitled</b> DynJMenu instance.<br></br><br></br>
	 * Users can also choose a second constructor of DynJMenu for setting the title<br></br>
	 * <code>JMenu<DynJMenu> menu = new DynJMenu(String title).</code>
	 * <p></p>
	 * @param title the title of an untitled DynJMenu
	 */
	public void setTitle(String title) {
		
		if (this.getText() == null) {
			this.title = title;
			this.setText(this.getTitle());
		}
	}
	
	/**
	 * Sets the action listening class for DynJMenu
	 * <p></p>
	 * @param listener the action listening class - JLDBListener
	 */
	public void setListener(JLDBGuiListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Returns the title of a instance of DynJMenu.<br></br>
	 * If the DynJMenu is instantiated without a title, <br></br>
	 * this method will throw a java.lang.NullPointerException
	 * <p></p> 
	 * @return the title of a instance of DynJMenu, otherwise null
	 */
	public String getTitle() {
		return this.title;
	}
}