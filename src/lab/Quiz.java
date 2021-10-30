package lab;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Quiz extends JFrame {
	private static final long serialVersionUID = -8723649827349837249L;
	private JPanel mainFrame = new JPanel();
	public JButton startQuiz = new JButton("Start Quiz");
	public JButton cancelQuiz = new JButton("Cancel Quiz");
	public JButton submitAnswer = new JButton("Submit");
	private JLabel enterAnswerLabel = new JLabel("<html><u><bold><b1>Enter Answer</u></bold></b1></html>");
	private JLabel rightLabel = new JLabel("<html><u><bold><b1>Right</u></bold></b1></html>");
	private JLabel wrongLabel = new JLabel("<html><u><bold><b1>Wrong</u></bold></b1></html>");
	public JLabel rightValLabel = new JLabel("0");
	public JLabel wrongValLabel = new JLabel("0");
	private JLabel proteinLabel = new JLabel("Protein:");
	public JLabel proteinSwitchLabel = new JLabel("<Start Quiz>");
	private JLabel timerLabel = new JLabel("Time Left:");
	public JLabel timerCounterLabel = new JLabel("NOT STARTED");
	private JLabel holder = new JLabel("<html><u><bold><b1></u></bold></b1></html>");
	public JTextField answerField = new JTextField("");
	private static Quiz UIPass;
	private static QuizThread quizThread;
	private static TimerThread timerThread;
	
	public Quiz (String title) {
		super(title);
		setSize(200,200);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainFrame, BorderLayout.CENTER);
		buildMainFrame();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	* Application quizzes the user on Amino Acids
	* @param  seconds  first param - amount of seconds to run quiz for
	* @return      voice
	*/
	public static void main(String args[])
	{
		Quiz UI = new Quiz("Protein Quiz v0.01");
		UIPass = UI;
		
	}
	
	public void buildMainFrame() {
		mainFrame.setLayout(new GridLayout(7,2));
		mainFrame.add(enterAnswerLabel);
		mainFrame.add(holder);
		mainFrame.add(answerField);
		mainFrame.add(submitAnswer);
		mainFrame.add(proteinLabel);
		mainFrame.add(proteinSwitchLabel);
		mainFrame.add(rightLabel);
		mainFrame.add(wrongLabel);
		mainFrame.add(rightValLabel);
		mainFrame.add(wrongValLabel);
		mainFrame.add(timerLabel);
		mainFrame.add(timerCounterLabel);
		mainFrame.add(startQuiz);
		mainFrame.add(cancelQuiz);
		cancelQuiz.setEnabled(false);
		submitAnswer.setEnabled(false);
		startQuiz.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        startQuiz.setEnabled(false);
		        cancelQuiz.setEnabled(true);
		        rightValLabel.setText("0");
		        wrongValLabel.setText("0");
		        submitAnswer.setEnabled(true);
		        quizThread = new QuizThread(UIPass);
				new Thread(quizThread).start();
		        timerThread = new TimerThread(UIPass,quizThread);
		        new Thread(timerThread).start();

		     }
		});
		cancelQuiz.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent ae) {
				 timerThread.stop();
			     startQuiz.setEnabled(true);
			     submitAnswer.setEnabled(false);
			     cancelQuiz.setEnabled(false);
			 }
		});
		submitAnswer.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent ae) {
				 quizThread.checkAnswer();
			 }
		});
	}

}
