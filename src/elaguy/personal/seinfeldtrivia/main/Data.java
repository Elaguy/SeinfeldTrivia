package elaguy.personal.seinfeldtrivia.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Data {
	
	private SeinfeldTrivia seinfeldTrivia;
	private ArrayList<String>triviaData;
	private int questionCount;
	private ArrayList<String>correctAnswers;
	
	public Data(SeinfeldTrivia seinfeldTrivia) {
		reset(seinfeldTrivia);
	}
		
	public void gatherData() {
		String home = System.getProperty("user.home");
		
		String dataDirPath = home + "/Documents/SeinfeldTrivia/";
		File dataDir = new File(dataDirPath);
		
		String dataFileName = "data.txt";
		
		if(!dataDir.exists())
			dataDir.mkdir();
		
		File localDataDir = new File("data");
		File[] dataFiles = localDataDir.listFiles();
		
		for(File file : dataFiles) {
			if(!(new File(dataDirPath + file.getName()).exists())) {
				try {
					Files.copy(file.toPath(), (new File(dataDirPath + file.getName())).toPath());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(seinfeldTrivia.getMainFrame(), 
							"An Input/Output error occurred!\n"
							+ "The program failed to copy critical data files.",
							"IO Error", JOptionPane.ERROR_MESSAGE);
					seinfeldTrivia.getMainFrame().dispose();
					System.exit(0);
				}
			}
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(dataDirPath + dataFileName));
			
			String line = "";
			int index = -1;
			while((line = br.readLine()) != null) {
				line = line.trim();
				if(!line.equals("")) { // ignore whitespaces
					if((line.charAt(0) != '/') && (line.charAt(1) != '/')) { // ignore one-line comments ON THEIR OWN LINE
						for(int i = 0; i < line.length(); i++) {
							if(line.charAt(i) == '+') {
								line = line.substring(0, i) + line.substring(i+1);
								correctAnswers.add(line);
						}
					}
					triviaData.add(line);
					
					if(index % 6 == 0)
						questionCount++;
					}
				index++;
				}
			}
		} 
		
		catch (IOException e) {
			JOptionPane.showMessageDialog(seinfeldTrivia.getMainFrame(), 
					"An Input/Output error occurred!\n"
					+ "This usually means the program could not"
					+ " find a question data file.",
					"IO Error", JOptionPane.ERROR_MESSAGE);
			seinfeldTrivia.getMainFrame().dispose();
			System.exit(0);
		}
		
		finally {
			
			try {
				br.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(seinfeldTrivia.getMainFrame(), 
						"An Input/Output error occurred!\n"
						+ " This usually means the program could not"
						+ " find a question data file.",
						"IO Error", JOptionPane.ERROR_MESSAGE);
				seinfeldTrivia.getMainFrame().dispose();
				System.exit(0);
			}
		}
	}
	
	// resets the whole class, equivalent to inside of constructor
	private void reset(SeinfeldTrivia seinfeldTrivia) {
		this.seinfeldTrivia = seinfeldTrivia;
		
		triviaData = new ArrayList<String>();
		questionCount = 0;
		correctAnswers = new ArrayList<String>();
	}
	
	public ArrayList<String> getTriviaData() {
		return triviaData;
	}
	
	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionGui(QuestionGui questionGui) {
		
	}
	
	public ArrayList<String> getCorrectAnswers() {
		return correctAnswers;
	}
	
}
