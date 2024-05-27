import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {

    private static final int MAX_GUESSES = 7;

    private String[] words; // Array to store words read from file
    private String targetWord; // The word to guess
    private StringBuilder display; // Current progress of the guessed word
    private int incorrectGuesses; // Number of incorrect guesses

    public HangmanGame() {
        words = readFileIntoArray("words"); // Initialize the words array by reading from file
        display = new StringBuilder(); // Initialize the display StringBuilder
        incorrectGuesses = 0; // Initialize the incorrect guesses counter
    }

    // Read words from file and return as an array of strings
    private static int countLinesInFile(String nameOfFile) throws FileNotFoundException {

        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);

        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            scanner.nextLine();
        }
        return lineCount;
    }

    private static String[] readFileIntoArray(String nameOfFile)  {
        String[] array = null;
        try {
            int linesInFile = countLinesInFile(nameOfFile);
            array = new String[linesInFile];

            File file = new File(nameOfFile);
            Scanner scanner = new Scanner(file);

            int index = 0;
            while (scanner.hasNextLine()) {
                array[index++] = scanner.nextLine().strip();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Word file not found.");
            System.exit(0);
        }
        return array;
    }

    // Get a random word from the array
    private String getRandomWord(String[] words) {
        Random random = new Random(); // Create a Random object for generating random numbers
        int randomIndex = random.nextInt(words.length); // Generate a random index within the bounds of the words array
        return words[randomIndex]; // Return the word at the randomly chosen index
    }

    public void play() {
        targetWord = getRandomWord(words); // Choose a random word from the words array list
        for (int i = 0; i < targetWord.length(); i++) {
            display.append("-"); // Fill the display with dashes
        }

        System.out.println("Welcome to Hangman! Let's begin."); // Display a welcome message for the user
        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        while (incorrectGuesses < MAX_GUESSES) { // Loop until the maximum number of incorrect guesses is hit by the user
            System.out.println("Guess a singular letter: "); // Prompt the user to enter a letter
            String input = scanner.next(); // Read the user's input value

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid input. Please enter a single letter only."); // Display an error message for invalid input or incorrect input
                continue; // Skip to the next iteration of the loop
            }

            char guess = Character.toLowerCase(input.charAt(0)); // Convert the user's guess to lowercase

            boolean found = false; // Initialize a flag to track if the guess is seen
            char[] targetWordChars = targetWord.toCharArray(); // Convert the target word to a char
            char[] displayChars = display.toString().toCharArray(); // Convert the string to a char
            for (int i = 0; i < targetWordChars.length; i++) {
                if (Character.toLowerCase(targetWordChars[i]) == guess) { // Compare the lowercase target word char with a lowercase guess
                    displayChars[i] = guess; // Update the display char array with a correct guess
                    found = true; // Set the system to true
                }
            }

            if (!found) {
                incorrectGuesses++; // increase incorrect guesses counter if the guess was not found
            }

            display = new StringBuilder(String.valueOf(displayChars)); // Update the display string with the changed char array

            System.out.println("Current progress: " + display); // Display the current progress of the game
            System.out.println("Amount of Incorrect guesses: " + incorrectGuesses); // Display the number of incorrect guesses of the user
            System.out.println("Guesses left in the game: "+ (MAX_GUESSES - incorrectGuesses)); // Display the remaining guesses for the user

            if (!display.toString().contains("-")) {
                System.out.println("Congratulations! You guessed the word correctly. The game is now finished!"); // Display a victory message if the word is guessed correctly
                return; // Exit the play method
            }
        }

        System.out.println("Game over, Oh no! You ran out of guesses. The word was: " + targetWord); // Revel the target word to the user
    }

    public static void main(String[] args)  {
        HangmanGame game = new HangmanGame(); // Create an instance of the HangmanGame class
        game.play(); // Start the game by calling the play method used above
    }

}