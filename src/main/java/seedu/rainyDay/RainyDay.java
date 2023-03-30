package seedu.rainyDay;

import com.google.gson.JsonParseException;
import seedu.rainyDay.command.CommandResult;
import seedu.rainyDay.data.UserData;
import seedu.rainyDay.modules.Storage;
import seedu.rainyDay.modules.Ui;
import seedu.rainyDay.command.Command;
import seedu.rainyDay.data.FinancialReport;
import seedu.rainyDay.modules.Parser;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class RainyDay {
    public static String filePath = "./data/rainyDay.json";
    public static UserData userData;

    private static Logger logger = Logger.getLogger(RainyDay.class.getName());

    private final Ui ui;

    private RainyDay(String filePath) {
        ui = new Ui();
        try {
            userData = Storage.loadFromFile(filePath);
            assert userData != null : "Error loading from json file";
            logger.log(Level.INFO, "File loaded successfully.");
        } catch (FileNotFoundException | JsonParseException e) {
            logger.log(Level.INFO, "No valid save file detected. Starting with empty financial data.");
            ui.noFileExist();
            String username = ui.readUserName();
            assert username != null : "Inputted username should not be null";
            FinancialReport financialReport = new FinancialReport(new ArrayList<>(), new HashMap<>());
            userData = new UserData(financialReport);
            financialReport.setReportOwner(username);
        }
    }

    private void run() {
        showStartingMessage();
        runCommand();
        ui.sayFarewellToUser(userData.getReportOwner());
    }

    private void showStartingMessage() {
        ui.printLogo();
        ui.greetUser(userData.getReportOwner());
        int currentMonthYear = LocalDate.now().getMonthValue() + LocalDate.now().getYear() * 12;
        System.out.println(userData.checkUserBudgetLimit(currentMonthYear));
        Storage.writeToFile(RainyDay.userData, RainyDay.filePath);
    }

    private void runCommand() {
        Command specificCommand;
        while (true) {
            try {
                String userInput = ui.readUserCommand();
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                }
                specificCommand = new Parser().parseUserInput(userInput);
                assert specificCommand != null : "Parser returned null";
                executeCommand(specificCommand);
            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    private void executeCommand(Command command) {
        command.setData(userData);
        CommandResult result = command.execute();
        if (result != null) {
            result.printResult();
        }
        Storage.writeToFile(userData, filePath);
    }

    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler("RainyDay.log");
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "File logger not working.", e);
        }
    }

    public static void main(String[] args) {
        setupLogger();
        logger.log(Level.INFO, "Starting RainyDay");
        new RainyDay(filePath).run();
        logger.log(Level.INFO, "Quitting RainyDay");
    }
}
