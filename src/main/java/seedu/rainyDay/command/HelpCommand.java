package seedu.rainyDay.command;

//@@author BenjaminPoh
public class HelpCommand extends Command {
    private static final String HELP_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Welcome to RainyDay! Here is the overview of commands available                                    |\n" +
            "+====================================================================================================+\n" +
            "| Feature            | Command   | Additional Syntax                                                 |\n" +
            "+--------------------+-----------+-------------------------------------------------------------------+\n" +
            "| Add entry          | add       | [-in/-out] [DESCRIPTION] $[VALUE] {-c CATEGORY} {-date DATE}      |\n" +
            "| Delete entry       | delete    | [INDEX]                                                           |\n" +
            "| View entries       | view      | {TIME} {-sort}                                                    |\n" +
            "| Filter entries     | filter    | {-in} {-out} {-d DESCRIPTION} {-c CATEGORY} {-date DATE}          |\n" +
            "| Edit an entry      | edit      | [INDEX] [-in/-out/-d DESCRIPTION/-v $VALUE/-c CATEGORY/-date DATE]|\n" +
            "| Set monthly budget | setbudget | [VALUE]                                                           |\n"+
            "| Create shortcuts   | shortcut  | [SHORTCUT COMMAND -maps VALID COMMAND]                            |\n" +
            "| Ignore entry       | ignore    | [INDEX]                                                           |\n" +
            "| Unignore entry     | unignore  | [INDEX]                                                           |\n" +
            "| Export to .csv     | export    |                                                                   |\n" +
            "| Display a guide    | help      | {COMMAND}                                                         |\n" +
            "+====================================================================================================+\n" +
            "|[] :  Denotes compulsory fields. Relevant details / flags must be included.                         |\n" +
            "|{} :  Denotes optional fields. Can be ignored                                                       |\n" +
            "|For more information on any command: type   < help {command} >   as shown above (e.g. help add)     |\n" +
            "+====================================================================================================+\n";

    private static final String HELP_ADD_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Add command                 | Used to add a new entry to RainyDay                                  |\n" +
            "+====================================================================================================+\n" +
            "| Details       | Requirement | Description                                                          |\n" +
            "+---------------+-------------+----------------------------------------------------------------------+\n" +
            "| -in / -out    | Mandatory   | Used to denote if the entry is an inflow or an outflow               |\n" +
            "| <DESCRIPTION> | Optional    | Used to describe the entry. Description will be left blank if omitted|\n" +
            "| $<VALUE>      | Mandatory   | Used to set the value of the entry.                                  |\n" +
            "| -c <CATEGORY> | Optional    | Used to denote the category of the entry. Set to Misc if omitted     |\n" +
            "| -date <DATE>  | Optional    | Used to denote the date of the entry. Set to present date if omitted |\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| add -in Salary $2113.09     | Adds entry with description: Salary and value: $2113.09 as inflow    |\n" +
            "|                             |                                                                      |\n" +
            "| add -out Noodles $5         | Adds entry with description: Noodles and value: $5 as outflow        |\n" +
            "|                             |                                                                      |\n" +
            "| add -out Bus $0.98 -c       | Adds entry with description: Bus and value: $0.98 as outflow,        |\n" +
            "| Transport                   | with its category set to Transport                                   |\n" +
            "|                             |                                                                      |\n" +
            "| add -out Bubble Tea $5.20   | Adds entry with description: Bubble Tea and value: $5.20 as outflow, |\n" +
            "| -c Food -date 22/03/2018    | with its category set to Food and date set to 22nd March, 2018       |\n" +
            "|                             |                                                                      |\n" +
            "| add -out $198 -c Bills      | Adds entry with no description and value: $198 as outflow, with its  |\n" +
            "|                             | category set to Bills                                                |\n" +
            "+====================================================================================================+\n";

    private static final String HELP_DELETE_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Delete command              | Used to delete an existing entry from RainyDay                       |\n" +
            "+====================================================================================================+\n" +
            "| Details    | Requirement    | Description                                                          |\n" +
            "+------------+----------------+----------------------------------------------------------------------+\n" +
            "| INDEX      | Mandatory      | Used to delete an entry, as denoted by the given index               |\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| delete 7                    | Deletes the 7th entry from the list                                  |\n" +
            "+====================================================================================================+\n";

    private static final String HELP_VIEW_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| View command                | Views entries within a specific timeframe in RainyDay                |\n" +
            "+====================================================================================================+\n" +
            "| Details    | Requirement    | Description                                                          |\n" +
            "+------------+----------------+----------------------------------------------------------------------+\n" +
            "| TIME       | Optional       | Used to set the timeframe, going back from the current date; With    |\n" +
            "|            |                | timeframes not more than 31 days, 4 weeks, 12 months or 10 years.    |\n" +
            "|            |                | -all can also be used to view all entries                            |\n" +
            "| -sort      | Optional       | Sort entries by their value, and show them in non-decreasing order.  |\n" +
            "|            |                | Inflows are shown before Outflows, and statements will be displayed  |\n" +
            "|            |                | in entry order if omitted.                                           |\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| view -all                   | View all entries                                                     |\n" +
            "| view 1d                     | View entries starting from yesterday, including today                |\n" +
            "| view 18d                    | View entries starting from the past 18 days                          |\n" +
            "| view 18d -sort              | View entries starting from the past 18 days in sorted order          |\n" +
            "| view 3w                     | View entries starting from the past 3 weeks                          |\n" +
            "| view 6m                     | View entries starting from the past 6 months                         |\n" +
            "| view 12m                    | View entries starting from the past 12 months / 1 year               |\n" +
            "| view 3y                     | View entries starting from the past 3 years                          |\n" +
            "| view -sort                  | View entries starting from the past 1 month in sorted order          |\n" +
            "+====================================================================================================+\n";
    private static final String HELP_FILTER_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Filter command              | Filters entries by specific constraints                              |\n" +
            "+====================================================================================================+\n" +
            "| Details       | Requirement | Description                                                          |\n" +
            "+---------------+-------------+----------------------------------------------------------------------+\n" +
            "| -in / -out    | Optional    | Used to filter for only inflows / outflows.                          |\n" +
            "| -d <DESC>     | Optional    | Used to filter for descriptions with a matching substring            |\n" +
            "| -c <CATEGORY> | Optional    | Used to filter for categories with a matching substring              |\n" +
            "| -date <DATE>  | Optional    | Used to filter for entries with the specific date                    |\n" +
            "+---------------+-------------+----------------------------------------------------------------------+\n" +
            "| Note: Multiple flags are allowed but must be in the following order:                               |\n" +
            "|        `-in` or `out` -> `-d` -> `-c` -> `-date`                                                   |\n" +
            "+---------------+-------------+----------------------------------------------------------------------+\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| filter -in                  | View all inflows                                                     |\n" +
            "| filter -c Food              | View all entries with the category: Food                             |\n" +
            "| filter -date 22/03/2022     | View all entries with the date: 22/03/2022                           |\n" +
            "| filter -d Bubble Tea        | View all entries containing the phrase: Bubble Tea                   |\n" +
            "| filter -out -c Transfers    | View all outflows with the category: Transfers                       |\n" +
            "| filter -out -d Shark -c Toys| View all outflows containing the word: Shark and the category: Toys  |\n" +
            "+====================================================================================================+\n";
    private static final String HELP_EDIT_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Edit command (WIP)          | Edits a specific entry                                               |\n" +
            "+====================================================================================================+\n" +
            "| Details       | Requirement | Description                                                          |\n" +
            "+---------------+-------------+----------------------------------------------------------------------+\n" +
            "| index         | Mandatory   | Used to specify the entry to edit                                    |\n" +
            "| -in / -out    | Optional    | Used to change an entry to outflow or inflow                         |\n" +
            "| -d <DESC>     | Optional    | Used to change the description of an entry                           |\n" +
            "| -v $<VALUE>   | Optional    | Used to change the value of an entry                                 |\n" +
            "| -c <CATEGORY> | Optional    | Used to change the category of an entry                              |\n" +
            "| -date <DATE>  | Optional    | Used to change the date of an entry                                  |\n" +
            "+---------------+-------------+----------------------------------------------------------------------+\n" +
            "| Note: Multiple flags are allowed but must be in the following order:                               |\n" +
            "|        `-in` or `out` -> `-d` -> `-c` -> `-date`                                                   |\n" +
            "+---------------+-------------+----------------------------------------------------------------------+\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| edit 1 -in                  | Change flow direction of entry 1 to inflow                           |\n" +
            "| edit 3 -c Food              | Change category of entry 3 to Food                                   |\n" +
            "| edit 7 -date 22/03/2022     | Change date of entry 7 to 22/03/2022                                 |\n" +
            "| edit 2 -d Bubble Tea        | Change description of entry 2 to Bubble Tea                          |\n" +
            "| edit 4 -out -c Transfers    | Change flow direction of entry 4 to outflow and category to Transfers|\n" +
            "| edit 5 -out -d Shark -c Toys| Change flow direction of entry 5 to outflow, description to Shark,   |\n" +
            "| -date 22/03/2023            | category to Toys and date to 22/03/2023                              |\n" +
            "+====================================================================================================+\n";
    private static final String HELP_EXPORT_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Export command              | Used to export all entries in RainyDay to CSV                        |\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| export                      | Creates a CSV file with all entries                                  |\n" +
            "+====================================================================================================+\n";
    private static final String HELP_SET_BUDGET_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Set budget command          | Used to set the user's Monthly Budget Goal                           |\n" +
            "+====================================================================================================+\n" +
            "| Details    | Requirement    | Description                                                          |\n" +
            "+------------+----------------+----------------------------------------------------------------------+\n" +
            "| VALUE      | Mandatory      | Used to specify the user's Monthly Budget Goal, up to 2dp            |\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| setbudget 44.5              | Sets the user's Budget Goal to be $44.50                             |\n" +
            "| setbudget 0                 | Remove the user's Budget Goal                                        |\n" +
            "+====================================================================================================+\n";

    private static final String HELP_SHORTCUT_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Shortcut commands (3)       | Used to create custom commands to act as shortcuts                   |\n" +
            "+====================================================================================================+\n" +
            "|   <shortcut add>            |                                                                      |\n" +
            "| Details    | Requirement    | Description                                                          |\n" +
            "+------------+----------------+----------------------------------------------------------------------+\n" +
            "| SHORTCUT   | Mandatory      | Used to denote the shortcut the user wants to use                    |\n" +
            "| -maps      | Mandatory      | Used to separate the shortcut from the actual command                |\n" +
            "| COMMAND    | Mandatory      | Used to denote the actual command                                    |\n" +
            "+------------+----------------+----------------------------------------------------------------------+\n" +
            "|   <shortcut delete>         |                                                                      |\n" +
            "| Details    | Requirement    | Description                                                          |\n" +
            "+------------+----------------+----------------------------------------------------------------------+\n" +
            "| SHORTCUT   | Mandatory      | Used to denote the shortcut the user wants to delete                 |\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| shortcut salary -maps add   | Creates a shortcut: salary ,which runs add -in $10000 salary         |\n" +
            "| -in $10000 salary           |                                                                      |\n" +
            "|                             |                                                                      |\n" +
            "| shortcut_view               | Views all shortcuts created and what they are mapped to              |\n" +
            "|                             |                                                                      |\n" +
            "| shortcut_delete salary      | Remove the shortcut: salary                                          |\n" +
            "+====================================================================================================+\n";

    private static final String HELP_IGNORE_COMMAND = "" +
            "+====================================================================================================+\n" +
            "| Ignore commands (2)         | Used to ignore the calculation of an existing entry from RainyDay    |\n" +
            "+====================================================================================================+\n" +
            "| Details    | Requirement    | Description                                                          |\n" +
            "+------------+----------------+----------------------------------------------------------------------+\n" +
            "| INDEX      | Mandatory      | Entry at the given index will be ignored when calculating overview   |\n" +
            "+====================================================================================================+\n" +
            "| Example Usage               | Description                                                          |\n" +
            "+-----------------------------+----------------------------------------------------------------------+\n" +
            "| ignore 7                    | Ignores the 7th entry from the list                                  |\n" +
            "| unignore 7                  | Un-ignores the 7th entry from the list                               |\n" +
            "+====================================================================================================+\n";

    private static final String HELP_HELP_COMMAND = "You funny guy. I like you.";

    private final String description;

    public HelpCommand(String description) {
        this.description = description;
    }

    @Override
    protected void setupLogger() {

    }

    /**
     * Depending on the requested help, prints a specific help to console.
     * A general overview is provided if description does not match to valid commands, or if it is empty
     *
     * @return a valid CommandResult which prints the string for the Help Table
     */
    @Override
    public CommandResult execute() {
        if (description.equals("add")) {
            return new CommandResult(HELP_ADD_COMMAND);
        }
        if (description.equals("delete")) {
            return new CommandResult(HELP_DELETE_COMMAND);
        }
        if (description.equals("view")) {
            return new CommandResult(HELP_VIEW_COMMAND);
        }
        if (description.equals("filter")) {
            return new CommandResult(HELP_FILTER_COMMAND);
        }
        if (description.equals("edit")) {
            return new CommandResult(HELP_EDIT_COMMAND);
        }
        if (description.equals("help")) {
            return new CommandResult(HELP_HELP_COMMAND);
        }
        if (description.equals("export")) {
            return new CommandResult(HELP_EXPORT_COMMAND);
        }
        if (description.equals("setbudget")) {
            return new CommandResult(HELP_SET_BUDGET_COMMAND);
        }
        if (description.equals("shortcut")) {
            return new CommandResult(HELP_SHORTCUT_COMMAND);
        }
        if (description.equals("ignore") || description.equalsIgnoreCase("unignore")) {
            return new CommandResult(HELP_IGNORE_COMMAND);
        }
        return new CommandResult(HELP_COMMAND);
    }
}
