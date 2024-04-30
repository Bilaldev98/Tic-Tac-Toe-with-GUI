import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {

    private JButton[][] buttons;
    private char[][] board;
    private boolean playerXTurn;
    private boolean gameOver;
    private boolean playWithComputer; // Added variable to track if playing with computer

    public TicTacToeGUI(boolean playWithComputer) {
        this.playWithComputer = playWithComputer; // Set whether playing with computer or not
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        board = new char[3][3];
        playerXTurn = true;
        gameOver = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver)
            return;

        JButton clickedButton = (JButton) e.getSource();
        int row = -1, col = -1;

        // Finding the button clicked
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == clickedButton) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Check if the selected move is valid
        if (board[row][col] == '\0') {
            if (playerXTurn) {
                clickedButton.setText("X");
                board[row][col] = 'X';
                if (hasContestantWon(board, 'X')) {
                    JOptionPane.showMessageDialog(this, "Player X wins!");
                    gameOver = true;
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(this, "The game ended in a tie!");
                    gameOver = true;
                } else {
                    playerXTurn = false;
                    if (playWithComputer) {
                        computerTurn();
                    }
                }
            } else {
                clickedButton.setText("O");
                board[row][col] = 'O';
                if (hasContestantWon(board, 'O')) {
                    JOptionPane.showMessageDialog(this, "Player O wins!");
                    gameOver = true;
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(this, "The game ended in a tie!");
                    gameOver = true;
                } else {
                    playerXTurn = true;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move. Please choose an empty cell.");
        }
    }

    private boolean hasContestantWon(char[][] board, char symbol) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        // Check if the board is full
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    // Computer's turn logic
    private void computerTurn() {
        // Implement computer's move here
        // For simplicity, random move is implemented
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (board[row][col] != '\0');

        buttons[row][col].doClick(); // Simulate click on the button
    }

    public static void main(String[] args) {
        int choice = JOptionPane.showOptionDialog(null, "Choose your opponent:", "Tic Tac Toe",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Play against the computer", "Play against another player"}, null);
        boolean playWithComputer = (choice == JOptionPane.YES_OPTION);
        new TicTacToeGUI(playWithComputer);
    }
}
