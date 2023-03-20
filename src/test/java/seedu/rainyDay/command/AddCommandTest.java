package seedu.rainyDay.command;

import org.junit.jupiter.api.Test;
import seedu.rainyDay.data.FinancialReport;
import seedu.rainyDay.data.FinancialStatement;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandTest {
    ArrayList<FinancialStatement> statements = new ArrayList<>();
    FinancialReport financialReport = new FinancialReport(statements);

    @Test
    public void execute_singleStatement_statementInformation() {
        AddCommand addCommand = new AddCommand("Ipad", "out", 120.50, "Default");
        addCommand.setData(financialReport);
        addCommand.execute();
        FinancialStatement expectedStatement =
                new FinancialStatement("Ipad", "out", 120.5, "Default");
        assertEquals(expectedStatement.getFullStatement(),
                financialReport.getFinancialStatement(0).getFullStatement());
    }

    @Test
    public void execute_multipleStatements_statementsInformation() {
        AddCommand addCommand = new AddCommand("angpao", "in", 3000.00, "Default");
        addCommand.setData(financialReport);
        addCommand.execute();
        FinancialStatement expectedStatement =
                new FinancialStatement("angpao", "in", 3000.00, "Default");
        assertEquals(expectedStatement.getFullStatement(),
                financialReport.getFinancialStatement(0).getFullStatement());
        addCommand = new AddCommand("textbook", "out", 50.00, "Default");
        addCommand.setData(financialReport);
        addCommand.execute();
        assertEquals(2, financialReport.getStatementCount());
    }
}
