import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
    The first menu the user sees when they enter the game.
    Gives the option of starting a new game or loading from a
    previously saved version.
*/

public class StartMenuGUI extends JFrame implements ActionListener{
    //Instance Variables
    private Container mainPane;
    private JButton startNewGameButton, loadSavedGameButton;
    private JPanel buttonPanel, informationPanel;
    private JLabel infoMessage;

    //Constructor
    public StartMenuGUI(){
        super();

        mainPane = getContentPane();
        mainPane.setPreferredSize(new Dimension(500,500));
        mainPane.setLayout(new BorderLayout());
        pack();
        setLocationRelativeTo(null);
        //Make Buttons
        startNewGameButton = new JButton("START NEW GAME");
        loadSavedGameButton = new JButton("LOAD SAVED GAME");

        //TODO Add listeners to buttons
        
        startNewGameButton.addActionListener(this);
        loadSavedGameButton.addActionListener(this);
        //Make the information label
        infoMessage = new JLabel("Welcome to Ricochet Robots!");
        informationPanel = new JPanel();
        informationPanel.add(infoMessage);
        mainPane.add(informationPanel, BorderLayout.NORTH);

        //Make the button label
        buttonPanel = new JPanel();       
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(startNewGameButton);
        buttonPanel.add(loadSavedGameButton);
        mainPane.add(buttonPanel, BorderLayout.SOUTH);        
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.BLUE);
        JLabel gameLogo = new JLabel();
        gameLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("RR.jpeg")));
        logoPanel.add(gameLogo);
        mainPane.add(logoPanel, BorderLayout.CENTER);
        //Pack and make visible
        pack();
        setVisible(true);        
    }

    public void actionPerformed(ActionEvent e){
        Object event = e.getSource();
        if(event == startNewGameButton){
            super.dispose();
            ChooseDisplayGUI newChooseDisplayGUI= new ChooseDisplayGUI();
        } // update this with actual loading of the game
        else if(event == loadSavedGameButton){
            String message = "***Load Game Option to be added later.***";
            JOptionPane loadGame = new JOptionPane();
            loadGame.showMessageDialog(new JFrame(), message);
        }
    }
    //Methods
}