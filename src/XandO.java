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
