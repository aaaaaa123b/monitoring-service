package by.harlap.monitoring.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The ConsoleDecorator class provides methods for interacting with the console, including reading input and printing messages.
 */
public class ConsoleDecorator {

    private final Scanner scanner;

    public ConsoleDecorator() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a non-empty line of text from the console.
     *
     * @return the non-empty line of text
     */
    public String readLine() {
        String line = scanner.nextLine();

        while (line.isBlank()) {
            line = scanner.nextLine();
        }

        return line;
    }

    /**
     * Reads an integer from the console, handling input mismatch exceptions.
     *
     * @return the read integer
     */
    public int readInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException ignored) {
                scanner.nextLine();
            }
        }
    }

    /**
     * Reads a double from the console, handling input mismatch exceptions.
     *
     * @return the read double
     */
    public double readDouble() {
        double result;
        while (true) {
            try {
                result = scanner.nextDouble();
                if (result > 0) {
                    return result;
                } else {
                    print("Введите положительное значение");
                }
            } catch (InputMismatchException ignored) {
                scanner.nextLine();
            }
        }
    }

    /**
     * Prints the specified message to the console followed by a newline.
     *
     * @param message the message to be printed
     */
    public void print(String message) {
        System.out.println(message);
    }
}
