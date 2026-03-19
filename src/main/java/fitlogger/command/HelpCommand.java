package fitlogger.command;

import fitlogger.ui.Ui;

public class HelpCommand extends Command {

    /**
     * Saves workout data and displays a goodbye message to the user.
     *
     * @param ui UI used to display output messages.
     */
    public void execute(Ui ui) {
        ui.showHelpMenu();
    }
}
