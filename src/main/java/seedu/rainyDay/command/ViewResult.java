package seedu.rainyDay.command;

import seedu.rainyDay.data.FinancialStatement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static seedu.rainyDay.RainyDay.userData;

//@@author BenjaminPoh
public class ViewResult {
    private static final String ACKNOWLEDGE_VIEW_COMMAND = "" +
            "|Here is your financial report!                                                                      |\n";
    private static final String ACKNOWLEDGE_FILTER_COMMAND = "" +
            "|Here is your filtered financial report!                                                             |\n";
    private static final String TABLE_FORMAT = "" +
            "+------+---------------------------------------------+--------------+---------------------+----------+\n" +
            "|Index |Description                                  |Amount        |Category             |Date      |\n";
    private static final String TABLE_BORDER = "" +
            "+------+---------------------------------------------+--------------+---------------------+----------+\n";
    private static final String TABLE_OUTSIDE_BORDER = "" +
            "+====================================================================================================+\n";
    private static final Logger logger = Logger.getLogger(ViewResult.class.getName());

    /**
     * Sets up logger for logging
     */
    protected void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler("ViewResult.log", true);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            System.out.println("unable to log ViewResult class");
            logger.log(Level.SEVERE, "File logger not working.", e);
        }
    }

    /**
     * Used to format the information shown to the user such that it fits the table.
     * The current table displays from left to right order:
     * The index, as a string of length 6
     * The description, as a string of length 45
     * The value, in 2dp, as a string of length 12, or "Ignored" if it is to be ignored.
     * The category, as a string of length 21
     * If category/description strings exceed their length, they will be truncated and appended with ... instead.
     * If value is ignored, "Ignored" will replace it.
     *
     * @param statementIndex   1-based indexing to be shown to the user
     * @param currentStatement the FinancialStatement
     * @return A formatted string
     */
    private static String formatFinancialStatement(int statementIndex, FinancialStatement currentStatement) {
        String statementOutput;
        String statementName = currentStatement.getDescription();
        double statementValue = currentStatement.getValue();
        String statementCategory = currentStatement.getCategory();
        String statementDirection = currentStatement.getFlowSymbol();
        String value;
        String description;
        String category;
        String date;

        String index = String.format("000000%d", statementIndex);
        index = index.substring(index.length() - 6);

        if (currentStatement.isIgnored()) {
            value = " Ignored      ";
        } else {
            value = String.format("%s$%.2f               ", statementDirection, statementValue);
            value = value.substring(0, 14);
        }

        if (statementName.length() > 45) {
            description = statementName.substring(0, 42) + "...";
        } else {
            description = String.format("%s                                              ", statementName);
            description = description.substring(0, 45);
        }

        if (statementCategory.length() > 21) {
            category = statementCategory.substring(0, 18) + "...";
        } else {
            category = String.format("%s                     ", statementCategory);
            category = category.substring(0, 21);
        }

        if (currentStatement.getDate() == null) {
            date = "no date   ";
        } else {
            date = currentStatement.getDate().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        }

        statementOutput = "|" + index + "|" + description + "|" + value + "|" + category + "|" + date + "|"
                + System.lineSeparator();
        return statementOutput;
    }

    /**
     * Used to format the summary of inflows and outflows
     *
     * @param inflow  the total inflow
     * @param outflow the total outflow
     * @return a string with the formatted summary
     */
    private static String formatSummary(double inflow, double outflow, LocalDate startDate,
                                        boolean isSorted, boolean viewAll) {
        assert (inflow != 0 || outflow != 0);
        String timespanInfo = "|Viewing all entries from " + startDate + " till today";
        if (viewAll) {
            timespanInfo = "|Viewing all entries from the start of time";
        }
        if (isSorted) {
            timespanInfo += " in sorted order";
        }
        String inflowInfo = String.format("|Total Inflow: $%.2f", inflow);
        String outflowInfo = String.format("|Total Outflow: $%.2f", outflow);
        String remainingValueInfo = String.format("|Remaining value: $%.2f", (inflow - outflow));
        timespanInfo = padSummaryLines(timespanInfo);
        inflowInfo = padSummaryLines(inflowInfo);
        outflowInfo = padSummaryLines(outflowInfo);
        remainingValueInfo = padSummaryLines(remainingValueInfo);

        return String.format("%s%s%s%s", timespanInfo, inflowInfo, outflowInfo, remainingValueInfo);
    }

    /**
     * Helper function used to pad lines in the summary such that the information fits in the table
     *
     * @param info The unformatted string
     * @return A formatted string
     */
    private static String padSummaryLines(String info) {
        info += "                                                                                                  ";
        info = info.substring(0, 101);
        info += "|\n";
        return info;
    }

    /**
     * Prints all statements specified by an ArrayList to the user.
     * Note that indices are 1-based in input, while it is 0-based in financialStatement
     *
     * @param indexArray ArrayList of Integers with the indices of the entries to print from financialReport
     */
    public static void printItemsInList(ArrayList<Integer> indexArray) {
        String output;

        System.out.print(TABLE_BORDER);
        System.out.print(ACKNOWLEDGE_FILTER_COMMAND);
        System.out.print(TABLE_FORMAT);

        for (int index : indexArray) {
            logger.log(Level.INFO, "starting statement " + index);
            FinancialStatement currentStatement = userData.getStatement(index - 1);
            output = formatFinancialStatement(index, currentStatement);

            System.out.print(output);
            logger.log(Level.INFO, "passed statement " + index);
        }

        System.out.print(TABLE_BORDER);
    }

    /**
     * Prints all statements specified by an ArrayList to the user.
     * Additional information, including the user's total inflow and outflow are also provided
     * Note that indices are 1-based in input, while it is 0-based in financialStatement
     *
     * @param validIndexes ArrayList of Integers with the indices of the entries to print from financialReport
     */
    public static void printReport(ArrayList<Integer> validIndexes, LocalDate startDate,
                                   boolean isSorted, boolean viewAll) {
        double totalInflow = 0;
        double totalOutflow = 0;

        System.out.print(TABLE_OUTSIDE_BORDER);
        System.out.print(ACKNOWLEDGE_VIEW_COMMAND);
        System.out.print(TABLE_FORMAT);
        for (Integer index : validIndexes) {
            logger.log(Level.INFO, "starting statement " + index);
            FinancialStatement currentStatement = userData.getStatement(index);
            if (currentStatement.getFlowDirectionWord().equals("in") && !currentStatement.isIgnored()) {
                totalInflow += currentStatement.getValue();
            } else if (currentStatement.getFlowDirectionWord().equals("out") && !currentStatement.isIgnored()) {
                totalOutflow += currentStatement.getValue();
            }
            String formattedOutput = formatFinancialStatement(index + 1, currentStatement);
            System.out.print(formattedOutput);
            logger.log(Level.INFO, "passed statement " + index);
        }
        System.out.print(TABLE_BORDER);
        System.out.print(formatSummary(totalInflow, totalOutflow, startDate, isSorted, viewAll));
        System.out.print(TABLE_OUTSIDE_BORDER);
    }
}

