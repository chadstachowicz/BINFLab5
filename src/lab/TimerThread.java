package lab;

import java.util.concurrent.TimeUnit;

public class TimerThread implements Runnable {
	private Quiz ui;
	private QuizThread qt;
	private boolean exit;
	
	public TimerThread(Quiz quiz, QuizThread qt) {
		this.ui = quiz;
		this.qt = qt;
	}
	@Override
	public void run() {
		//get current system time
		int time = 30;
		ui.timerCounterLabel.setText(Integer.toString(time));

	    
	    //do a while loop checking time and for a wrong answer
		while (time > 0 && !exit) {
				try {
					TimeUnit.SECONDS.sleep(1);
					time = time - 1;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ui.timerCounterLabel.setText(Integer.toString(time));
		}
		ui.timerCounterLabel.setText("QUIZ ENDED");
		ui.proteinSwitchLabel.setText("<Quiz Ended>");
		ui.startQuiz.setEnabled(true);
        ui.cancelQuiz.setEnabled(false);
        ui.submitAnswer.setEnabled(false);
        ui.answerField.setText("");
        qt.stop();
        
	}
	
	public void stop()
    {
        exit = true;
    }
	
	
}
