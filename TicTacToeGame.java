import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToeGame extends JFrame {
    private static final int BOARD_SIZE = 3;
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    private String currentPlayer;
    private JButton[][] boardButtons;

    public TicTacToeGame() {
        currentPlayer = PLAYER_X;
        boardButtons = new JButton[BOARD_SIZE][BOARD_SIZE];

        initializeGUI();
        initializeBoard();
    }

    private void initializeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.PLAIN, 60));
                button.addActionListener(new ButtonClickListener(row, col));
                boardButtons[row][col] = button;
                add(button);
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boardButtons[row][col].setEnabled(true);
                boardButtons[row][col].setText("");
            }
        }
    }

    private void checkWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (boardButtons[i][0].getText().equals(currentPlayer) &&
                    boardButtons[i][1].getText().equals(currentPlayer) &&
                    boardButtons[i][2].getText().equals(currentPlayer)) {
                announceWinner(currentPlayer);
                return;
            }
            if (boardButtons[0][i].getText().equals(currentPlayer) &&
                    boardButtons[1][i].getText().equals(currentPlayer) &&
                    boardButtons[2][i].getText().equals(currentPlayer)) {
                announceWinner(currentPlayer);
                return;
            }
        }

        if (boardButtons[0][0].getText().equals(currentPlayer) &&
                boardButtons[1][1].getText().equals(currentPlayer) &&
                boardButtons[2][2].getText().equals(currentPlayer)) {
            announceWinner(currentPlayer);
            return;
        }

        if (boardButtons[0][2].getText().equals(currentPlayer) &&
                boardButtons[1][1].getText().equals(currentPlayer) &&
                boardButtons[2][0].getText().equals(currentPlayer)) {
            announceWinner(currentPlayer);
            return;
        }

        // Check for a draw
        if (isBoardFull()) {
            announceWinner("It's a draw!");
            return;
        }

        // Switch to the other player if no win or draw
        currentPlayer = (currentPlayer.equals(PLAYER_X)) ? PLAYER_O : PLAYER_X;
    }

    private void announceWinner(String winner) {
        JOptionPane.showMessageDialog(this, "Winner: " + winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        initializeBoard();
        currentPlayer = PLAYER_X;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (boardButtons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (boardButtons[row][col].getText().isEmpty()) {
                boardButtons[row][col].setText(currentPlayer);
                boardButtons[row][col].setEnabled(false);
                checkWin();
                if (currentPlayer.equals(PLAYER_O)) {
                    makeAIMove();
                }
            }
        }
    }

    private void makeAIMove() {
        if (!isBoardFull()) {
            Random random = new Random();
            int row, col;
            do {
                row = random.nextInt(BOARD_SIZE);
                col = random.nextInt(BOARD_SIZE);
            } while (!boardButtons[row][col].getText().isEmpty());

            boardButtons[row][col].setText(currentPlayer);
            boardButtons[row][col].setEnabled(false);
            checkWin();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGame::new);
    }
}
