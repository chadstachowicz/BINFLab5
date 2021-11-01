package lab;

import java.util.Random;

public class QuizThread implements Runnable {
	private Quiz ui;
	private boolean exit = false;
	private int rightCount = 0;
	private int wrongCount = 0;
	private String currentAmino;
	private String shortAmino;
	//need to be volatile so JVM can see updates in while loop (not cached)
	volatile boolean isAnswered;
	volatile boolean questionPresented;
	
	public QuizThread(Quiz quiz) {
		this.ui = quiz;
		this.isAnswered = false;
		this.questionPresented = false;
	}
	//syncronized needed to wait / notify.
	@Override
	public synchronized void run() {
	    
	    //do a while loop checking time and for a wrong answer
		while (!exit) {
				if(!questionPresented) {
					//create a random number 0-19 for working with the arrays
					Random random = new Random();
					int num = random.nextInt(20);
					
					//retrieve our full name and short code from array
					currentAmino = FULL_NAMES[num];
					shortAmino = SHORT_NAMES[num];
					
					ui.proteinSwitchLabel.setText(currentAmino);
					questionPresented = true;
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(isAnswered == true) {
				    String aminoAcid = ui.answerField.getText();
				    //check if the amino acid entered is correct (downcase them both so case doesn't matter)
				    if(!(aminoAcid.toLowerCase()).equals(shortAmino.toLowerCase()))
				    {
				    	wrongCount++;
				    	ui.wrongValLabel.setText(Integer.toString(wrongCount));
				    } else {
				    	rightCount++;
				    	ui.rightValLabel.setText(Integer.toString(rightCount));
				    }
				    isAnswered = false;
				    questionPresented = false;
				    ui.answerField.setText("");
				}
		}
		ui.answerField.setText("");
	}
	
	public void stop()
    {
        exit = true;
    }
	
	public synchronized void checkAnswer()
    {
        isAnswered = true;
        this.notifyAll();
    }
	
	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" 
		};

	public static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"
		};
	
}
