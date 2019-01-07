package FlashCard;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import FlashCard.QuizCardBuilder.NewMenuListener;
import FlashCard.QuizCardBuilder.SaveMenuListener;

import java.awt.*;
import java.io.*;
/*
 * kylee prescott
 * quiz card player 
 * with this program you are able to play with the flash cards you have build 
 * 
 */
public class QuizCardPalyer {
	private JTextArea display;
	private JTextArea answer;
	private ArrayList<QuizCard>cardlist;
	private QuizCard currentcard;
	private int currentCardindex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;
	

	public static void main(String[] args) {
		QuizCardPalyer reader = new QuizCardPalyer();
		reader.go();
		
		

	}
	public void go() {
		frame = new JFrame ("Quiz Card Player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif",Font.BOLD,24);
		 display = new JTextArea(10,20);
		display.setFont(bigFont);
		display.setLineWrap(true);
		display.setEditable(false );
		
		
		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		nextButton = new JButton("show Question");
		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		
		

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card Set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.setSize(640,500);
		frame.setVisible(true);
	
	}
public class NextCardListener implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		if(isShowAnswer) {
		display.setText(currentcard.getAnswer());
		nextButton.setText("next card");
				isShowAnswer = false;
	}
	else {
		if (currentCardindex<cardlist.size()) {
			showNextCard();
		}
		else {
			display.setText("that was the last card");
			nextButton.setEnabled(false);
		}
	}
}
}
	public class OpenMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
		}
	}
	private void showNextCard() {
		currentcard = cardlist.get(currentCardindex);
		currentCardindex++;
		display.setText(currentcard.getQuestion());
		nextButton.setText("show answer");
		isShowAnswer = true;
	}
	private void loadFile(File file) {
		cardlist = new ArrayList<QuizCard>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine())!=null) {
				makeCard(line);
				
			}
		}catch(Exception ex) {
			System.out.println("couldnt read the file");
			ex.printStackTrace();
		}
		showNextCard();
	}
	private void makeCard(String lineToParse) {
		String[]results = lineToParse.split("/");
		QuizCard card = new QuizCard(results[0],results[1]);
		cardlist.add(card);
		System.out.println("made a card");
				
	}
}


