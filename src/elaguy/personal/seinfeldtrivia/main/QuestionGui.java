package elaguy.personal.seinfeldtrivia.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class QuestionGui extends JPanel implements ActionListener, Runnable {

	private SeinfeldTrivia seinfeldTrivia;
	private Data data;
	private Graphics graphics;
	private HomeGui homeGui;
	private ScoreboardGui scoreboardGui;
	
	private ArrayList<String>triviaData;
	private int questionCount, questionIndex, imgIndex;
	private int boundaryIndex1, boundaryIndex2;
	private ArrayList<String> correctAnswers;
	private String rightAnswer;
	
	private JButton answer1, answer2, answer3, answer4;
	private JLabel timeLeftLabel, questionNumLabel;
	
	private boolean hasAnswered, answerTimerIsStopped;
	
	private int moveOnDelay; // in seconds
	private int timeLeft; // in seconds
	
	private int currentQuestionNum;
	private int numOfQuestions;
	
	private Timer moveOnTimer, answerTimer;
	
	private int numCorrect;
	private int numIncorrect;
	
	
	public QuestionGui(SeinfeldTrivia seinfeldTrivia, Data data, Graphics graphics, 
			HomeGui homeGui, ScoreboardGui scoreboardGui) {
		reset(seinfeldTrivia, data, graphics, homeGui, scoreboardGui);
	}
	
	private void initContent() {
		//System.out.println(questionIndex);
		
		String home = System.getProperty("user.home");
		String dataDirPath = home + "/Documents/SeinfeldTrivia/";
		int answerIndex = new Random().nextInt(boundaryIndex2 - boundaryIndex1) + boundaryIndex1;
		
		JPanel questionPanel = new JPanel(new BorderLayout());
		questionPanel.setMaximumSize(new Dimension(seinfeldTrivia.getWIDTH(), 50));
		
		JLabel question = new JLabel(triviaData.get(questionIndex));
		question.setFont(new Font("Georgia", Font.BOLD, 18));
		question.setVerticalAlignment(SwingConstants.TOP);
		question.setHorizontalAlignment(SwingConstants.CENTER);
		questionPanel.add(question);
		this.add(questionPanel);
		
		timeLeftLabel = new JLabel("Time Left: " + timeLeft);
		timeLeftLabel.setFont(new Font("Georgia", Font.BOLD, 12));
		timeLeftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0, 5)));
		this.add(timeLeftLabel);
		
		questionNumLabel = new JLabel("Question " + currentQuestionNum + " of " + numOfQuestions);
		questionNumLabel.setFont(new Font("Georgia", Font.BOLD, 12));
		questionNumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(questionNumLabel);
		
		JLabel image = new JLabel();
		ImageIcon imageIcon = new ImageIcon(dataDirPath + triviaData.get(imgIndex));
		Image newImg = graphics.getResizedImage(imageIcon.getImage(), 200, 200);
		imageIcon = new ImageIcon(newImg);
		image.setIcon(imageIcon);
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0, 25)));
		this.add(image);
		
		answer1 = new JButton(triviaData.get(answerIndex));
		answer1.setAlignmentX(Component.CENTER_ALIGNMENT);
		answer1.setMinimumSize(new Dimension(75, 10));
		answer1.setMaximumSize(new Dimension(150, 40));
		answer1.setForeground(Color.RED);
		answer1.setOpaque(true);
		answer1.addActionListener(this);
		answer1.setActionCommand("answer1");
		this.add(Box.createRigidArea(new Dimension(0, 30)));
		this.add(answer1);
		triviaData.remove(answerIndex);
		
		answerIndex = new Random().nextInt((boundaryIndex2-=1) - boundaryIndex1) + (boundaryIndex1);
		answer2 = new JButton(triviaData.get(answerIndex));
		answer2.setAlignmentX(Component.CENTER_ALIGNMENT);
		answer2.setMinimumSize(new Dimension(75, 10));
		answer2.setMaximumSize(new Dimension(150, 40));
		answer2.setForeground(Color.RED);
		answer2.setOpaque(true);
		answer2.addActionListener(this);
		answer2.setActionCommand("answer2");
		this.add(Box.createRigidArea(new Dimension(0, 45)));
		this.add(answer2);
		triviaData.remove(answerIndex);
		
		answerIndex = new Random().nextInt((boundaryIndex2-=1) - boundaryIndex1) + (boundaryIndex1);
		answer3 = new JButton(triviaData.get(answerIndex));
		answer3.setAlignmentX(Component.CENTER_ALIGNMENT);
		answer3.setMinimumSize(new Dimension(75, 10));
		answer3.setMaximumSize(new Dimension(150, 40));
		answer3.setForeground(Color.RED);
		answer3.setOpaque(true);
		answer3.addActionListener(this);
		answer3.setActionCommand("answer3");
		this.add(Box.createRigidArea(new Dimension(0, 45)));
		this.add(answer3);
		triviaData.remove(answerIndex);
		
		answerIndex = new Random().nextInt((boundaryIndex2-=1) - boundaryIndex1) + (boundaryIndex1);
		answer4 = new JButton(triviaData.get(answerIndex));
		answer4.setAlignmentX(Component.CENTER_ALIGNMENT);
		answer4.setMinimumSize(new Dimension(75, 10));
		answer4.setMaximumSize(new Dimension(150, 40));
		answer4.setForeground(Color.RED);
		answer4.setOpaque(true);
		answer4.addActionListener(this);
		answer4.setActionCommand("answer4");
		this.add(Box.createRigidArea(new Dimension(0, 45)));
		this.add(answer4);
		triviaData.remove(answerIndex);
		
		seinfeldTrivia.getMainFrame().pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("answer1")) {
			if(answer1.getText().equals(rightAnswer)) {
				if(!hasAnswered) {
					numCorrect++;
					modifyAnswer1AsCorrect();
				}
			}
			
			else {
				if(!hasAnswered) {
					numIncorrect++;
					modifyAnswer1AsWrong();
				}
			}
		}
		
		else if(e.getActionCommand().equals("answer2")) {
			if(answer2.getText().equals(rightAnswer)) {
				if(!hasAnswered) {
					numCorrect++;
					modifyAnswer2AsCorrect();
				}
			}
			
			else {
				if(!hasAnswered) {
					numIncorrect++;
					modifyAnswer2AsWrong();
				}
			}
		}
		
		else if(e.getActionCommand().equals("answer3")) {
			if(answer3.getText().equals(rightAnswer)) {
				if(!hasAnswered) {
					numCorrect++;
					modifyAnswer3AsCorrect();
				}
			}
			
			else {
				if(!hasAnswered) {
					numIncorrect++;
					modifyAnswer3AsWrong();
				}
			}
		}
		
		else if(e.getActionCommand().equals("answer4")) {
			if(answer4.getText().equals(rightAnswer)) {
				if(!hasAnswered) {
					numCorrect++;
					modifyAnswer4AsCorrect();
				}
			}
			
			else {
				if(!hasAnswered) {
					numIncorrect++;
					modifyAnswer4AsWrong();
				}
			}
		}
		
	}
	
	private void modifyAnswer1AsCorrect() {
		answer1.setBackground(Color.GREEN);
		answer1.setForeground(Color.GREEN);
		answer1.setFocusable(false);
		
		answer2.setForeground(Color.GRAY);
		answer2.setEnabled(false);
		answer2.setFocusable(false);
		
		answer3.setForeground(Color.GRAY);
		answer3.setEnabled(false);
		answer3.setFocusable(false);
		
		answer4.setForeground(Color.GRAY);
		answer4.setEnabled(false);
		answer4.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	private void modifyAnswer1AsWrong() {
		answer1.setBackground(Color.RED);
		answer1.setForeground(Color.RED);
		answer1.setFocusable(false);
		
		answer2.setForeground(Color.GRAY);
		answer2.setEnabled(false);
		answer2.setFocusable(false);
		
		answer3.setForeground(Color.GRAY);
		answer3.setEnabled(false);
		answer3.setFocusable(false);
		
		answer4.setForeground(Color.GRAY);
		answer4.setEnabled(false);
		answer4.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	private void modifyAnswer2AsCorrect() {
		answer2.setBackground(Color.GREEN);
		answer2.setForeground(Color.GREEN);
		answer2.setFocusable(false);
		
		answer1.setForeground(Color.GRAY);
		answer1.setEnabled(false);
		answer1.setFocusable(false);
		
		answer3.setForeground(Color.GRAY);
		answer3.setEnabled(false);
		answer3.setFocusable(false);
		
		answer4.setForeground(Color.GRAY);
		answer4.setEnabled(false);
		answer4.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	private void modifyAnswer2AsWrong() {
		answer2.setBackground(Color.RED);
		answer2.setForeground(Color.RED);
		answer2.setFocusable(false);
		
		answer1.setForeground(Color.GRAY);
		answer1.setEnabled(false);
		answer1.setFocusable(false);
		
		answer3.setForeground(Color.GRAY);
		answer3.setEnabled(false);
		answer3.setFocusable(false);
		
		answer4.setForeground(Color.GRAY);
		answer4.setEnabled(false);
		answer4.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	private void modifyAnswer3AsCorrect() {
		answer3.setBackground(Color.GREEN);
		answer3.setForeground(Color.GREEN);
		answer3.setFocusable(false);
		
		answer1.setForeground(Color.GRAY);
		answer1.setEnabled(false);
		answer1.setFocusable(false);
		
		answer2.setForeground(Color.GRAY);
		answer2.setEnabled(false);
		answer2.setFocusable(false);
		
		answer4.setForeground(Color.GRAY);
		answer4.setEnabled(false);
		answer4.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	private void modifyAnswer3AsWrong() {
		answer3.setBackground(Color.RED);
		answer3.setForeground(Color.RED);
		answer3.setFocusable(false);
		
		answer1.setForeground(Color.GRAY);
		answer1.setEnabled(false);
		answer1.setFocusable(false);
		
		answer2.setForeground(Color.GRAY);
		answer2.setEnabled(false);
		answer2.setFocusable(false);
		
		answer4.setForeground(Color.GRAY);
		answer4.setEnabled(false);
		answer4.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	private void modifyAnswer4AsCorrect() {
		answer4.setBackground(Color.GREEN);
		answer4.setForeground(Color.GREEN);
		answer4.setFocusable(false);
		
		answer1.setForeground(Color.GRAY);
		answer1.setEnabled(false);
		answer1.setFocusable(false);
		
		answer2.setForeground(Color.GRAY);
		answer2.setEnabled(false);
		answer2.setFocusable(false);
		
		answer3.setForeground(Color.GRAY);
		answer3.setEnabled(false);
		answer3.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	private void modifyAnswer4AsWrong() {
		answer4.setBackground(Color.RED);
		answer4.setForeground(Color.RED);
		answer4.setFocusable(false);
		
		answer1.setForeground(Color.GRAY);
		answer1.setEnabled(false);
		answer1.setFocusable(false);
		
		answer2.setForeground(Color.GRAY);
		answer2.setEnabled(false);
		answer2.setFocusable(false);
		
		answer3.setForeground(Color.GRAY);
		answer3.setEnabled(false);
		answer3.setFocusable(false);
		
		hasAnswered = true;
		
		waitThenMoveOn(moveOnDelay);
	}
	
	/*  User has answerTime (default is 10) seconds to answer the question.
	 *  If the question is still unanswered at the end of the timer,
	 *  the correct answer is shown and the program moves on to the next
	 *  question
	 */
	private void runAnswerTimer() {
		ActionListener answerTimerListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timeLeft <= 0 && !hasAnswered) { // if time is up and user has not answered
					answerTimer.stop();
					answerTimerIsStopped = true;
					JOptionPane.showMessageDialog(seinfeldTrivia.getMainFrame(),
							"Time is up!");
					getRightAnswerAndModifyButtons();
				}
				
				else if(timeLeft <= 0 && hasAnswered) { // if time is up but user has answered at the last second
					waitThenMoveOn(moveOnDelay);
				}
				
				else if(!hasAnswered) { // if time is not up and user has not answered
					timeLeft--;
					timeLeftLabel.setText("Time Left: " + timeLeft);
				}
			}
			
		};
		
		answerTimer = new Timer(1000, answerTimerListener);
		answerTimer.start();
	}                                       
	
	// Is used when user clicks an answer button
	private void waitThenMoveOn(int sec) { // in seconds
		correctAnswers.remove(rightAnswer);
		
		if(!answerTimerIsStopped)
			answerTimer.stop();
		
		ActionListener moveOnListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveOnTimer.stop();
				moveToNextQuestion();
			}
			
		};
		
		moveOnTimer = new Timer(moveOnDelay * 1000, moveOnListener);
		moveOnTimer.start();
	}
	
	// clears and re-populates QuestionGui with next question
	private void moveToNextQuestion() {
		if(currentQuestionNum < numOfQuestions) {
			this.removeAll();
			this.updateUI();
			
			seinfeldTrivia.getMainFrame().repaint();
			seinfeldTrivia.getMainFrame().revalidate();
			
			triviaData.remove(questionIndex); // avoid having same question twice
			triviaData.remove(questionIndex);
			
			questionCount -= 1;
			
			currentQuestionNum++;
			
			reset();
		}
		
		else {
			this.removeAll();
			this.updateUI();
			
			seinfeldTrivia.getMainFrame().remove(this);
			seinfeldTrivia.getMainFrame().add(scoreboardGui);
			
			seinfeldTrivia.getMainFrame().repaint();
			seinfeldTrivia.getMainFrame().revalidate();
			
			scoreboardGui.loadScoreboard(numCorrect, numIncorrect, numOfQuestions);
		}
			
	}
	
	// Is used when answerTimer runs out and user has not pressed anything
	private void getRightAnswerAndModifyButtons() {
		numIncorrect++;
		
		if(answer1.getText().equals(rightAnswer))
			modifyAnswer1AsCorrect();
		
		else if(answer2.getText().equals(rightAnswer))
			modifyAnswer2AsCorrect();
		
		else if(answer3.getText().equals(rightAnswer))
			modifyAnswer3AsCorrect();
		
		else
			modifyAnswer4AsCorrect();
	}
	
	// resets the whole class, equivalent to inside of constructor
	// Is called on the first QuestionGui/'screen'/question
	private void reset(SeinfeldTrivia seinfeldTrivia, Data data, Graphics graphics, 
			HomeGui homeGui, ScoreboardGui scoreboardGui) {
		this.seinfeldTrivia = seinfeldTrivia;										  
		this.data = data;
		this.graphics = graphics;
		this.homeGui = homeGui;
		this.scoreboardGui = scoreboardGui;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		data.gatherData();
		triviaData = new ArrayList<String>(data.getTriviaData()); // passing Data's triviaData as a parameter 
																  // makes a copy, so the copy in QuestionGui
		                                                          // doesn't affect the original in Data
		questionCount = data.getQuestionCount();                   
		
		questionIndex = new Random().nextInt(questionCount) * 6;
		imgIndex = questionIndex+5;
		
		boundaryIndex1 = questionIndex+1;
		boundaryIndex2 = imgIndex;
		
		correctAnswers = data.getCorrectAnswers();
		rightAnswer = correctAnswers.get(questionIndex/6);
		
		hasAnswered = false;
		
		moveOnDelay = 3; // in seconds
		timeLeft = 10; // default is 10 (in seconds)
		
		currentQuestionNum = 1;
		numOfQuestions = data.getQuestionCount();
		
		answerTimerIsStopped = false;
		
		numCorrect = 0;
		numIncorrect = 0;
		
		initContent();
	}
	
	// resets the whole class, equivalent to inside of constructor, without parameters
	// Is called AFTER program has gone through first QuestionGui/'screen'/question
	private void reset() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
		questionIndex = new Random().nextInt(questionCount) * 6;
		imgIndex = questionIndex+5;
		
		boundaryIndex1 = questionIndex+1;
		boundaryIndex2 = imgIndex;
		
		correctAnswers = data.getCorrectAnswers();
		rightAnswer = correctAnswers.get(questionIndex/6);
		
		hasAnswered = false;

		timeLeft = 10;
		
		answerTimerIsStopped = false;
		
		initContent();
		runAnswerTimer();
	}

	/*
	 * This is part of a thread
	 * 
	 * This is run at the beginning of the program in 
	 * SeinfeldTrivia with the questionThread reference
	 * 
	 * A thread was necessary so the program could continually
	 * check if the user pressed the start trivia button while
	 * simultaneously running the GUI
	 * 
	 * So, the answer timer will not run until the user has
	 * pressed the start trivia button (which is almost
	 * simultaneous with arriving at the QuestionGui 'screen'
	 * or JPanel
	 */
	@Override
	public void run() {
		// while user has not pressed start
		while(!homeGui.getHasPressedStart()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// sleep then try again
		}
		// if has pressed start, initiate the answer timer
		runAnswerTimer();
	}

}
