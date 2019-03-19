package elaguy.personal.seinfeldtrivia.main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreboardGui extends JPanel {
	
	private SeinfeldTrivia seinfeldTrivia;
	private Graphics graphics;
	
	private int numCorrect, numIncorrect, numOfQuestions;
	
	private String goldTrophyPath, silverTrophyPath, bronzeTrophyPath;
	
	public ScoreboardGui(SeinfeldTrivia seinfeldTrivia, Graphics graphics) {
		this.seinfeldTrivia = seinfeldTrivia;
		this.graphics = graphics;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		goldTrophyPath = "res/gold_trophy.png";
		silverTrophyPath = "res/silver_trophy.png";
		bronzeTrophyPath = "res/bronze_trophy.png";
	}
	
	public void loadScoreboard(int numCorrect, int numIncorrect, int numOfQuestions) {
		this.numCorrect = numCorrect;
		this.numIncorrect = numIncorrect;
		this.numOfQuestions = numOfQuestions;
		
		JLabel youGotLabel = new JLabel("Your Score:");
		youGotLabel.setFont(new Font("Georgia", Font.BOLD, 24));
		youGotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(youGotLabel);
		
		JLabel numCorrectLabel = new JLabel("Questions Correct: " + numCorrect);
		numCorrectLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		numCorrectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0, 25)));
		this.add(numCorrectLabel);
		
		JLabel numIncorrectLabel = new JLabel("Questions Incorrect: " + numIncorrect);
		numIncorrectLabel.setFont(new Font("Georgia", Font.BOLD, 18));
		numIncorrectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0, 25)));
		this.add(numIncorrectLabel);
		
		JLabel trophyImage = new JLabel();
		ImageIcon imageIcon = chooseTrophyImage();
		Image newImg = graphics.getResizedImage(imageIcon.getImage(), 400, 300);
		imageIcon = new ImageIcon(newImg);
		trophyImage.setIcon(imageIcon);
		trophyImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0, 100)));
		this.add(trophyImage);
		
		seinfeldTrivia.getMainFrame().pack();
	}
	
	private ImageIcon chooseTrophyImage() {
		if(numCorrect == numOfQuestions) // all questions are correct
			return new ImageIcon(goldTrophyPath);
		else if(numCorrect == Math.round((double)numOfQuestions/2)) // around half of the questions are correct
			return new ImageIcon(silverTrophyPath);
		else // less than half or none were correct
			return new ImageIcon(bronzeTrophyPath); 
	}
}
