package seedu.rainyDay.command;

import seedu.rainyDay.data.FinancialStatement;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final String description;

    private final String flowDirection;

    private final double value;

    private final String category;

    public AddCommand(String description, String flowDirection, double value, String category) {
        this.description = description;
        this.flowDirection = flowDirection;
        this.value = value;
        this.category = category;
    }

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

    @Override
    public CommandResult execute() {
        setupLogger();
        logger.log(Level.INFO, "starting AddCommand.execute()");

        int totalStatementCount = financialReport.getStatementCount();

        financialReport.addStatement(new FinancialStatement(description, flowDirection, value, category));

        assert totalStatementCount + 1 == financialReport.getStatementCount() : "statement count mismatch";

        String output = "Done! Added: " + financialReport.getFullStatement(totalStatementCount);

        logger.log(Level.INFO, " end of AddCommand.execute()");

        return new CommandResult(output);
    }
}
