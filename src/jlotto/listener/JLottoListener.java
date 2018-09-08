package jlotto.listener;

/**
 * JLottoListener is the action event listening class for all actions
 * taken in JLotto (GUI) 
 *<p></p>
 * @author Hayri Emrah Kayaman
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import jlotto.dialogs.VersionDialog;
import jlotto.factory.JLottoFactory;
import jlotto.frames.InfoDialog;
import jlotto.frames.JLottoManual;
import jlotto.gui.JLDBGui;
import jlotto.gui.JLottoGui;

public class JLottoListener implements ActionListener {
	
	protected int lottoChoosingOrder = 0;
	protected int kenoChoosingOrder = 0;
	
	private JLottoFactory jfl;
	private JLottoGui parent;
	private JLDBGui jdbcGui;
	
	/* the information frame to show the user the actions done by this instance */
	private InfoDialog infoFrame;
	
	/**
	 * Creates a new instance of JLottoListener
	 */
	public JLottoListener() {
		this.jfl = new JLottoFactory();
	}
	
	/**
	 * Defines or sets which JFrame has fired an action event
	 * events
	 * <p></p>
	 * @param jframe the JFrame from which an event is fired
	 */
	public void setParent(JLottoGui jframe) {
		this.parent = jframe;
	}
	
	/**
	 * Sets the currently JDBCGui instance reference of JLottoListener
	 * <p></p>
	 * @param jdbcGui an instance of JDBCGui
	 */
	public void setJDBCGui(JLDBGui jdbcGui) {
		this.jdbcGui = jdbcGui;
	}
	
	/**
	 * Sets the InfoFrame for this instance
	 * <p></p>
	 * @param infoFrame an instance of InfoFrame
	 */
	public void setInfoFrame(InfoDialog infoFrame) {
		this.infoFrame = infoFrame;
	}
	
	/**
	 * EventHandling Method for JLottoGUI handling all Events
	 * fired by its Components and JMenuItems
	 * <p></p>
	 * @param acevt the java.awt.event.ActionEvent
	 */
	public void actionPerformed(ActionEvent acevt) {
		
		/* String for holding the Button-Text */
		String s = acevt.getActionCommand();
		
		/* listen for fired by Lotto-JButtons and define handling */
//		for(int i = 0; i < this.parent.lottoButtons.length; i++) {
//			if(acevt.getSource() == this.parent.lottoButtons[i]) {
//				s = this.parent.lottoButtons[i].getText();
//				if(this.lottoChoosingOrder < this.parent.getLottoTextFields().length) {
//					this.parent.getLottoTextFields()[this.lottoChoosingOrder].setText(s);
//					this.lottoChoosingOrder++;
//				}
//			}
//		}
		/* listen for fired by Keno-JButtons and define handling */
//		for(int i = 0; i < this.parent.kenoButtons.length; i++) {
//			if(acevt.getSource() == this.parent.kenoButtons[i]) {
//                s = this.parent.kenoButtons[i].getText();
//				if(this.kenoChoosingOrder < this.parent.getKenoTextFields().length) {
//					this.parent.getKenoTextFields()[this.kenoChoosingOrder].setText(s);
//					this.kenoChoosingOrder++;
//				}
//			}
//		}
		if (acevt.getSource() instanceof JButton) {
			
			System.out.println("" + s);
			
//			if (s.equals("Generiere Lotto")) {
//				/* fill the fields with numbers */
//				this.parent.fillFields(this.parent.getLottoTextFields(), this.jfl
//						.generateNumbers(6));
//				this.infoFrame.setInfoText("---> Lottozahlen generiert");
//			}
//			if (s.equals("Generiere Keno")) {
//				/* fill the fields with numbers */
//				this.parent.fillFields(this.parent.getKenoTextFields(), this.jfl
//						.generateNumbers(10));
//				this.infoFrame.setInfoText("---> Kenozahlen generiert");
//			}
//			if(s.equals("Lotto aufnehmen")) {
//				this.infoFrame.setInfoText("---> Lotto Datenaufnahme");
//				this.jdbcGui.setChosenLottoNumbers(
//						this.parent.getChoosenLottoNumbers());
//			}
//			if(s.equals("Keno aufnehmen")) {
//				this.infoFrame.setInfoText("---> Keno Datenaufnahme");
//				this.jdbcGui.setChosenKenoNumbers(
//						this.parent.getChoosenKenoNumbers());
//			}
		}
		if (acevt.getSource() instanceof JMenuItem) {
			/* handle the JMenuItems */
//			if (s.equals("Neue Lotterie")) {
//				/* clear all fields of Lotto and Keno */
//				this.parent.clearFields(this.parent.getLottoTextFields());
//				this.parent.clearFields(this.parent.getKenoTextFields());
//				this.infoFrame.setInfoText("---> Eine neue Lotterie ist gestartet");
//			}
			if(s.equals("neue_kenoziehung")) {
				parent.getMainPanel().removeAll();
				parent.getMainPanel().repaint();
				parent.getMainPanel().add(parent.getKenoDrawingPanel());
				parent.getMainPanel().repaint();
				parent.getMainPanel().validate();
			}
			if(s.equals("neue_lottoziehung")) {
				parent.getMainPanel().removeAll();
				parent.getMainPanel().repaint();
				parent.getMainPanel().add(parent.getLottoDrawingPanel());
				parent.getMainPanel().repaint();
				parent.getMainPanel().validate();
			}
			if (s.equals("oeffne_datenbankanwendung")) {
				this.jdbcGui.setVisible(true);
			}
			if (s.equals("jlotto_beenden")) {
				System.exit(0);
			}
			if (s.equals("zeige_jlotto_version")) {
				// open InfoDialog with Version information
				VersionDialog infdiag = 
						new VersionDialog("Hayri Emrah Kayaman",
										LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM uuuu")), 
										"JLotto", 
										"0.1 beta");
				
				infdiag.setInfoText("JLotto ist ein Programm, "
						+ "womit man in einfachen Schritten \n"
						+ "eine Lottoziehung vollführen kann und diese dann auf \n"
						+ "bereits gezogene Lottozahlen statistisch untersuchen lassen kann.\n"
						+ "Wie dies geschieht lesen Sie bitte im Handbuch nach.\n\n");
			}
			if (s.equals("zeige_jlotto_handbuch")) {
				// open Manual Frame
				JLottoManual jlm = new JLottoManual();
			}
		}
	}
}
