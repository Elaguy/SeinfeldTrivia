package elaguy.personal.seinfeldtrivia.main;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SeinfeldTrivia {
	
	private HomeGui homeGui;
	private QuestionGui questionGui;
	private Data data;
	private Graphics graphics;
	private ScoreboardGui scoreboardGui;
	
	private JFrame mainFrame;
	private final String VERSION = "1.0";

	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	public SeinfeldTrivia() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		mainFrame = new JFrame("Seinfeld Trivia - Version " + VERSION);
		mainFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		mainFrame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		homeGui = new HomeGui(this);
		data = new Data(this);
		graphics = new Graphics(this);
		scoreboardGui = new ScoreboardGui(this, graphics);
		
		questionGui = new QuestionGui(this, data, graphics, homeGui, scoreboardGui);
		Thread questionThread = new Thread(questionGui);
		
		homeGui.setQuestionGui(questionGui);
		data.setQuestionGui(questionGui);
		
		mainFrame.add(homeGui);
		homeGui.setVisible(true);
		questionGui.setVisible(true);
		
		mainFrame.pack();
		
		questionThread.start();
	}				
	
	public JFrame getMainFrame() {
		return mainFrame;
	}
	
	public HomeGui getHomeGui() {
		return homeGui;
	}				

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}
	
	public static void main(String[] args) {
		SeinfeldTrivia seinfeldTrivia = new SeinfeldTrivia();
	}

}