package seedu.rainyDay.data;

import seedu.rainyDay.RainyDay;
import seedu.rainyDay.modules.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class FinancialReport {
    private final ArrayList<FinancialStatement> financialReport;
    private final HashMap<Integer, Double> monthlyExpenditures;
    private String reportOwner;

    public FinancialReport(ArrayList<FinancialStatement> financialReport, HashMap<Integer, Double> monthlyOutflow) {
        this.financialReport = financialReport;
        this.monthlyExpenditures = monthlyOutflow;
    }

    public String getReportOwner() {
        if (this.reportOwner == null) {
            return "no name";
        }
        return reportOwner;
    }

    public void setReportOwner(String name) {
        this.reportOwner = name;
    }

    public int getStatementCount() {
        return financialReport.size();
    }

    public void addStatement(FinancialStatement statement) {
        financialReport.add(statement);
        addToMonthlyExpenditure(statement);
        Storage.writeToFile(RainyDay.userData, RainyDay.filePath);
    }

    public void addStatementAtIndex(FinancialStatement statement, int index) {
        financialReport.add(index, statement);
        addToMonthlyExpenditure(statement);
    }

    public void deleteStatement(int statementNumber) {
        removeFromMonthlyExpenditure(financialReport.get(statementNumber));
        financialReport.remove(statementNumber);
    }

    public String getFullStatement(int statementNumber) {
        return financialReport.get(statementNumber).getStatementForList();
    }

    public double getMonthlyExpenditure(int monthAndYear) {
        if (!monthlyExpenditures.containsKey(monthAndYear)) {
            return 0;
        }
        return monthlyExpenditures.get(monthAndYear);
    }

    public void clearReport() {
        financialReport.clear();
    }

    public FinancialStatement getFinancialStatement(int statementNumber) {
        return financialReport.get(statementNumber);
    }

    public ArrayList<FinancialStatement> getFinancialReport() {
        return this.financialReport;
    }

    public LocalDate getStatementDate(int statementNumber) {
        return financialReport.get(statementNumber).getDate();
    }

    public void addToMonthlyExpenditure(FinancialStatement statement) {
        int monthAndYear = statement.getMonthAndYear();
        if (!monthlyExpenditures.containsKey(monthAndYear)) {
            monthlyExpenditures.put(monthAndYear, 0.0);
        }
        double currentExpenditure = monthlyExpenditures.get(monthAndYear);
        if (statement.getFlowDirectionWord().equals("out")) {
            currentExpenditure += statement.getValue();
            monthlyExpenditures.put(monthAndYear, currentExpenditure);
        }
    }

    public void removeFromMonthlyExpenditure(FinancialStatement statement) {
        int monthAndYear = statement.getMonthAndYear();
        double currentExpenditure = monthlyExpenditures.get(monthAndYear);
        if (statement.getFlowDirectionWord().equals("out")) {
            currentExpenditure -= statement.getValue();
            monthlyExpenditures.put(monthAndYear, currentExpenditure);
        }
    }
}
