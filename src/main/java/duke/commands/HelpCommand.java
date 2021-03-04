package duke.commands;

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        return new CommandResult(
                AddTodoCommand.MESSAGE_USAGE
                        + "\n" + AddDeadlineCommand.MESSAGE_USAGE
                        + "\n" + DeleteCommand.MESSAGE_USAGE
                        + "\n" + ListCommand.MESSAGE_USAGE
                        + "\n" + DoneCommand.MESSAGE_USAGE
                        + "\n" + HelpCommand.MESSAGE_USAGE
                        + "\n" + ExitCommand.MESSAGE_USAGE
        );
    }
}
