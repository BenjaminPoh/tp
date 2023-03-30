package seedu.rainyDay.modules;

import seedu.rainyDay.RainyDay;

import seedu.rainyDay.command.Command;
import seedu.rainyDay.command.AddCommand;
import seedu.rainyDay.command.DeleteCommand;
import seedu.rainyDay.command.ShortcutDeleteCommand;
import seedu.rainyDay.command.EditCommand;
import seedu.rainyDay.command.ExportCommand;
import seedu.rainyDay.command.ShortcutAddCommand;
import seedu.rainyDay.command.ViewCommand;
import seedu.rainyDay.command.HelpCommand;
import seedu.rainyDay.command.FilterCommand;
import seedu.rainyDay.command.InvalidCommand;
import seedu.rainyDay.command.ShortcutViewCommand;
import seedu.rainyDay.command.SetBudgetCommand;
import seedu.rainyDay.command.IgnoreCommand;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author azriellee
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());
    private String direction;
    private String description;
    private String category;
    private double amount = -1.0;
    private LocalDate date;

    public Command parseUserInput(String userInput) throws RainyDayException {
        try {
            assert userInput != null : "Failed to read user input!";
            String[] action = userInput.split("\\s+", 2);
            if (action[0].equalsIgnoreCase(Command.COMMAND_ADD)) {
                logger.info("add command executing");
                return addStatement(action[1].trim());
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_DELETE)) {
                logger.info("delete command executing");
                return parseDeleteStatement(userInput);
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_VIEW)) {
                logger.info("view command executing");
                return generateReport(userInput);
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_HELP)) {
                logger.info("help command executing");
                return displayHelp(userInput.trim());
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_FILTER)) {
                logger.info("filter command executing");
                return filterStatement(action[1]);
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_EDIT)) {
                logger.info("edit command executing");
                return editStatement(userInput);
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_EXPORT)) {
                logger.info("export command executing");
                return new ExportCommand();
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_SHORTCUT)) {
                logger.info("shortcut command executing");
                return generateShortcut(action[1].trim());
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_SET_BUDGET)) {
                logger.info("set budget command executing");
                return setUserBudgetGoal(action[1].trim());
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_DELETE_SHORTCUT)) {
                logger.info("delete_shortcut command executing");
                return new ShortcutDeleteCommand(action[1].trim());
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_VIEW_SHORTCUT)) {
                logger.info("view_shortcut command executing");
                return new ShortcutViewCommand();
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_IGNORE)) {
                logger.info("ignore command executing");
                return ignoreStatement(userInput);
            } else if (action[0].equalsIgnoreCase(Command.COMMAND_UNIGNORE)) {
                logger.info("ignore command executing");
                return ignoreStatement(userInput);
            } else {
                // check if the user has a shortcut command
                HashMap<String, String> shortcutCommands = RainyDay.userData.getShortcutCommands();
                if (shortcutCommands.containsKey(userInput)) {
                    return parseUserInput(shortcutCommands.get(userInput));
                }
                logger.warning("unrecognised input from user!");
                return new InvalidCommand(ErrorMessage.UNRECOGNIZED_INPUT.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            logger.warning("filter or add command missing details");
            throw new RainyDayException(ErrorMessage.MISSING_DETAILS.toString());
        }
    }

    private Command addStatement(String addInput) throws RainyDayException { // example: add -<in/out> <description>
        // $value -c -date
        try {
            this.category = "miscellaneous";
            this.date = LocalDate.now();
            String remainingInformation = returnRemainingInformation(addInput);
            logger.info("obtained mandatory information");
            if (remainingInformation.trim().isEmpty()) {
                logger.info("returning new AddCommand object");
                return new AddCommand(description.trim(), direction, amount, category, date);
            }
            if (!addInput.contains("-c ") && !addInput.contains("-date ")) {
                logger.info("returning new InvalidCommand object");
                throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
            }
            logger.info("checking for presence of -c");
            if (addInput.contains("-c ")) {
                remainingInformation = setCategory(remainingInformation);
            }
            logger.info("checking for presence of -date");
            if (addInput.contains("-date ")) {
                setDate(remainingInformation);
            }
            logger.info("returning new AddCommand object");
            return new AddCommand(description.trim(), direction, amount, category, date);
        } catch (Exception e) {
            logger.warning("add command given by user in the wrong format");
            throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
        }
    }

    private String returnRemainingInformation(String input) throws RainyDayException {
        try {
            Pattern pattern = Pattern.compile("-(in|out)\\s+(.+)\\$([\\d.]+)");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                this.direction = matcher.group(1);
                this.description = matcher.group(2);
                double exactAmount = Double.parseDouble(matcher.group(3));
                exactAmount = (int) (exactAmount * 100);
                if (exactAmount == 0) {
                    throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
                }
                this.amount = exactAmount / 100;
                logger.info("obtaining mandatory information");
                return "";
            }
            pattern = Pattern.compile("-(in|out)\\s+(.+)\\$([\\d.]+)\\s+(.*)");
            matcher = pattern.matcher(input);
            if (matcher.matches()) {
                this.direction = matcher.group(1);
                this.description = matcher.group(2);
                double exactAmount = Double.parseDouble(matcher.group(3));
                exactAmount = (int) (exactAmount * 100);
                if (exactAmount == 0) {
                    throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
                }
                this.amount = exactAmount / 100;
                logger.info("obtaining mandatory information");
                return matcher.group(4);
            }
        } catch (Exception e) {
            logger.warning("add command given by user in the wrong format");
            throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
        }
        logger.warning("add command given by user in the wrong format");
        throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
    }

    private String setCategory(String input) throws RainyDayException {
        Pattern newPattern = Pattern.compile("-c\\s+(.+)\\s+-date\\s+(\\d{2}/\\d{2}/\\d{4})");
        Matcher newMatcher = newPattern.matcher(input);
        if (newMatcher.matches()) {
            this.category = newMatcher.group(1);
            logger.info("obtaining category");
            return "-date " + newMatcher.group(2);
        }

        Pattern pattern = Pattern.compile("-c\\s+(.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            this.category = matcher.group(1);
            logger.info("obtaining category");
            return "";
        }
        logger.warning("add command given by user in the wrong format");
        throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
    }

    private void setDate(String input) throws RainyDayException {
        Pattern pattern = Pattern.compile("-date\\s+(\\d{2}/\\d{2}/\\d{4})");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            try {
                this.date = LocalDate.parse(matcher.group(1).trim(), DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                logger.info("obtaining date");
            } catch (DateTimeParseException e) {
                logger.warning("add command given by user in the wrong format");
                throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
            }
            if (this.date.getYear() < 1800 || this.date.isAfter(LocalDate.now())) {
                throw new RainyDayException(ErrorMessage.UNSUPPORTED_DATE.toString());
            }
        } else {
            logger.warning("add command given by user in the wrong format");
            throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
        }
    }

    public Command parseDeleteStatement(String userInput) throws RainyDayException {
        String[] tokens = userInput.split("\\s+");
        if (tokens.length != 2) {
            logger.warning("invalid delete index from user");
            throw new RainyDayException(ErrorMessage.NO_DELETE_INDEX.toString());
        }
        try {
            int index = Integer.parseInt(tokens[1]);
            if (index > RainyDay.userData.getFinancialReport().getStatementCount()) {
                throw new IllegalArgumentException();
            }
            if (index <= 0) {
                throw new IllegalArgumentException();
            }
            return new DeleteCommand(index);
        } catch (Exception e) {
            logger.warning("delete index provided incorrectly");
            throw new RainyDayException(String.format(ErrorMessage.WRONG_DELETE_INDEX.toString(),
                    RainyDay.userData.getFinancialReport().getStatementCount() + "!"));
        }
    }

    //@@author BenjaminPoh
    public Command generateReport(String input) {
        input = input.substring(4).trim();
        LocalDate startDate = LocalDate.now();
        if (input.equals("")) {
            startDate = startDate.minusMonths(1);
            return new ViewCommand(startDate, false);
        }
        if (input.equals("-sort")) {
            startDate = startDate.minusMonths(1);
            return new ViewCommand(startDate, true);
        }
        Pattern pattern = Pattern.compile("^(-all)?\\s*((-sort)?)\\s*$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            try {
                System.out.println(matcher.group(1));
                System.out.println(matcher.group(2));
                boolean printEverything = matcher.group(1).equals("-all");
                boolean sortRequired = matcher.group(2).equals("-sort");
                if (printEverything) {
                    startDate = LocalDate.of(1800, 1, 1);
                    return new ViewCommand(startDate, sortRequired);
                }
                startDate = startDate.minusMonths(1);
                return new ViewCommand(startDate, sortRequired);
            } catch (Exception e) {
                logger.warning("view command given by user in the wrong format");
                return new InvalidCommand(ErrorMessage.WRONG_VIEW_FORMAT.toString());
            }
        }

        pattern = Pattern.compile("^(\\d{1,2})([dwmy])\\s*((-sort)?)\\s*$");
        matcher = pattern.matcher(input);
        if (matcher.matches()) {
            try {
                logger.info("obtaining relevant data");
                int minusAmount = Integer.parseInt(matcher.group(1));
                String dateType = matcher.group(2);
                boolean sortRequired = matcher.group(3).equals("-sort");
                if (dateType.equals("d") && minusAmount < 32) {
                    startDate = startDate.minusDays(minusAmount);
                    return new ViewCommand(startDate, sortRequired);
                }
                if (dateType.equals("w") && minusAmount < 5) {
                    startDate = startDate.minusWeeks(minusAmount);
                    return new ViewCommand(startDate, sortRequired);
                }
                if (dateType.equals("m") && minusAmount < 13) {
                    startDate = startDate.minusMonths(minusAmount);
                    return new ViewCommand(startDate, sortRequired);
                }
                if (dateType.equals("y") && minusAmount < 11) {
                    startDate = startDate.minusYears(minusAmount);
                    return new ViewCommand(startDate, sortRequired);
                }
                logger.warning("view command given by user in the wrong format");
                return new InvalidCommand(ErrorMessage.WRONG_VIEW_FORMAT.toString());
            } catch (Exception e) {
                logger.warning("view command given by user in the wrong format");
                return new InvalidCommand(ErrorMessage.WRONG_VIEW_FORMAT.toString());
            }
        } else {
            logger.warning("view command given by user in the wrong format");
            return new InvalidCommand(ErrorMessage.WRONG_VIEW_FORMAT.toString());
        }
    }

    //@@author BenjaminPoh
    public HelpCommand displayHelp(String input) {
        input = input.substring(4);
        return new HelpCommand(input.trim());
    }

    //@@author ChongQiRong
    private Command filterStatement(String input) {
        try {
            int sizeOfFilterFlagAndField = 0;
            String[] flagAndField = input.split("\\s");
            for (String s : flagAndField) {
                if (s.contains("-in")) {
                    sizeOfFilterFlagAndField += 1;
                } else if (s.contains("-out")) {
                    sizeOfFilterFlagAndField += 1;
                } else if (s.contains("-d")) {
                    sizeOfFilterFlagAndField += 2;
                } else if (s.contains("-c")) {
                    sizeOfFilterFlagAndField += 2;
                } else if (s.contains("-date")) {
                    sizeOfFilterFlagAndField += 2;
                }
            }

            if (input.startsWith("-in") | input.startsWith("-out") | input.startsWith("-d")
                    | input.startsWith("-c") | input.startsWith("-date")) {
                return new FilterCommand(parseFilterMultipleFlags(input, sizeOfFilterFlagAndField));
            } else {
                logger.warning("unrecognised input from user!");
                return new InvalidCommand(ErrorMessage.WRONG_FILTER_FORMAT.toString());
            }
        } catch (Exception e) {
            logger.warning("filter command given by user in the wrong format");
            return new InvalidCommand(ErrorMessage.WRONG_FILTER_FORMAT.toString());
        }
    }

    private ArrayList<String> parseFilterMultipleFlags(String input, int sizeOfFilterFlagAndField) {
        Pattern pattern = Pattern.compile("(?:(-in|-out)\\s*)?\\s*" +
                "(?:(-d)\\s+([^\\s-]+(?:\\s+[^\\s-]+)*)\\s*)?" +
                "(?:(-c)\\s+([^\\s-]+(?:\\s+[^\\s-]+)*)\\s*)?" +
                "(?:(-date)\\s+(\\d{2}/\\d{2}/\\d{4})\\s*)?([\\w]*)$");
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> filterFlagAndField = new ArrayList<>();

        if (matcher.find()) {
            for (int i = 1; i <= 8; i += 1) {
                if (!matcher.group(8).equals("")) {
                    logger.warning("edit command given by user in the wrong format");
                    throw new IllegalArgumentException(ErrorMessage.WRONG_EDIT_FORMAT.toString());
                }
                if (matcher.group(i) == null || i == 8) {
                    continue;
                }
                filterFlagAndField.add(matcher.group(i));
            }

            if (filterFlagAndField.size() != sizeOfFilterFlagAndField) {
                logger.warning("filter command given by user in the wrong format");
                throw new IllegalArgumentException(ErrorMessage.WRONG_FILTER_FORMAT.toString());
            }
            return filterFlagAndField;
        } else {
            logger.warning("filter command given by user in the wrong format");
            throw new IllegalArgumentException(ErrorMessage.WRONG_FILTER_FORMAT.toString());
        }
    }

    public Command editStatement(String userInput) throws IllegalArgumentException {
        try {
            String[] tokens = userInput.split("\\s+", 3);
            if (tokens.length < 3) {
                logger.warning("invalid edit index from user");
                throw new IllegalArgumentException();
            }

            int index = Integer.parseInt(tokens[1]);
            if (index > RainyDay.userData.getFinancialReport().getStatementCount()) {
                throw new IllegalArgumentException();
            }

            int sizeOfEditFlagAndField = 0;
            String[] flagAndField = tokens[2].split("\\s");
            for (String s : flagAndField) {
                if (s.equals("-in")) {
                    sizeOfEditFlagAndField += 1;
                } else if (s.equals("-out")) {
                    sizeOfEditFlagAndField += 1;
                } else if (s.equals("-d")) {
                    sizeOfEditFlagAndField += 2;
                } else if (s.equals("-v")) {
                    sizeOfEditFlagAndField += 2;
                } else if (s.equals("-c")) {
                    sizeOfEditFlagAndField += 2;
                } else if (s.equals("-date")) {
                    sizeOfEditFlagAndField += 2;
                }
            }

            if (tokens[2].startsWith("-in") | tokens[2].startsWith("-out") | tokens[2].startsWith("-d")
                    | tokens[2].startsWith("-c") | tokens[2].startsWith("-v") | tokens[2].startsWith("-date")) {
                ArrayList<String> editFlagAndField = parseEditMultipleFlags(tokens[2], sizeOfEditFlagAndField);
                if (editFlagAndField.contains("-date")) {
                    LocalDate.parse(editFlagAndField.get(editFlagAndField.size() - 1),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
                return new EditCommand(index, editFlagAndField);
            } else {
                logger.warning("unrecognised input from user!");
                return new InvalidCommand(ErrorMessage.WRONG_EDIT_FORMAT.toString());
            }
        } catch (Exception e) {
            logger.warning("edit index provided incorrectly");
            return new InvalidCommand(ErrorMessage.WRONG_EDIT_FORMAT.toString());
        }
    }

    private ArrayList<String> parseEditMultipleFlags(String input, int sizeOfEditFlagAndField) {
        Pattern pattern = Pattern.compile("(?:(-in|-out)\\s*)?\\s*" +
                "(?:(-d)\\s+([^\\s-]+(?:\\s+[^\\s-]+)*)\\s*)?" +
                "(?:(-v)\\s+\\$([\\d]+(?:\\.?[\\d]){0,2})\\s*)?" +
                "(?:(-c)\\s+([^\\s-]+(?:\\s+[^\\s-]+)*)\\s*)?" +
                "(?:(-date)\\s+(\\d{2}/\\d{2}/\\d{4})\\s*)?([\\w]*)$");
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> editFlagAndField = new ArrayList<>();

        if (matcher.find()) {
            for (int i = 1; i <= 10; i += 1) {
                if (!matcher.group(10).equals("")) {
                    logger.warning("edit command given by user in the wrong format");
                    throw new IllegalArgumentException(ErrorMessage.WRONG_EDIT_FORMAT.toString());
                }
                if (matcher.group(i) == null || i == 10) {
                    continue;
                }
                editFlagAndField.add(matcher.group(i));
            }

            if (editFlagAndField.size() == 0 || editFlagAndField.size() != sizeOfEditFlagAndField) {
                logger.warning("edit command given by user in the wrong format");
                throw new IllegalArgumentException(ErrorMessage.WRONG_EDIT_FORMAT.toString());
            }
            return editFlagAndField;
        } else {
            logger.warning("edit command given by user in the wrong format");
            throw new IllegalArgumentException(ErrorMessage.WRONG_EDIT_FORMAT.toString());
        }

    }

    //@@author KN-CY
    private Command generateShortcut(String userInput) {
        if (!userInput.contains(" -maps ")) {
            return new InvalidCommand(ErrorMessage.WRONG_SHORTCUT_FORMAT.toString());
        }

        String[] tokens = userInput.split(" -maps ");

        // check for > 1 instance of " -maps "
        if (tokens.length > 2) { //
            return new InvalidCommand(ErrorMessage.WRONG_SHORTCUT_FORMAT.toString());
        }
        String key = tokens[0];
        String value = tokens[1];

        // ensure that shortcut is a single word
        if (key.contains(" ")) {
            return new InvalidCommand(ErrorMessage.WRONG_SHORTCUT_FORMAT.toString());
        }
        return new ShortcutAddCommand(key, value);
    }


    //@@author BenjaminPoh
    private Command setUserBudgetGoal(String userInput) {
        try {
            double amount = Double.parseDouble(userInput);
            amount = (int) (amount * 100);
            amount /= 100;
            if (amount < 0) {
                logger.warning("set budget details provided incorrectly");
                return new InvalidCommand(ErrorMessage.WRONG_SET_BUDGET_FORMAT.toString());
            }
            return new SetBudgetCommand(amount);
        } catch (Exception e) {
            logger.warning("set budget details provided incorrectly");
            return new InvalidCommand(ErrorMessage.WRONG_SET_BUDGET_FORMAT.toString());
        }
    }

    public Command ignoreStatement(String userInput) throws RainyDayException {
        String[] tokens = userInput.split("\\s+", 2);
        if (tokens.length < 2) {
            logger.warning("no ignore index from user");
            throw new RainyDayException(ErrorMessage.WRONG_IGNORE_FORMAT.toString());
        }

        int index = Integer.parseInt(tokens[1]);
        if (index > RainyDay.userData.getFinancialReport().getStatementCount()) {
            throw new RainyDayException(String.format(ErrorMessage.WRONG_IGNORE_INDEX.toString(),
                    RainyDay.userData.getFinancialReport().getStatementCount() + "!"));
        }
        if (index <= 0) {
            throw new RainyDayException(String.format(ErrorMessage.WRONG_IGNORE_INDEX.toString(),
                    RainyDay.userData.getFinancialReport().getStatementCount() + "!"));
        }
        return new IgnoreCommand(index, tokens[0]);
    }
}
