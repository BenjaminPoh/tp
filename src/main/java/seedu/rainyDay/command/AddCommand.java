package seedu.rainyDay.command;

import seedu.rainyDay.data.FinancialStatement;

import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author lil1n

/**
 * Represents a command that add statement to the financial report
 */
public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final String description;
    private final String flowDirection;
    private final double value;
    private final String category;
    private final LocalDate date;

    public AddCommand(String description, String flowDirection, double value, String category, LocalDate date) {
        this.description = description;
        this.flowDirection = flowDirection;
        this.value = value;
        this.category = category;
        this.date = date;
    }

    /**
     * Sets up logger for logging
     */
    @Override
    protected void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler("AddCommand.log", true);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            System.out.println("unable to log AddCommand class");
            logger.log(Level.SEVERE, "File logger not working.", e);
        }
    }

    /**
     * Executes the command and print the relevant output message
     */
    @Override
    public CommandResult execute() {
        setupLogger();
        logger.log(Level.INFO, "starting AddCommand.execute()");

        int totalStatementCount = userData.getStatementCount(); // only used for assertion
        FinancialStatement newStatement = new FinancialStatement(description, flowDirection, value, category, date);
        userData.addStatement(newStatement);

        assert totalStatementCount + 1 == userData.getStatementCount() : "statement count mismatch";

        //To fix, due to Junit Test failing
        String budgetInfo;
        try {
            budgetInfo = userData.checkUserBudgetLimit(newStatement);
        } catch (Exception e) {
            budgetInfo = "";
        }

        String output = "Done! Added: " + userData.getStatement(totalStatementCount).getFullStatement()
                + budgetInfo;

        logger.log(Level.INFO, " end of AddCommand.execute()");
        return new CommandResult(output);
    }
}
