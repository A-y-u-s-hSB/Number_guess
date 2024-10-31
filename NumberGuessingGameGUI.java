import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class NumberGuessingGameGUI extends JFrame implements ActionListener {
    private static final int MAX_ATTEMPTS = 5;
    private static final int ROUNDS = 3;

    private Random random;
    private int currentRound;
    private int currentScore;
    private int randomNumber;
    private int attempts;
    private int points;

    private List<Integer> correctNumbers; // List to store correct numbers from each round

    private JLabel roundLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JTextArea resultArea;

    public NumberGuessingGameGUI() {
        random = new Random();
        currentRound = 1;
        currentScore = 0;
        correctNumbers = new ArrayList<>(); // Initialize the list to store correct numbers

        // Set up the frame
        setTitle("Number Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize components
        roundLabel = new JLabel("Round: " + currentRound);
        attemptsLabel = new JLabel("Attempts: " + 0);
        scoreLabel = new JLabel("Score: " + currentScore);
        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        // Add action listener to the button
        guessButton.addActionListener(this);

        // Add components to the frame
        add(roundLabel);
        add(attemptsLabel);
        add(scoreLabel);
        add(new JLabel("Enter your guess (1 to 100): "));
        add(guessField);
        add(guessButton);
        add(new JScrollPane(resultArea));

        // Start the game
        startNewRound();

        setVisible(true);
    }

    private void startNewRound() {
        randomNumber = random.nextInt(100) + 1; // Random number between 1 and 100
        attempts = 0;
        points = 100; // Reset points for the new round
        attemptsLabel.setText("Attempts: " + attempts);
        resultArea.setText("Round " + currentRound + " has begun! Make your guess.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int userGuess = Integer.parseInt(guessField.getText());
            if (userGuess < 1 || userGuess > 100) {
                resultArea.setText("Please enter a number between 1 and 100.");
                return;
            }

            attempts++;
            attemptsLabel.setText("Attempts: " + attempts);

            // Check the guess
            if (userGuess == randomNumber) {
                resultArea.setText("Congratulations! You guessed the correct number " + randomNumber + ".");
                currentScore += points; // Update score
                scoreLabel.setText("Score: " + currentScore);
                correctNumbers.add(randomNumber); // Store the correct number
                endRound();
            } else if (userGuess < randomNumber) {
                resultArea.setText("Your guess is too low.");
            } else {
                resultArea.setText("Your guess is too high.");
            }

            // Reduce points based on number of attempts
            points -= 20;

            if (attempts >= MAX_ATTEMPTS) {
                resultArea.setText("Sorry! You've used all your attempts. The correct number was " + randomNumber + ".");
                correctNumbers.add(randomNumber); // Store the correct number
                endRound();
            }

            guessField.setText(""); // Clear input field

        } catch (NumberFormatException ex) {
            resultArea.setText("Invalid input. Please enter a valid number.");
        }
    }

    private void endRound() {
        currentRound++;
        if (currentRound <= ROUNDS) {
            roundLabel.setText("Round: " + currentRound);
            startNewRound();
        } else {
            resultArea.append("\nGame Over! Your total score: " + currentScore);
            resultArea.append("\nThe correct numbers were: " + correctNumbers.toString() + "."); // Show all correct answers
            guessButton.setEnabled(false); // Disable button
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGameGUI::new);
    }
}
