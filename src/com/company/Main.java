package com.company;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose one of the following: \n1. New test. \n2. Print summary. \n3. Exit. \n4. Check percentage.");
            int choice = scanner.nextInt();
            if (choice == 2) {
                printSummary();
            } else if (choice == 3) {
                System.exit(0);
            } else if (choice == 4) {
                checkPercent();
            } else if (choice == 1) {
                System.out.println("Welcome to the calculation training. What would you like to train? \n1. Plus\n2. Minus");
                int calcStyle = scanner.nextInt();
                System.out.println("What difficulty would you like to train? \n1. Easy\n2. Hard");
                int difficulty = scanner.nextInt();

                if (calcStyle == 1) {
                    plus(difficulty);
                } else if (calcStyle == 2) {
                    minus(difficulty);
                }
            }
        }
    }

    public static void plus(int difficulty) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int correctAnswers = 0;
        int wrongAnswers = 0;
        String plus = "Plus";
        int bound = 0;
        String diff = "";

        if (difficulty == 1) {
            bound = 10;
            diff = "Easy";
        } else if (difficulty == 2) {
            bound = 100;
            diff = "Hard";
        }

        System.out.println("The test will now start.");
        for (int i = 1; i <= 10; i++) {
            int number1 = random.nextInt(bound);
            int number2 = random.nextInt(bound);

            System.out.println(i + ".     " + number1 + " + " + number2 + " = ");
            int answer = scanner.nextInt();

            if (answer == number1 + number2) {
                System.out.println("That is correct!");
                correctAnswers++;
            } else if (answer != number1 + number2) {
                System.out.println("That is not correct. The answer is " + (number1 + number2));
                wrongAnswers++;
            }
        }
        statistics(correctAnswers, wrongAnswers, plus, diff);
        saveToTxt(correctAnswers, plus, diff);
    }

    public static void minus(int difficulty) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int correctAnswers = 0;
        int wrongAnswers = 0;
        int bound = 0;
        String minus = "Minus";
        String diff = "";

        if (difficulty == 1) {
            bound = 10;
            diff = "Easy";
        } else if (difficulty == 2) {
            bound = 100;
            diff = "Hard";
        }

        System.out.println("The test will now start.");
        for (int i = 1; i <= 10; i++) {
            int number1 = random.nextInt(bound);
            int number2 = random.nextInt(bound);

            System.out.println(i + ".     " + number1 + " - " + number2 + " = ");
            int answer = scanner.nextInt();

            if (answer == number1 - number2) {
                System.out.println("That is correct!");
                correctAnswers++;
            } else if (answer != number1 - number2) {
                System.out.println("That is not correct. The answer is " + (number1 - number2));
                wrongAnswers++;
            }
        }
        statistics(correctAnswers, wrongAnswers, minus, diff);
        saveToTxt(correctAnswers, minus, diff);
    }

    public static void statistics(int correct, int wrong, String style, String difficulty) {
        System.out.println("\n\nThe test is over. These are your statistics:");
        System.out.println("Number of correct answers: " + correct);
        System.out.println("Number of incorrect answers: " + wrong);
        System.out.println("Correct answers in %: " + (int) ((correct / 10.0) * 100.0) + "%");
        System.out.println("Calculation style: " + style);
        System.out.println("Difficulty: " + difficulty + "\n\n");
    }

    public static void saveToTxt(int correct, String style, String difficulty) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = scanner.next();
        System.out.println("Saving progress...");

        try {
            BufferedWriter input = new BufferedWriter(new FileWriter(name.toLowerCase(Locale.ROOT) + ".txt", true));
            input.write("Test: " + LocalDate.now() + " " + LocalTime.now());
            input.write("\nNumber of correct answers: " + correct);
            input.write("\nCalculation style: " + style);
            input.write("\nDifficulty: " + difficulty + "\n\n");
            input.close();
            System.out.println("Progress has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printSummary() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name you want a summary for: ");
        String name = scanner.nextLine().toLowerCase();
        Scanner out = new Scanner(new File(name + ".txt"));

        int test = 0;
        int testPlus = 0;
        int testMinus = 0;
        int amount = 0;
        int amountPlus = 0;
        int amountMinus = 0;
        int allCorrect = 0;
        int moreThan50 = 0;
        int between50and75 = 0;

        while (out.hasNext()) {
            String line = out.nextLine().toLowerCase();
            if (line.contains("test")) {
                test++;
            } else if (line.contains(("correct"))) {
                String number = line.substring(line.length() - 2);
                if (number.charAt(0) == ' ') {
                    number = number.substring(1);
                } else {
                    allCorrect++;
                }
                amount = Integer.parseInt(number);
                if (moreThan50(amount)) {
                    moreThan50++;
                }
                if (between50and75(amount)) {
                    between50and75++;
                }
            } else if (line.contains("plus")) {
                testPlus++;
                amountPlus += amount;
            } else if (line.contains("minus")) {
                testMinus++;
                amountMinus += amount;
            }

        }

        System.out.println("Total tests done: " + test);
        System.out.println("Plus tests done: " + testPlus);
        System.out.println("% of correct answers in plus tests: " + (amountPlus / (10.0 * testPlus) * 100.0) + "%");
        System.out.println("Minus tests done: " + testMinus);
        System.out.println("% of correct answers in minus tests: " + (amountMinus / (10.0 * testMinus) * 100.0) + "%");
        System.out.println("Tests with all correct: " + allCorrect);
        System.out.println("Tests with more than 50% correct: " + moreThan50);
        System.out.println("Tests with more than 50% and less than 75% correct: " + between50and75);

    }

    public static boolean moreThan50(int check) {
        return check >= 5;
    }

    public static boolean between50and75(int check) {
        return check >= 5 && check < 8;
    }

    public static void checkPercent() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name you want to check percentages for: ");
        String name = scanner.nextLine().toLowerCase();
        Scanner out = new Scanner(new File(name + ".txt"));
        System.out.println("Enter the percentage you would like to check how many tests have more correct answers for.");
        int percentage = scanner.nextInt();
        int amount = 0;
        int test = 0;

        while(out.hasNext()) {
            String line = out.nextLine().toLowerCase();
            if (line.contains("correct")) {
                String number = line.substring(line.length() - 2);
                if (number.charAt(0) == ' ') {
                    number = number.substring(1);
                }
                amount = Integer.parseInt(number);
                if (amount * 10 >= percentage) {
                    test++;
                }
            }
        }
        System.out.println("There is " + test + " tests that has " + percentage + "% or more correct answers.");
    }
}
