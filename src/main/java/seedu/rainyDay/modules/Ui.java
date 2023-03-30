package seedu.rainyDay.modules;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author BenjaminPoh
public class Ui {
    private static final String WELCOME_MESSAGE = "" +
            "Hello from rainyDay!\n" +
            "        __.|.__\n" +
            "    .-\"'..':`..`\"-.\n" +
            "  .' .' .  :  . `. `.\n" +
            " / .   .   :   .   . \\\n" +
            "/_ _._ _.._:_.._ _._ _\\\n" +
            "  '   '    |    '   '\n" +
            "           |\n" +
            "           |\n" +
            "           |\n" +
            "         `='";
    private static final String NO_FILE_DETECTED = "No valid save file detected. Starting with empty financial data.";
    private static final String FINANCIAL_REPORT_EMPTY = "Your financial report is empty";
    private static Logger UILogger = Logger.getLogger("UILogger.log");

    private final Scanner in;

    //@@author lil1n
    public Ui() {
        this(System.in);
    }

    public Ui(InputStream in) {
        this.in = new Scanner(in);
    }

    //@@author BenjaminPoh
    public void printLogo() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void greetUser(String username) {
        System.out.println("Welcome " + username);
    }

    public void noFileExist() {
        System.out.println(NO_FILE_DETECTED);
    }

    public static void emptyFinancialReport() {
        System.out.println(FINANCIAL_REPORT_EMPTY);
    }

    public String readUserName() {
        System.out.println("Welcome new user! What is your name?");
        String username;
        while(true) {
            username = in.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Very funny, you should not have an empty name!\nWhat is your name?");
            } else {
                break;
            }
        }
        return username;
    }

    public String readUserCommand() {
        System.out.print("> ");
        String userInput = in.nextLine();
        UILogger.log(Level.INFO, userInput);
        return userInput.trim();
    }

    public void sayFarewellToUser(String username) {
        System.out.println("Bye " + username);
    }
}
