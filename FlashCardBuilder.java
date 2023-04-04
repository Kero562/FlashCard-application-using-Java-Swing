package JavaFx.FlashCard;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class FlashCardBuilder {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardlist; //created class/obj each holding a question and answer
    private JFrame frame;

    public FlashCardBuilder()
    { //GUI
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Panel to hold everything
        JPanel mainPanel = new JPanel();

        //Font
        Font font = new Font("Helevetica Neue", Font.BOLD, 21);

        //Question text area -- Scroller will be vertical only
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(font);

        JScrollPane qJScrollPane = new JScrollPane(question);
        qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Answer Text Area -- Scroller will be vertical only
        answer = new JTextArea(6, 20);
        answer.setLineWrap(true); //Automatically go to the next line when the line is over instead of extending
        answer.setWrapStyleWord(true); //This means that if a word is too long to fit on a line, the entire word will be moved to the next line, 
                                            //rather than breaking the word in the middle and displaying part of it on the first line and the rest on the next.
        answer.setFont(font);

        JScrollPane scroll = new JScrollPane(answer);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Next button
        JButton nextButton = new JButton("Next Card");

        //Create a few labels
        JLabel qJLabel = new JLabel("Question");
        JLabel aJLabel = new JLabel("Answer");

        //Add components to mainPanel
        mainPanel.add(qJLabel);
        mainPanel.add(qJScrollPane);
        mainPanel.add(aJLabel);
        mainPanel.add(scroll);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        cardlist = new ArrayList<FlashCard>();

        //MenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");

        //Add items to Menu and the Menu to the bar
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        //Add eventListeners
        newMenuItem.addActionListener(new newMenuItemListener());
        saveMenuItem.addActionListener(new saveMenuListener());

        //Add components to frame:

        //Add mainPanel
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel); //ALT?: frame.add(mainPanel);
        //Add menuBar
        frame.setJMenuBar(menuBar);

        frame.setResizable(false);
        frame.setSize(450, 500);
        //Make frame visible
        frame.setVisible(true);
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run()
            {
                new FlashCardBuilder();
            }
        });
    }

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //save the current flashcard before going next
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardlist.add(card);

            question.setText("");
            answer.setText("");
            question.requestFocus();
        }
        
    }

    class newMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }

    class saveMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!question.getText().isEmpty() && !answer.getText().isEmpty())
            {
                FlashCard card = new FlashCard(question.getText(), answer.getText());
                cardlist.add(card);
            }
            //create a file dialog with file chooser
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
        
    }

    private void saveFile(File selectedFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));

            for (FlashCard card : cardlist)
            {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }

            writer.close();
        } catch (Exception e)
        {
            System.out.println("Couldn't write to file");
            e.printStackTrace();
        }
    }
}
