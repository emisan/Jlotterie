package jlotto.panels;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jlotto.factory.JLottoFactory;
import jlotto.listener.JLottoListener;

public class JLottoPanel extends JPanel implements FocusListener {

	private final int DRAWING_TIMES = 12;
	
	private JButton[] buttons;
	private JLabel[] lbl_drawingNames;
	private JPanel buttonDrawings, drawingNamesPanel;
	
	private GridLayout buttonPanelLayout, mainLayout;
	
	private JLottoFactory lottoFactory;
	private JLottoListener jll;
	
	public JLottoPanel() {
		
		lottoFactory = new JLottoFactory();
		
		jll = new JLottoListener();
		
		initComponents();
	}
	
	private void initComponents() {
		
		buttonPanelLayout = new GridLayout(7, 7);
		buttonPanelLayout.setHgap(2);
		buttonPanelLayout.setVgap(2);
		
		mainLayout = new GridLayout(5, 5);
		mainLayout.setHgap(2);
		mainLayout.setVgap(2);
		
		buttons = createLottoButtons();
		lbl_drawingNames = createLottoLabels();
		
		// place all buttons on its panel
		//
		buttonDrawings = new JPanel();
		buttonDrawings = placeButtons(buttons, buttonDrawings, buttonPanelLayout);
				
		this.setLayout(mainLayout);
		this.add(buttonDrawings);
		this.validate();
	}
	
	/*
	 * Places an amount of JButtons on a JPanel and adds
	 * the class-implementation of the ActionListener to each
	 * placed JButton.
	 * 
	 * 
	 * @param buttons a JButton-Array containing JButtons
	 * @param pane the JPanel to inherit the JButtons
	 */
	private JPanel placeButtons(JButton[] buttons, JPanel pane, LayoutManager layout) 
	{
		pane.setLayout(layout);
		int i = 0;
		while(i < buttons.length) {
			buttons[i].addActionListener(jll);
			pane.add(buttons[i]);
			i++;
		}
		return pane;
	}

	/**
	 * Creates 49 JButtons for the ActionHandling of lotto number
	 * select ability
	 * <p></p>
	 * @return 49 JButtons with Writings from 1 to 49
	 */
	public JButton[] createLottoButtons() {
		int i = 0;
		JButton[] buttons = new JButton[49];
		while(i < buttons.length) {
			buttons[i] = new JButton("" + (i + 1));
			i++;
		}
		return buttons;
	}
	
	/**
	 * Creates six instances of JLabel an saves them in an 
	 * Array of Object-Type JLabel
	 *<p></p>
	 *@return six Jlabel's in an JLabel-Array
	 */
	public JLabel[] createLottoLabels() {
		int i = 0;
		JLabel[] lbs = new JLabel[6];
		while(i < lbs.length) {
			lbs[i] = new JLabel("Ziehung " + (i + 1));
			i++;
		}
		return lbs;
	}
	
	/**
	 * Creates six instances of JTextField an saves them in an
	 * Array of Object-Type JTextField
	 *<p></p>
	 *@return six JTextField's in an JTextField-Array
	 */
	public JTextField[] createLottoTextFields() {
		int i = 0;
		JTextField[] jtf = new JTextField[6];
		while(i < jtf.length) {
			jtf[i] = new JTextField(20);
			i++;
		}
		return jtf;
	}
	
	@Override
	public void focusGained(FocusEvent focus) {
		
	}

	@Override
	public void focusLost(FocusEvent focus) {
		
	}
	
	/**
	 * Returns the internal JTextFields for the keno
	 * <p></p>
	 * @return JTextField[] - kenoTxtFields
	 */
//	public JTextField[] getKenoTextFields() {
//		return kenoTxtFields;
//	}
}
