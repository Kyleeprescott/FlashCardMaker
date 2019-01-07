package FlashCard;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
/*
 * Kylee Prescott
 * 1/19
 * quiz card builder 
 * this class will alllow the user to create a set of quiz cards and use them for future 
 * use with the quiz card player 
 * 
 */
public class QuizCardBuilder {
private JTextArea question;
private JTextArea answer;
private ArrayList<QuizCard>cardlist;
private JFrame frame;



	public static void main(String[] args) {
     QuizCardBuilder builder = new QuizCardBuilder();
     builder.go();

	}
	public void go() {
		/*
		 * build GUI
		 * this is all GUI code nothing special although 
		 * you might want to look at the menu bar section 
		 */
		frame = new JFrame("Quiz Card Builder");
		Font bigFont = new Font("sanseirf",Font.BOLD,24);
		JPanel  mainPanel= new JPanel();
		
		
		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question .setWrapStyleWord(true);
		question.setFont(bigFont);
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer .setWrapStyleWord(true);
		answer.setFont(bigFont);
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JButton nextButton = new JButton ("Next Card");
		cardlist = new ArrayList<QuizCard>();
		JLabel qLabel = new JLabel ("Question:");
		JLabel aLabel = new JLabel ("Answer:");
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());
		
		/*
		 * 
		 */
			JMenuBar menuBar = new JMenuBar();
			JMenu fileMenu = new JMenu("File");
			JMenuItem newMenuItem = new JMenuItem("New");
			JMenuItem saveMenuItem = new JMenuItem("Save");
			newMenuItem.addActionListener(new NewMenuListener());
			saveMenuItem.addActionListener(new SaveMenuListener());
			fileMenu.add(newMenuItem);
			fileMenu.add(saveMenuItem);
			menuBar.add(fileMenu);
			frame.setJMenuBar(menuBar);
			
		
		
		
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
		
	}public class NextCardListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = card = new QuizCard(question.getText(),answer.getText());
			cardlist.add(card);
			clearCard();
		
		}
	}//end cardlistener
	public class NewMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {

			cardlist.clear();
			clearCard();
		
		}
	}//end cardlistener
	
	public class SaveMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardlist.add(card);
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}
	}
	private void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
		
	}
	private void saveFile(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (QuizCard card :cardlist) {
				writer.write(card.getQuestion()+"/");
				writer.write(card.getAnswer()+"\n");
			}
			writer.close();
		}
		catch(IOException ex) {
			System.out.println("couldnt write the cardlist out");
			ex.printStackTrace();
			
		}
	}

}
