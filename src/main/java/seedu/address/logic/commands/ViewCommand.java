package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author {zhXchD}
public abstract class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_VIEW_SUCCESS = "View %1$s: %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the person or entry by the "
            + "index number used in the displayed list."
            + "\nParameters: in/SCOPE (must be \"c\" or  \"j\") index/INDEX (must be a positive integer)"
            + "\nExample: " + COMMAND_WORD + " in/c" + " index/1";

    protected final Index targetIndex;

    public ViewCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);
}
