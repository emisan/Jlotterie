package jlotto.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import jlotto.factory.JLottoFactory;
import jlotto.gui.JLottoGui;
import jlotto.listener.JLottoListener;

public class JKenoPanel extends JPanel implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int DRAWING_TIMES = 12;
	
	private int[] kenoNumbers;
	
	private JButton[] buttons;
	private JLabel[] lbl_drawingNames;
	private JPanel buttonPanel, txtfLabelPanel;
	private JTextField[] kenoTxtFields;
	
	private TitledBorder buttonPanelBorder, txtfLabelPanelBorder;
	
	private GridLayout mainLayout, buttonPanelLayout, txtfLabelPanelLayout;
	
	private JLottoListener jll;
	private JLottoFactory lottoFactory;
	
	public JKenoPanel() {
		
		kenoNumbers = new int[10];
		
		lottoFactory = new JLottoFactory();
		
		jll = new JLottoListener();
		
		initComponents();
	}
	
	private void initComponents() {
		
		buttonPanelLayout = new GridLayout(10, 7);
		buttonPanelLayout.setHgap(10);
		buttonPanelLayout.setVgap(10);
		
		buttons = createKenoButtons();
		
		// place all buttons on its panel
		//
		buttonPanel = new JPanel();
		
		buttonPanelBorder = new TitledBorder("Kenozahlen");
		
		buttonPanel.setBorder(buttonPanelBorder);
		buttonPanel.setLayout(buttonPanelLayout);
		
		for(int i = 0; i < buttons.length; i++) 
		{
			buttonPanel.add(buttons[i]);
		}
		
		// place labels and textfields necessary to represent the drawing
		//
		lbl_drawingNames = createKenoLabels();
		kenoTxtFields = createKenoTextFields();
		
		txtfLabelPanel = new JPanel();
		
		txtfLabelPanelBorder = new TitledBorder("Lotto-Ziehungen");
		
		txtfLabelPanelLayout = new GridLayout(10, 2);
		txtfLabelPanelLayout.setHgap(10);
		txtfLabelPanelLayout.setVgap(5);
		
		txtfLabelPanel.setBorder(txtfLabelPanelBorder);
		txtfLabelPanel.setLayout(txtfLabelPanelLayout);
		
		for(int i = 0; i < lbl_drawingNames.length; i++) 
		{
			txtfLabelPanel.add(lbl_drawingNames[i]);
			txtfLabelPanel.add(kenoTxtFields[i]);
		}
		
		txtfLabelPanel.validate();
		
		mainLayout = new GridLayout(1, 2);
		mainLayout.setVgap(10);
		
		this.setLayout(mainLayout);
		
		this.add(buttonPanel);
		this.add(txtfLabelPanel);
		this.repaint();
		
		this.validate();
	}
	
	/**
	 * Creates 70 JButtons for the ActionHandling of Keno number
	 * select ability
	 *<p></p>
	 *@return 70 JButtons with Writings from 1 to 70
	 */
	public JButton[] createKenoButtons() 
	{
		int i = 0;
		
		JButton[] buttons = new JButton[70];
		
		while(i < buttons.length) 
		{
			buttons[i] = new JButton("" + (i + 1));
			buttons[i].addActionListener(jll);
			buttons[i].setActionCommand("keno_button_" + (i + 1));
			i++;
		}
		return buttons;
	}
	
	/**
	 * Creates ten instances of JLabel an saves them in an 
	 * Array of Object-Type JLabel
	 *<p></p>
	 *@return ten Jlabel's in an JLabel-Array
	 */
	public JLabel[] createKenoLabels() 
	{
		int i = 0;
		
		JLabel[] lbs = new JLabel[10];
		
		while(i < lbs.length) 
		{
			lbs[i] = new JLabel("Ziehung " + (i + 1));
			i++;
		}
		return lbs;
	}
	
	/**
	 * Creates ten instances of JTextField an saves them in an 
	 * Array of Object-Type JTextField
	 *<p></p>
	 *@return ten JTextField's in an JTextField-Array
	 */
	public JTextField[] createKenoTextFields() 
	{
		int i = 0;
		
		JTextField[] jtf = new JTextField[10];
		
		while(i < jtf.length) 
		{
			jtf[i] = new JTextField(40);
			jtf[i].addFocusListener(this);
			i++;
		}
		return jtf;
	}
	
	/**
	 * Returns the internal JTextFields for the keno
	 * <p></p>
	 * @return JTextField[] - kenoTxtFields
	 */
	public JTextField[] getKenoTextFields() {
		return kenoTxtFields;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
