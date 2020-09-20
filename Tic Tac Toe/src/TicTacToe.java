import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** A simple Tic-Tac-Toe Game using Java Swing
 * @author Shabab Noor
 * @version 1.0
 */

public class TicTacToe extends JFrame {

    //Custom JLabel class
    static class JLabel extends javax.swing.JLabel {
        JLabel(){
            super(tab);
        }
        JLabel(String text){
            super(text);
            setFont(new Font("Arial", Font.BOLD, 14));
            //Setting default size and font for every JLabel.
        }
    }

    //Attributes
    private int playerIndex = -1, winNumPlayer1 = 0, winNumPlayer2 = 0;
    private final int[] position = new int[9];
    private final JTextField[] textFields = new JTextField[5];
    private final JButton[] buttons = new JButton[9];
    private final JPanel gameWindow = new JPanel();

    private final static String nextPlayer1 = "Player 1 will move first next game.";
    private final static String nextPlayer2 = "Player 2 will move first next game.";
    private final static String congrats = "Congratulations!";
    private final static String winnerPlayer1 = "Player 1 won! "+congrats;
    private final static String winnerPlayer2 = "Player 2 won! "+congrats;
    private final static String tab = "           "; //used this to add empty space as JLabel doesn't allow "\t."

    public TicTacToe(){
        super("Tic-Tac-Toe Game");
        setSize(550, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        JPanel header = new JPanel(), mainBody = new JPanel(), footer = new JPanel();

        //Header Section
        header.setLayout(new GridLayout(4,0));
        JPanel welcome= new JPanel();
        JLabel welcomeText = new JLabel("Welcome to TIC-TAC-TOE!\n");
        welcomeText.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel[] label = new JLabel[2];
        welcome.add(welcomeText);
        JPanel playerInfo = new JPanel();

        for (int i=0; i<2; i++){
            label[i] = new JLabel("Player " + (i + 1) + " num of wins: ");
            textFields[i] = new JTextField(5);
            textFields[i].setEditable(false);
            playerInfo.add(label[i]);
            playerInfo.add(textFields[i]);
        }
        header.add(new JPanel().add(new JLabel("  ")));
        header.add(welcome);
        header.add(playerInfo);
        header.add(new JPanel().add(new JLabel("  ")));

        //Main Body Section and Action Listener
        mainBody.setLayout(new GridLayout(3,3));
        mainBody.setSize(480,480);
        mainBody.setMaximumSize(new Dimension(700, 700));

        for(int i=0; i<9; i++){
            buttons[i] = new JButton("");
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 50));
            mainBody.add(buttons[i]);
        }
        for (int i=0; i<9; i++) {
            JButton finalButton = buttons[i];
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    finalButton.setEnabled(false);
                    if (playerIndex < 0){
                        finalButton.setText("O");
                        finalButton.setBackground(Color.blue);
                    }
                    else{
                        finalButton.setText("X");
                        finalButton.setBackground(Color.red);
                    }
                    finalButton.setForeground(new Color(255, 255, 255)); //This isn't working for some reason. Button texts are still grey.
                    for (int i=0; i<9; i++){
                        if (e.getSource() == buttons[i]) position[i] = playerIndex;
                    }
                    if (winnerChecker() == -1){
                        winNumPlayer1++;
                        textFields[0].setText(winNumPlayer1+"");
                        JOptionPane.showMessageDialog(gameWindow,winnerPlayer1+"\n"+nextPlayer2);
                        resetGame();
                    }
                    else if (winnerChecker() == 1){
                        winNumPlayer2++;
                        textFields[1].setText(winNumPlayer2+"");
                        JOptionPane.showMessageDialog(gameWindow,winnerPlayer2+"\n"+nextPlayer1);
                        resetGame();
                    }
                    else drawChecker();
                    playerIndex *= -1;
                }
            });
        }

        //Footer Section Code
        JPanel footerLeft = new JPanel(), footerRight = new JPanel();
        footer.setLayout(new GridLayout(1, 2));
        footerLeft.setLayout(new GridLayout(6, 1));
        footerRight.setLayout(new GridLayout(6, 1));
        footer.add(footerLeft);
        footer.add(footerRight);

        //Footer Left
        JLabel[] instructions = new JLabel[4];
        instructions[0] = new JLabel("Instructions:");
        instructions[1] = new JLabel("1. Player 1 is blue and circle.");
        instructions[2] = new JLabel("2. Player 2 is red and cross.");
        instructions[3] = new JLabel("3. 1st move changes each game.");
        footerLeft.add(new JPanel().add(new JLabel(""))); //Empty Line
        for (JLabel instruction:instructions){
            JPanel panel = new JPanel();
            panel.add(instruction);
            footerLeft.add(panel);
        }
        footerLeft.add(new JPanel().add(new JLabel(""))); //Empty Line

        //Footer Right
        JLabel bottomText = new JLabel("May the best player win!");
        JButton resetBoard = new JButton("Reset Board");
        JButton resetScore = new JButton("Reset Score");
        JButton endGame = new JButton("End Game");

        footerRight.add(new JPanel().add(new JLabel(""))); //Empty Line
        JPanel[] panels = new JPanel[4];
        for (int i=0; i<4; i++){
            panels[i] = new JPanel();
            footerRight.add(panels[i]);
        }

        panels[0].add(bottomText);
        panels[1].add(resetBoard);
        panels[2].add(resetScore);
        panels[3].add(endGame);
        footerRight.add(new JPanel().add(new JLabel(""))); //Empty Line

        //Footer buttons action listener
        resetBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        resetScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                winNumPlayer1 = 0; winNumPlayer2 = 0;
                textFields[0].setText("");
                textFields[1].setText("");
                String reset = "Scores have been reset!";
                if(playerIndex == -1) JOptionPane.showMessageDialog(gameWindow,reset+"\n"+nextPlayer1);
                else JOptionPane.showMessageDialog(gameWindow,reset+"\n"+nextPlayer2);
            }
        });

        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                String result = ": : Final Score : : \nPlayer 1: "+winNumPlayer1+" \nPlayer 2: "+winNumPlayer2;
                if (winNumPlayer1 > winNumPlayer2) JOptionPane.showMessageDialog(gameWindow,"Player 1 is the winner!\n"+congrats+"\n\n"+result);
                else if (winNumPlayer1 < winNumPlayer2) JOptionPane.showMessageDialog(gameWindow,"Player 2 is the winner!\n"+congrats+"\n\n"+result);
                else JOptionPane.showMessageDialog(gameWindow,"It's a DRAW! Good Game!");
                System.exit(0);
            }
        });

        //Final placements
        gameWindow.add(header, BorderLayout.NORTH);
        gameWindow.add(mainBody, BorderLayout.CENTER);
        gameWindow.add(footer, BorderLayout.SOUTH);
        gameWindow.add(new JPanel().add(new JLabel()), BorderLayout.EAST);
        gameWindow.add(new JPanel().add(new JLabel()), BorderLayout.WEST);
        setContentPane(gameWindow);
        setVisible(true);
    }

    int winnerChecker(){ //Checks if the last move results in any player winning or not.
        if (position[0] == position[1] && position[0] == position[2] && position[0] != 0) return position[0];
        else if(position[3] == position[4] && position[3] == position[5] && position[3] != 0) return position[3];
        else if(position[6] == position[7] && position[6] == position[8] && position[6] != 0) return position[6];
        else if(position[0] == position[3] && position[0] == position[6] && position[0] != 0) return position[0];
        else if(position[1] == position[4] && position[1] == position[7] && position[1] != 0) return position[1];
        else if(position[2] == position[5] && position[2] == position[8] && position[2] != 0) return position[2];
        else if(position[4] == position[0] && position[4] == position[8] && position[4] != 0) return position[4];
        else if(position[4] == position[2] && position[4] == position[6] && position[4] != 0) return position[4];
        else return 0;
    }

    void resetGame(){ //Resets the game.
        for (JButton button:buttons){
            button.setEnabled(true);
            button.setBackground(Color.WHITE);
            button.setText("");
        }
        for (int i=0; i<9; i++) position[i] = 0;
    }

    void drawChecker(){ //Checks if the game results in a draw or not.
        for (JButton button:buttons){
            if (button.isEnabled()) return;
        }
        if(playerIndex == -1) JOptionPane.showMessageDialog(gameWindow,"It's a DRAW! Try again! \n"+nextPlayer2);
        else JOptionPane.showMessageDialog(gameWindow,"It's a DRAW! Try again!\n"+nextPlayer1);
        resetGame();
    }

    //Main Method
    public static void main(String[] args) {
        new TicTacToe();
    }
}
