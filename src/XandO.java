import java.awt.*;
import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class XandO {
    JFrame welcomeFrame, gameFrame;
    JTextField player1Field, player2Field;
    String player1Name = "Player 1";
    String player2Name = "Player 2";
    String currentPlayer;
    JButton[][] board = new JButton[3][3];
    JLabel statusLabel, scoreLabel;
    boolean gameOver = false;
    int turns = 0;
    int player1Score = 0;
    int player2Score = 0;

    public static void main(String[] args) {
        new XandO();
    }

    public XandO() {
        showWelcomeScreen();
    }

    void showWelcomeScreen() {
        welcomeFrame = new JFrame("Welcome to XandO");
        welcomeFrame.setSize(400, 300);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLayout(new GridLayout(4, 1));
        welcomeFrame.setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to XandO", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeFrame.add(welcomeLabel);

        player1Field = new JTextField("Enter Player 1 Name");
        player2Field = new JTextField("Enter Player 2 Name");
        welcomeFrame.add(player1Field);
        welcomeFrame.add(player2Field);

        // Clear placeholder text on focus
        player1Field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (player1Field.getText().equals("Enter Player 1 Name")) {
                    player1Field.setText("");
                }
            }
        });

        player2Field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (player2Field.getText().equals("Enter Player 2 Name")) {
                    player2Field.setText("");
                }
            }
        });

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> {
            player1Name = player1Field.getText().trim().isEmpty() ? "Player 1" : player1Field.getText().trim();
            player2Name = player2Field.getText().trim().isEmpty() ? "Player 2" : player2Field.getText().trim();
            currentPlayer = player1Name;
            welcomeFrame.dispose();
            initGame();
        });

        welcomeFrame.add(continueButton);
        welcomeFrame.setVisible(true);
    }

    void initGame() {
        gameFrame = new JFrame("XandO Game");
        gameFrame.setSize(600, 700);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(245, 245, 245));

        statusLabel = new JLabel(currentPlayer + "'s Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 32));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(60, 63, 65));
        statusLabel.setForeground(Color.WHITE);
        topPanel.add(statusLabel);

        scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(220, 220, 220));
        scoreLabel.setForeground(Color.BLACK);
        topPanel.add(scoreLabel);

        gameFrame.add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(200, 200, 255));
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton button = new JButton();
                board[r][c] = button;
                button.setFont(new Font("Arial", Font.BOLD, 120));
                button.setFocusable(false);
                button.setBackground(new Color(100, 149, 237)); // Cornflower Blue
                button.setForeground(Color.WHITE);

                final int row = r, col = c;
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver || !button.getText().equals("")) return;

                        button.setText(currentPlayer.equals(player1Name) ? "X" : "O");
                        button.setForeground(Color.WHITE);
                        turns++;
                        checkWinner();

                        if (!gameOver) {
                            currentPlayer = currentPlayer.equals(player1Name) ? player2Name : player1Name;
                            statusLabel.setText(currentPlayer + "'s Turn");
                        }
                    }
                });

                boardPanel.add(button);
            }
        }

        gameFrame.setVisible(true);
    }

    void checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].getText().equals("") &&
                    board[i][0].getText().equals(board[i][1].getText()) &&
                    board[i][1].getText().equals(board[i][2].getText())) {
                highlightWinner(board[i][0], board[i][1], board[i][2]);
                showWinner(currentPlayer);
                return;
            }
            if (!board[0][i].getText().equals("") &&
                    board[0][i].getText().equals(board[1][i].getText()) &&
                    board[1][i].getText().equals(board[2][i].getText())) {
                highlightWinner(board[0][i], board[1][i], board[2][i]);
                showWinner(currentPlayer);
                return;
            }
        }

        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            highlightWinner(board[0][0], board[1][1], board[2][2]);
            showWinner(currentPlayer);
            return;
        }

        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            highlightWinner(board[0][2], board[1][1], board[2][0]);
            showWinner(currentPlayer);
            return;
        }

        if (turns == 9) {
            JOptionPane.showMessageDialog(gameFrame, "It's a Tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            restartGame();
        }
    }
    void highlightWinner(JButton b1, JButton b2, JButton b3) {
        Color winColor = new Color(0, 128, 0); // Dark green
        b1.setBackground(winColor);
        b2.setBackground(winColor);
        b3.setBackground(winColor);
        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.WHITE);
        b3.setForeground(Color.WHITE);
    }

    void showWinner(String winner) {
        gameOver = true;
        if (winner.equals(player1Name)) player1Score++;
        else if (winner.equals(player2Name)) player2Score++;

        JOptionPane.showMessageDialog(gameFrame, winner + " Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        restartGame();
    }

    void restartGame() {
        int response = JOptionPane.showConfirmDialog(gameFrame, "Do you want to play again?", "Restart", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            turns = 0;
            gameOver = false;
            currentPlayer = player1Name;
            statusLabel.setText(currentPlayer + "'s Turn");
            scoreLabel.setText(getScoreText());

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    board[r][c].setText("");
                    board[r][c].setBackground(new Color(100, 149, 237));
                    board[r][c].setForeground(Color.WHITE);
                }
            }
        } else {
            System.exit(0);
        }
    }

    String getScoreText() {
        return player1Name + " (X): " + player1Score + "  |  " + player2Name + " (O): " + player2Score;
    }
}


