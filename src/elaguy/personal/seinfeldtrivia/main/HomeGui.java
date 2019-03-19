package elaguy.personal.seinfeldtrivia.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeGui extends JPanel implements ActionListener {
	
	private SeinfeldTrivia seinfeldTrivia;
	private QuestionGui questionGui;
	
	private boolean hasPressedStart;

	public HomeGui(SeinfeldTrivia seinfeldTrivia) {
		this.seinfeldTrivia = seinfeldTrivia;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		hasPressedStart = false;
		
		initLogo();
		initButtons();
	}
	
	private void initLogo() {
		JLabel seinfeldLogo = new JLabel();
		seinfeldLogo.setIcon(new ImageIcon("res/seinfeld_logo.png"));
		seinfeldLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(seinfeldLogo);
		this.add(Box.createRigidArea(new Dimension(0, 125)));
		seinfeldTrivia.getMainFrame().pack();
	}
	
	private void initButtons() {
		JButton startTrivia = new JButton("Start Trivia");
		startTrivia.setForeground(Color.RED);
		startTrivia.setAlignmentX(Component.CENTER_ALIGNMENT);
		startTrivia.setMinimumSize(new Dimension(10, 10));
		startTrivia.setMaximumSize(new Dimension(150, 40));
		startTrivia.addActionListener(this);
		startTrivia.setActionCommand("start");
		startTrivia.setToolTipText("Begin the trivia!");
		this.add(startTrivia);
		
		JButton quit = new JButton("Quit");
		quit.setForeground(Color.RED);
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		quit.setMinimumSize(new Dimension(10, 10));
		quit.setMaximumSize(new Dimension(150, 40));
		this.add(Box.createRigidArea(new Dimension(0, 45)));
		quit.addActionListener(this);
		quit.setActionCommand("quit");
		quit.setToolTipText("Quit the program.");
		this.add(quit);
		
		seinfeldTrivia.getMainFrame().pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("start")) {
			this.removeAll();
			this.updateUI();
			
			seinfeldTrivia.getMainFrame().remove(this);
			seinfeldTrivia.getMainFrame().add(questionGui);
		
			seinfeldTrivia.getMainFrame().repaint();
			seinfeldTrivia.getMainFrame().revalidate();
			
			hasPressedStart = true;
		}
		
		else if(e.getActionCommand().equals("options")) {
			this.removeAll();
			this.updateUI();
			
			seinfeldTrivia.getMainFrame().remove(this);
			
			seinfeldTrivia.getMainFrame().repaint();
			seinfeldTrivia.getMainFrame().revalidate();
		}
		
		else {
			seinfeldTrivia.getMainFrame().dispose();
			System.exit(0);
		}
	}
	
	public void setQuestionGui(QuestionGui questionGui) {
		this.questionGui = questionGui;
	}
	
	public boolean getHasPressedStart() {
		return hasPressedStart;
	}
}