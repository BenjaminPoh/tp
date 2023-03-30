package seedu.rainyDay.data;

import java.time.LocalDate;
import java.util.HashMap;

public class UserData {
    private FinancialReport financialReport;
    private HashMap<String, String> shortcutCommands;
    private double budgetGoal;

    public UserData(FinancialReport financialReport) {
        this.financialReport = financialReport;
        shortcutCommands = new HashMap<>();
        this.budgetGoal = 0;
    }

    public FinancialReport getFinancialReport() {
        return financialReport;
    }

    public HashMap<String, String> getShortcutCommands() {
        return shortcutCommands;
    }

    public void setBudgetGoal(double goal) {
        this.budgetGoal = goal;
    }

    public double getBudgetGoal() {
        return this.budgetGoal;
    }

    public int getStatementCount() {
        return financialReport.getStatementCount();
    }

    /**
     * Helper command used to check if the user's expenditure for the month has exceeded his set
     * budget
     * The progress is show if and only if a budget is present, and the changed entry is in the same
     * month and year as the user. Otherwise, an empty string is returned
     *
     * @param statementMonthYear an integer denoting the MonthYear of the changed entry
     * @return A string denoting the progress if applicable
     */
    public String checkUserBudgetLimit(int statementMonthYear) {
        double budgetLimit = getBudgetGoal();
        double currentSpending = financialReport.getMonthlyExpenditure(statementMonthYear);
        int currentMonthYear = LocalDate.now().getMonthValue() + LocalDate.now().getYear() * 12;
        if (statementMonthYear != currentMonthYear) {
            return "";
        }
        if (budgetLimit == 0) {
            return "";
        }
        if (currentSpending >= budgetLimit) {
            return String.format("\nYou've spent $%.2f/$%.2f and exceeded your budget! " +
                    "Try harder next time :(",currentSpending, budgetLimit);
        }
        return String.format("\nYou have spent $%.2f/$%.2f, Keep it up!", currentSpending, budgetLimit);
    }

    public FinancialStatement getStatement(int statementIndex) {
        return financialReport.getFinancialStatement(statementIndex);
    }

    public void deleteStatement(int statementIndex) {
        financialReport.deleteStatement(statementIndex);
    }

    public void addStatementAtIndex(FinancialStatement newStatement, int index) {
        financialReport.addStatementAtIndex(newStatement, index);
    }

    public void removeFromMonthlyExpenditure(FinancialStatement statement) {
        financialReport.removeFromMonthlyExpenditure(statement);
    }

    public void addToMonthlyExpenditure(FinancialStatement statement) {
        financialReport.addToMonthlyExpenditure(statement);
    }

    public LocalDate getStatementDate(int statementIndex) {
        return financialReport.getStatementDate(statementIndex);
    }

    public void addStatement(FinancialStatement statement) {
        financialReport.addStatement(statement);
    }

    public String getReportOwner() {
        return financialReport.getReportOwner();
    }
}
