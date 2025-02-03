package strings;
import java.util.*;

public class finalproject {
    static Scanner scanner = new Scanner(System.in);
    static int score = 0;
    static boolean fiftyFiftyUsed = false, audienceUsed = false, skipUsed = false;

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz Application!");

        // Collect Player Details
        System.out.print("Please enter your Name: ");
        String playerName = scanner.nextLine();
        System.out.println("Hello" + playerName + "! Are you ready to start the quiz?");
        System.out.println("Rules: Answer correctly to earn points. Use lifelines wisely. Each lifeline can only be used once.");

        System.out.print("Do you agree to the rules? (yes/no): ");
        String agree = scanner.nextLine();

        if (agree.equalsIgnoreCase("yes")) {
            startQuiz();
        } else {
            System.out.println("Thank you! Come back again.");
        }
    }

    public static void startQuiz() {
        // List of questions
        String[][] questions = {
            {"What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "3"},
            {"Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus", "2"},
            {"Who wrote 'Hamlet'?", "Charles Dickens", "William Shakespeare", "Mark Twain", "Leo Tolstoy", "2"},
            {"What is the square root of 144?", "10", "12", "14", "16", "2"},
            {"Which element has the chemical symbol 'O'?", "Gold", "Oxygen", "Osmium", "Hydrogen", "2"}
        };

        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + questions[i][0]);
            System.out.println("1. " + questions[i][1]);
            System.out.println("2. " + questions[i][2]);
            System.out.println("3. " + questions[i][3]);
            System.out.println("4. " + questions[i][4]);

            boolean answered = false;
            while (!answered) {
                if (areLifelinesAvailable()) {
                    System.out.print("Enter your answer (1-4) or type 'lifeline' to use a lifeline: ");
                } else {
                    System.out.print("Enter your answer (1-4): ");
                }

                String input = scanner.nextLine();

                if (isValidOption(input)) {
                    if (input.equals(questions[i][5])) {
                        System.out.println("Correct! You earned 10 points.");
                        score += 10;
                        answered = true;
                    } else {
                        System.out.println("Wrong answer. The correct answer was: " + questions[i][Integer.parseInt(questions[i][5])]);
                        System.out.println("Game Over. Your final score is: " + score);
                        return; 
                    }
                } else if (input.equalsIgnoreCase("lifeline") && areLifelinesAvailable()) {
                    if (!showAvailableLifelines(questions[i])) {
                        System.out.println("No lifelines are available!");
                    }
                } else if (input.equalsIgnoreCase("lifeline") && !areLifelinesAvailable()) {
                    System.out.println("All lifelines have been used! Enter your answer (1-4).");
                } else {
                    System.out.println("Invalid input! Please enter a number between 1-4 or 'lifeline'.");
                }
            }

            System.out.print("Do you want to quit? (yes/no): ");
            String quit = scanner.nextLine();
            if (quit.equalsIgnoreCase("yes")) {
                System.out.println("You decided to quit. Your final score is: " + score);
                return;
            }
        }

        System.out.println("\nQuiz completed! Your final score is: " + score);
    }

    public static boolean showAvailableLifelines(String[] question) {
        boolean lifelineUsed = false;
        System.out.println("\nAvailable Lifelines:");
        if (!fiftyFiftyUsed) System.out.println("1. 50-50");
        if (!audienceUsed) System.out.println("2. Audience Poll");
        if (!skipUsed) System.out.println("3. Skip Question");

        System.out.print("Choose a lifeline (enter the number): ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                if (!fiftyFiftyUsed) {
                    fiftyFiftyUsed = true;
                    useFiftyFifty(question);
                    lifelineUsed = true;
                } else {
                    System.out.println("50-50 lifeline already used.");
                }
                break;
            case "2":
                if (!audienceUsed) {
                    audienceUsed = true;
                    useAudiencePoll(question);
                    lifelineUsed = true;
                } else {
                    System.out.println("Audience Poll lifeline already used.");
                }
                break;
            case "3":
                if (!skipUsed) {
                    skipUsed = true;
                    System.out.println("You skipped the question. Moving to the next one.");
                    lifelineUsed = true;
                } else {
                    System.out.println("Skip Question lifeline already used.");
                }
                break;
            default:
                System.out.println("Invalid choice. No lifeline used.");
                break;
        }
        return lifelineUsed;
    }

    public static void useFiftyFifty(String[] question) {
        System.out.println("50-50 Lifeline Activated! Two incorrect options are removed.");
        Random rand = new Random();
        int correctOption = Integer.parseInt(question[5]);
        int otherOption;
        do {
            otherOption = rand.nextInt(4) + 1;
        } while (otherOption == correctOption);

        System.out.println("Options:");
        System.out.println(correctOption + ". " + question[correctOption]); // Fixed indexing
        System.out.println(otherOption + ". " + question[otherOption]);     // Fixed indexing
    }

    public static void useAudiencePoll(String[] question) {
        System.out.println("Audience Poll Lifeline Activated! Here are the audience opinions:");
        Random rand = new Random();
        int correctOption = Integer.parseInt(question[5]);

        int correctPercentage = rand.nextInt(41) + 60; // 60% to 100%
        int remainingPercentage = 100 - correctPercentage;

        int[] percentages = new int[4];
        percentages[correctOption - 1] = correctPercentage;
        for (int i = 0; i < 4; i++) {
            if (i != correctOption - 1) {
                percentages[i] = rand.nextInt(remainingPercentage + 1);
                remainingPercentage -= percentages[i];
            }
        }

        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + question[i + 1] + " - " + percentages[i] + "%");
        }
    }

    public static boolean areLifelinesAvailable() {
        return !fiftyFiftyUsed || !audienceUsed || !skipUsed;
    }

    public static boolean isValidOption(String input) {
        return input.matches("^[1-4]$"); // Fix for strict validation
    }
}